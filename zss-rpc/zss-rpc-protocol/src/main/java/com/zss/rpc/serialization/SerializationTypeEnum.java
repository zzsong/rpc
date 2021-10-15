package com.zss.rpc.serialization;


public enum SerializationTypeEnum {
    HESSIAN(0x10),
    JSON(0x20);

    private final int type;

    SerializationTypeEnum(int type) {
        this.type = type;
    }

    public static SerializationTypeEnum findByType(byte serializationType) {
        for (SerializationTypeEnum typeEnum : SerializationTypeEnum.values()) {
            if (typeEnum.getType() == serializationType) {
                return typeEnum;
            }
        }
        return HESSIAN;
    }

    public int getType() {
        return type;
    }
}
