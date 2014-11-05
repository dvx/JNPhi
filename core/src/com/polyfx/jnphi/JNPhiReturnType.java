package com.polyfx.jnphi;

/*
 * Pattern borrowed from Trever Shick
 * Ref: http://stackoverflow.com/questions/6692664/how-to-get-enum-value-from-index-in-java
 */
public enum JNPhiReturnType {
    NO_RETURN(null),
    VOID(Object.class),
    BOOLEAN(Boolean.class),
    BYTE(Byte.class),
    CHAR(Character.class),
    SHORT(Short.class),
    INT(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    OBJECT(Object.class);

    private Class<?> clazz;

    JNPhiReturnType(Class<?> cls) {
        this.clazz = cls;
    }
    
    public Object cast(Object obj) {
		return clazz.cast(obj);
    }

    public static final JNPhiReturnType[] RETURN_TYPES_INDEXED = new JNPhiReturnType[] { NO_RETURN, VOID, BOOLEAN, BYTE, CHAR, SHORT, INT, LONG, FLOAT, DOUBLE, OBJECT };

}