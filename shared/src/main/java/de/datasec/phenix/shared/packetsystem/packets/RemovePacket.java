package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 26.09.2017.
 */
public class RemovePacket extends Packet {

    private Object value;

    public RemovePacket() {
        id = 3;
        // For protocol
    }

    public RemovePacket(Object value) {
        this.value = value;
        id = 3;
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
