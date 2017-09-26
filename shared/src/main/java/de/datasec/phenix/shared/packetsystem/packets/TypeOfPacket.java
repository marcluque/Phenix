package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 26.09.2017.
 */
public class TypeOfPacket extends Packet {

    private Object object;

    public TypeOfPacket() {
        id = 4;
        // For protocol
    }

    public TypeOfPacket(Object object) {
        this.object = object;
        id = 4;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            object = readObject(byteBuf);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject() {
        return object;
    }
}
