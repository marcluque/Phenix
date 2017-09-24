package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 24.09.2017.
 */
public class ContainsPacket extends Packet {

    private Object value;

    private byte isKey;

    public ContainsPacket() {
        id = 2;
        // For protocol
    }

    public ContainsPacket(Object value) {
        this(value, (byte) -1);
    }

    public ContainsPacket(Object value, byte isKey) {
        this.value = value;
        this.isKey = isKey;
        id = 2;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            value = readObject(byteBuf);
            isKey = readByte(byteBuf);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, value);
            writeByte(byteBuf, isKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getValue() {
        return value;
    }

    public byte isKey() {
        return isKey;
    }
}