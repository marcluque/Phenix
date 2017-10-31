package de.datasec.phenix.shared;

import de.datasec.phenix.shared.packetsystem.Packet;
import de.datasec.phenix.shared.packetsystem.PacketId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DataSec on 05.09.2017.
 */
public class Protocol {

    private Map<Byte, Class<? extends Packet>> packets = new HashMap<>();

    private Map<Class<? extends Packet>, Byte> packetBytes = new HashMap<>();

    public void registerPacket(Class<? extends Packet> clazz) {
        if(clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }

        byte id = clazz.getAnnotation(PacketId.class).value();

        if (packets.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Packet with id %s is already registered!", id));
        }

        packets.put(id, clazz);
        packetBytes.put(clazz, id);
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