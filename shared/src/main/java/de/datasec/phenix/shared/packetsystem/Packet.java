package de.datasec.phenix.shared.packetsystem;

import io.netty.buffer.ByteBuf;

import java.io.*;

/**
 * Created by DataSec on 05.09.2017.
 */
public abstract class Packet {

    public int id;

    public abstract void read(ByteBuf byteBuf);

    public abstract void write(ByteBuf byteBuf);

    protected void writeByte(ByteBuf byteBuf, byte byt) {
        byteBuf.writeByte(byt);
    }

    protected byte readByte(ByteBuf byteBuf) {
        return byteBuf.readByte();
    }

    protected void writeBoolean(ByteBuf byteBuf, boolean bool) {
        byteBuf.writeBoolean(bool);
    }

    protected boolean readBoolean(ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }

    protected void writeLong(ByteBuf byteBuf, long longNumber) {
        byteBuf.writeLong(longNumber);
    }

    protected long readLong(ByteBuf byteBuf) {
        return byteBuf.readLong();
    }

    protected void writeObject(ByteBuf byteBuf, Object object) throws IOException {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
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

    public int getId() {
        return id;
    }
}