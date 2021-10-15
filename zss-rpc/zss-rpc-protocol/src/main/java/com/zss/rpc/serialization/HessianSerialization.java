package com.zss.rpc.serialization;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerialization implements RpcSerialization {

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        if (null == obj){
            throw new NullPointerException();
        }
        byte[] results ;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            HessianSerializerOutput output = new HessianSerializerOutput(bos);
            output.writeObject(obj);
            output.flush();
            results = bos.toByteArray();
        }
        return results;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        }
        T result;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            HessianSerializerInput input = new HessianSerializerInput(bis);
            result = (T) input.readObject(clz);
        }

        return result;
    }
}
