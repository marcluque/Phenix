package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 26.09.2017.
 */
@PacketId(2)
public class ContainsPacket extends Packet {

    private Object object;

    private byte isKey;

    public ContainsPacket() {
        // For protocol
    }

    public ContainsPacket(Object object, byte isKey) {
        this.object = object;
        this.isKey = isKey;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            object = readObject(byteBuf);
            isKey = readByte(byteBuf);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, object);
            writeByte(byteBuf, isKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject() {
        return object;
    }

    public byte isKey() {
        return isKey;
    }
}
