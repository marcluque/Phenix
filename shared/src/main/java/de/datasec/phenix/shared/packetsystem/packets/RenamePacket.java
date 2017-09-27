package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 26.09.2017.
 */
public class RenamePacket extends Packet {

    private Object oldKey;

    private Object newKey;

    public RenamePacket() {
        id = 7;
        // For protocol
    }

    public RenamePacket(Object oldKey, Object newKey) {
        this.oldKey = oldKey;
        this.newKey = newKey;
        id = 7;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            oldKey = readObject(byteBuf);
            newKey = readObject(byteBuf);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, oldKey);
            writeObject(byteBuf, newKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getOldKey() {
        return oldKey;
    }

    public Object getNewKey() {
        return newKey;
    }
}
