package garner.tan.redditclient.model;

public enum RedditType {

    LISTING("Listing"),
    POST("t3"),
    COMMENT("t1"),
    MORE("more");

    /**
     * @author Garner Jervis Tan
     */
    private String typeCode;

    RedditType(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public static RedditType fromTypeCode(String typeCode) {
        for (RedditType type : RedditType.values()) {
            if(type.typeCode.equalsIgnoreCase(typeCode)) {
                return type;
            }
        }
        return null;
    }

}
