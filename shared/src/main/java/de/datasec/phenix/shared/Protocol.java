package de.datasec.phenix.shared;

import de.datasec.phenix.shared.network.packetsystem.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DataSec on 05.09.2017.
 */
public class Protocol {

    private Map<Byte, Class<? extends Packet>> packets = new HashMap<>();

    private Map<Class<? extends Packet>, Byte> packetBytes = new HashMap<>();

    public void registerPacket(byte id, Class<? extends Packet> clazz) {

        if(clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }

        if (packets.containsKey(id)) {
            throw new IllegalArgumentException("Packet with id " + id + " is already registered!");
        }

        packets.put(id, clazz);
    }

    public Packet createPacket(byte id) {
        try {
            return packets.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte getPacketId(Packet packet) {
        return packetBytes.get(packet.getClass());
    }
}
