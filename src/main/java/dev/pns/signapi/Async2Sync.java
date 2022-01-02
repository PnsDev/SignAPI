package dev.pns.signapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Async2Sync {
    private final List<SignTask> signTasks = new ArrayList<>();

    public Async2Sync(JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (signTasks.isEmpty()) return;
            Iterator<SignTask> iterator = signTasks.iterator();
            while (iterator.hasNext()) {
                SignTask signTask = iterator.next();
                signTask.getPlayer().sendBlockChange(signTask.getBlock().getLocation(), signTask.getBlock().getBlockData());
                signTask.getOnClose().method(signTask.getPlayer(), signTask.getLines());
                iterator.remove();
            }
        }, 0, 1);
    }

    public void add(SignTask signTask) {
        signTasks.add(signTask);
    }
}
