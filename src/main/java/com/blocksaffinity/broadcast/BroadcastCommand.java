package com.blocksaffinity.broadcast;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;
import java.util.logging.Level;

import java.util.Objects;

public class BroadcastCommand implements BasicCommand {
    private final Broadcast plugin;
    private String prefix;

    public BroadcastCommand(Broadcast broadcastPlugin)
    {
        plugin = broadcastPlugin;
        reloadConfig();
    }

    private void reloadConfig()
    {
        prefix = plugin.getConfig().getString("messages.prefix", "&6&l&nBroadcast&7 »&f");
    }

    @Override
    public void execute(final @NonNull CommandSourceStack source, final String[] args)
    {
        CommandSender sender = source.getSender();

        if (args.length == 0 || Objects.equals(args[0], "help") || Objects.equals(args[0], "usage"))
        {
            sender.sendRichMessage("<gold>Broadcast Usage:\n" +
                    "<dark_gray> - <gold>/broadcast <red>reload\n" +
                    "<dark_gray> - <gold>/broadcast <red><text>");
            return;
        }

        if (args[0].equals("reload"))
        {
            plugin.reloadConfig();
            reloadConfig();
            plugin.getLogger().log(Level.INFO, "Reloaded configuration.");
            sender.sendRichMessage(ColorParser.parse(prefix) + " <green>Reloaded configuration.");
        }

        else
        {
            final String joinedMessage = String.join(" ", args);
            final Component message = MiniMessage.miniMessage().
                    deserialize("<prefix> <message>",
                            Placeholder.component("prefix", ColorParser.parse(prefix)),
                            Placeholder.component("message", ColorParser.parse(joinedMessage))
                    );
            plugin.getServer().broadcast(message);
        }
    }
}
