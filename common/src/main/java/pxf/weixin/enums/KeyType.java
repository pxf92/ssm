package pxf.weixin.enums;

public enum KeyType{

        TOKEN("token"),
        ;
        private String key;

        KeyType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }