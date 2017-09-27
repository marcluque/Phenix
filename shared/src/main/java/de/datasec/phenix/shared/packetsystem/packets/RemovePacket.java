package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 26.09.2017.
 */
@PacketId(3)
public class RemovePacket extends Packet {

    private Object value;

    public RemovePacket() {
        // For protocol
    }

    public RemovePacket(Object value) {
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