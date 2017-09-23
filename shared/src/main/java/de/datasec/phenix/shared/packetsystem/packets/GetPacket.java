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

    public GetPacket() {
        // Needs an empty constructor for protocol
    }

    public GetPacket(Object value) {
        this.value = value;
        id = 0;
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