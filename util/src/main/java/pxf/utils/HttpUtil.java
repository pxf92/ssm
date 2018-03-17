package pxf.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

    public static String getQueryString(Map<String,String> map){
        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator it = entries.iterator();
        int size = map.size();
        int i=0;

        while(it.hasNext()){
            Map.Entry<String, String> entry  = (Map.Entry<String, String>)it.next();
            String key = entry.getKey();
            String value = entry.getValue();

            sb.append(key);
            sb.append("=");
            sb.append(value);

            if (++i<size) {
                sb.append("&");
            }
        }

        return sb.toString();
    }


}
