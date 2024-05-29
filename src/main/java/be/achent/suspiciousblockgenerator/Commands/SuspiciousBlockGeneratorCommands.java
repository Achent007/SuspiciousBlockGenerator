package be.achent.suspiciousblockgenerator.Commands;

import be.achent.suspiciousblockgenerator.SuspiciousBlockGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;

public class SuspiciousBlockGeneratorCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        SuspiciousBlockGenerator plugin = SuspiciousBlockGenerator.getInstance();
        if (args.length == 0) {
            if (sender.hasPermission("suspiciousblockregenerator.reload") || sender.hasPermission("suspiciousblockgenerator.help")) {
                sender.sendMessage("");
                sender.sendMessage(ChatColor.GOLD + "/suspiciousblockgenerator" + ChatColor.WHITE + ": Command from SuspiciousBlockGenerator");
                sender.sendMessage(ChatColor.GOLD + "/suspiciousblockgenerator reload" + ChatColor.WHITE + ": Reload config and language files.");
                sender.sendMessage("");
            } else {
                sender.sendMessage(plugin.getMessage("NoPermission"));
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("suspiciousblockgenerator.reload")) {
                if (args.length == 1) {
                    File language = new File(plugin.getDataFolder(), "language.yml");
                    if (!language.exists()) {
                        plugin.saveDefaultsMessages();
                    } else {
                        plugin.reloadMessages();
                    }
                    sender.sendMessage(plugin.getMessage("Reloaded"));
                } else {
                    sender.sendMessage("reload");
                }
            } else {
                sender.sendMessage(plugin.getMessage("NoPermission"));
            }
        }
        return false;
    }
}
