package dev.pns.signapi;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SignAPI {
    private final Map<UUID, SignTask> openGUIs = new HashMap<>();
    private final Async2Sync async2Sync;

    public SignAPI(JavaPlugin plugin) {
        async2Sync = new Async2Sync(plugin);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.UPDATE_SIGN) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    UUID uuid = event.getPlayer().getUniqueId();
                    if (!openGUIs.containsKey(uuid)) return;

                    SignTask signTask = openGUIs.get(uuid);
                    signTask.setLines(event.getPacket().getStringArrays().read(0));

                    openGUIs.remove(uuid);
                    async2Sync.add(signTask);
                }, 0);
            }
        });
    }

    /**
     * Creates a sign GUI for the player.
     * @param text The text to display on the sign.
     * @param onClose The function to call when the player closes the GUI.
     * @return The GUI object.
     */
    public SignGUI createGUI(List<String> text, SignGUI.onClose onClose) {
        return new SignGUI(openGUIs, text, onClose);
    }
}
