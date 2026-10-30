package com.blocksaffinity.broadcast;

import io.papermc.paper.command.brigadier.BasicCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Broadcast extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveResource("config.yml", false);

        BasicCommand broadcastCommand = new BroadcastCommand(this);
        this.registerCommand("broadcast", broadcastCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
