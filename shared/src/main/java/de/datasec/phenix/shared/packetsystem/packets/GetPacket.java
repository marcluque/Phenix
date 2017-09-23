package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 05.09.2017.
 */
public class GetPacket extends Packet {

    private int id;

    private Object value;

    public GetPacket(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            value = readObject(byteBuf);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getValue() {
        return value;
    }
}