/*
 * @(#)HttpClientTransport.java 1.0 2011-6-29 下午07:54:28 Copyright 2004-2010 Client Service International, Inc. All
 * rights reserved. CSII PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pxf.utils;

import com.alibaba.druid.util.StringUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类HttpClientTransport.java的实现描述：TODO 类实现描述
 *
 * @author lx
 * @date 2016年9月20日
 * @version v1.0
 */
@Component
public class HttpConnClient {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private @Value("${http.protocol}") String     protocol;
    private @Value("${http.host}")String     host;
    private @Value("${http.port}")int        port;
    private @Value("${http.target}")String     target;
    private @Value("${http.queryString}")String     queryString;
    private @Value("${http.proxy}")boolean    proxy;
    private @Value("${http.proxyHost}")String     proxyHost;
    private @Value("${http.proxyPort}")int        proxyPort;
    private @Value("${http.contentType}")String     contentType;

    /*
     * (non-Javadoc)
     * @see com.csii.pe.service.comm.Transport#submit(java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    public Map<String,String> submit(Object data) throws Exception {
        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setMaxConnectionsPerHost(HostConfiguration.ANY_HOST_CONFIGURATION, 100);
        params.setConnectionTimeout(60000);
        params.setSoTimeout(60000);
        connectionManager.setParams(params);

        HttpClient httpClient = new HttpClient(connectionManager);

        init(httpClient);

        if (data instanceof byte[]) {
            return send(httpClient, (byte[]) data);
        } else if (data instanceof Map) {
            return send(httpClient, (Map) data);
        } else {
            throw new Exception();
        }
    }

    public Object upload(Object data) throws Exception {
        HttpClient httpClient = new HttpClient();

        init(httpClient);

        return post(httpClient, (File) data);
    }

    private void init(HttpClient httpClient) throws Exception {
        try {
            if ("http".equals(protocol)) {
                httpClient.getHostConfiguration().setHost(host, port);
            } else if ("https".equals(protocol)) {
                throw new RuntimeException("目前不支持https协议");
//                ProtocolSocketFactory protocolSocketFactory = null;
//                try {
//                    protocolSocketFactory = new EasySSLProtocolSocketFactory();
//                } catch (Exception ex) {
//                    log.error("Exception.", ex);
//                    throw new IllegalArgumentException(ex);
//                }
//                Protocol https = new Protocol(protocol, protocolSocketFactory, 443);
//                httpClient.getHostConfiguration().setHost(host, port, https);
            } else {
                throw new IllegalArgumentException("unsupported protocol: '" + protocol + "'");
            }

            if (proxy) {
                httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private Map<String,String> send(HttpClient httpClient, final byte[] bytes) throws Exception {
        PostMethod methods = new PostMethod(target);

        methods.setRequestHeader("Content-Type", contentType);

        if (!StringUtils.isEmpty(queryString)) {
            methods.setQueryString(queryString);
        }

        methods.setRequestEntity(new RequestEntity() {

            public void writeRequest(OutputStream outputStream) throws IOException {
                outputStream.write(bytes);
            }

            public boolean isRepeatable() {
                return false;
            }

            public String getContentType() {
                return contentType;
            }

            public long getContentLength() {
                return bytes.length;
            }
        });

        try {
            log.info("REQUEST MESSAGE:" + new String(bytes, "UTF-8"));

            httpClient.executeMethod(methods);
            log.info("HTTP STATUS:[" + methods.getStatusLine() + "]");

            return doSend(httpClient, methods);
        } catch (Exception ex) {
            throw ex;
        } finally {
            methods.releaseConnection();
        }
    }

    @SuppressWarnings("rawtypes")
    private Map<String,String> send(HttpClient httpClient, Map data) throws Exception {
        PostMethod methods = new PostMethod(target);

        methods.setRequestHeader("Content-Type", contentType);

        if (!StringUtils.isEmpty(queryString)) {
            methods.setQueryString(queryString);
        }

        if (data != null && !data.isEmpty()) {
            log.info("REQUEST MESSAGE:" + data);

            for (Iterator iterator = data.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                methods.addParameter(key, (String) data.get(key));
            }
        }
        return doSend(httpClient, methods);
    }

    private Map<String,String> doSend(HttpClient httpClient, PostMethod methods) throws Exception {
        try {
            httpClient.executeMethod(methods);
            log.info("HTTP STATUS:[" + methods.getStatusLine() + "]");
            // HTTP状态200为正常
            if (HttpStatus.SC_OK == methods.getStatusCode()) {
                byte[] result = methods.getResponseBody();

                Map<String, String> responseMap = new HashMap<String, String>();
                responseMap.put("respData", new String(result, "UTF-8"));
                responseMap.put("sign", methods.getResponseHeader("sign") == null ? "" : methods.getResponseHeader("sign").getValue());
                return responseMap;
            } else {
                throw new Exception("通讯超时");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            methods.releaseConnection();
        }
    }

    private byte[] post(HttpClient httpClient, File file) throws Exception {
        PostMethod methods = new PostMethod(target);

        if (!StringUtils.isEmpty(queryString)) {
            methods.setQueryString(queryString);
        }

        try {
            Part[] parts = { new FilePart(file.getName(), file) };
            methods.setRequestEntity(new MultipartRequestEntity(parts, methods.getParams()));

            httpClient.executeMethod(methods);
            log.info("HTTP STATUS:[" + methods.getStatusLine() + "]");

            // HTTP状态200为正常
            if (HttpStatus.SC_OK == methods.getStatusCode()) {
                byte[] result = methods.getResponseBody();

                log.info("RESPONSE MESSAGE:" + new String(result, "UTF-8"));

                return result;
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            throw (Exception) ex;
        } finally {
            methods.releaseConnection();
        }
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @param queryString the queryString to set
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * @param proxy the proxy to set
     */
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    /**
     * @param proxyHost the proxyHost to set
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    /**
     * @param proxyPort the proxyPort to set
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
