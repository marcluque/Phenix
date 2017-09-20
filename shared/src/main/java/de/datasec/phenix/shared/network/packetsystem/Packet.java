package de.datasec.phenix.shared.network.packetsystem;

import io.netty.buffer.ByteBuf;

import java.io.*;

/**
 * Created by DataSec on 05.09.2017.
 */
public abstract class Packet {

    public abstract void read(ByteBuf byteBuf);

    public abstract void write(ByteBuf byteBuf);

    protected void writeObject(ByteBuf byteBuf, Object object) throws IOException {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException("object does not implement Serializable interface");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }

    protected Object readObject(ByteBuf byteBuf) throws IOException, ClassNotFoundException {
        Object object;

        int length = byteBuf.readInt();
        if (length > byteBuf.readableBytes()) {
            throw new IllegalStateException("length cannot be larger than the readable bytes");
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes); ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            object = objectInputStream.readObject();
        }

        return object;
    }
}