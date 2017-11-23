package de.datasec.phenix.shared.packetsystem.packets;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;
import de.datasec.phenix.shared.util.TimeUnit;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by DataSec on 27.09.2017.
 */
@PacketId(8)
public class TimeToLivePacket extends Packet {

    private Object object;

    private byte returnTTL;

    private long timeToLive;

    public TimeToLivePacket() {
        // For protocol
    }

    public TimeToLivePacket(Object object, byte returnTTL) {
        this.object = object;
        this.returnTTL = returnTTL;
    }

    public TimeToLivePacket(Object object, byte returnTTL, long timeToLive, TimeUnit timeUnit) {
        this.object = object;
        this.returnTTL = returnTTL;
        adjustTimeToLive(timeToLive, timeUnit);
    }

    @Override
    public void read(ByteBuf byteBuf) {
        try {
            object = readObject(byteBuf);
            returnTTL = readByte(byteBuf);
            if (returnTTL == 0) {
                timeToLive = readLong(byteBuf);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(ByteBuf byteBuf) {
        try {
            writeObject(byteBuf, object);
            writeByte(byteBuf, returnTTL);
            if (returnTTL == 0) {
                writeLong(byteBuf, timeToLive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adjustTimeToLive(long timeToLive, TimeUnit timeUnit) {
        switch (timeUnit) {
            case MILLISECONDS:
                this.timeToLive = timeToLive;
                break;
            case SECONDS:
                this.timeToLive = timeToLive * 1000;
                break;
            case MINUTES:
                this.timeToLive = timeToLive * 1000 * 60;
                break;
            case HOURS:
                this.timeToLive = timeToLive * 1000 * 60 * 60;
                break;
        }
    }

    public Object getObject() {
        return object;
    }

    public byte getReturnTTL() {
        return returnTTL;
    }

    public long getTimeToLive() {
        return timeToLive;
    }
}