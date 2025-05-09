package dev.pns.signapi;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SignTask {
    private final Player player;
    private final SignGUI.onClose onClose;
    private final Block block;
    private String[] lines;

    public SignTask(Player player, SignGUI.onClose onClose, Block block) {
        this.player = player;
        this.onClose = onClose;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public SignGUI.onClose getOnClose() {
        return onClose;
    }

    public Block getBlock() {
        return block;
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }

}
