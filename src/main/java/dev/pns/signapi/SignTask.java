package dev.pns.signapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class SignTask {
    private final Player player;
    private final SignGUI.onClose onClose;
    private final Block block;
    @Setter
    private String[] lines;
}
