package dev.pns.signapi;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SignGUI {
    private final onClose onClose;
    private final List<String> text;
    private final Map<UUID, onClose> openGUIs;
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public SignGUI(Map<UUID, onClose> openGUIs, List<String> text, onClose closeFunction) {
        this.openGUIs = openGUIs;
        this.text = text;
        this.onClose = closeFunction;
    }

    public void open(Player player) {
        // Send a packet to show a sign at location
        BlockPosition bp = new BlockPosition(player.getLocation().getBlockX(), (255 - player.getLocation().getBlockY()), player.getLocation().getBlockZ());
        player.sendBlockChange(bp.toLocation(player.getWorld()), Material.OAK_SIGN.createBlockData());

        // Update the sign with the text
        if (text != null && text.size() > 0) {
            PacketContainer updateSign = protocolManager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);
            NbtCompound signNBT = (NbtCompound) updateSign.getNbtModifier().read(0);

            // Write all nbt data
            signNBT.put("id", "minecraft:sign");
            signNBT.put("x", bp.getX());
            signNBT.put("y", bp.getY());
            signNBT.put("z", bp.getZ());

            for (int i = 0; i < 4; i++) {
                signNBT.put("Text" + (i + 1), text.size() > i ? String.format("{\"text\":\"%s\"}", text.get(i)) : "");
            }

            updateSign.getNbtModifier().write(0, signNBT);
            updateSign.getBlockPositionModifier().write(0, bp);
            updateSign.getIntegers().write(0, 9);

            sendPacket(player, updateSign);
        }

        // Send a packet to open the sign
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        packet.getBlockPositionModifier().write(0, bp);
        sendPacket(player, packet);

        // Store the method to be called when the sign is closed
        openGUIs.put(player.getUniqueId(), onClose);
    }

    public interface onClose {
        void method(Player player, String[] lines);
    }

    private void sendPacket(Player player, PacketContainer packet) {
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {e.printStackTrace();}
    }
}
