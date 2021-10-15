package com.zss.rpc.protocol;


public enum MsgType {
    REQUEST(1),
    RESPONSE(2),
    HEARTBEAT(3);

    private final int type;

    MsgType(int type) {
        this.type = type;
    }

    public static MsgType findByType(int type) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }
}
