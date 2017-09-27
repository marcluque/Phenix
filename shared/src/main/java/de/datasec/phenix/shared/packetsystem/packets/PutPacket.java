package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;
import de.datasec.phenix.shared.util.TimeUnit;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 23.09.2017.
 */
@PacketId(1)
public class PutPacket extends Packet {

    private Object key;

    private Object value;

    private byte overrideIfKeyExists;

    // Saved as milliseconds on server
    private long timeToLive;

    private TimeUnit timeUnit;

    public PutPacket() {
        // For protocol
    }

    public PutPacket(Object key, Object value, boolean overrideIfKeyExists, long timeToLive, TimeUnit timeUnit) {
        this.key = key;
        this.value = value;
        this.overrideIfKeyExists = (byte) (overrideIfKeyExists ? 1 : 0);
        this.timeToLive = timeToLive;
        this.timeUnit = timeUnit;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            key = readObject(byteBuf);
            value = readObject(byteBuf);
            overrideIfKeyExists = readByte(byteBuf);
            timeToLive = readLong(byteBuf);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, key);
            writeObject(byteBuf, value);
            writeByte(byteBuf, overrideIfKeyExists);
            writeLong(byteBuf, (timeUnit == TimeUnit.MILLISECONDS) ? timeToLive : timeToLive * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public byte isOverrideIfKeyExists() {
        return overrideIfKeyExists;
    }

    public long getTimeToLive() {
        return timeToLive;
    }
}