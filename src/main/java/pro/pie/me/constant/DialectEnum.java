package pro.pie.me.constant;

public enum DialectEnum {
    MySQL("mysql"),
    ORACLE("oracle");

    DialectEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return value.toUpperCase();
    }

    public static DialectEnum getDialectEnum(String var) {
        for (DialectEnum de : DialectEnum.values()) {
            if (de.getValue().equals(var)) {
                return de;
            }
        }
        return null;
    }
}
