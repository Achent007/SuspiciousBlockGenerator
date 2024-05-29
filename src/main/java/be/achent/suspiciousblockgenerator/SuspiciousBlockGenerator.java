package be.achent.suspiciousblockgenerator;

import be.achent.suspiciousblockgenerator.ChatColorHandler.ChatColorHandler;
import be.achent.suspiciousblockgenerator.ChatColorHandler.parsers.custom.MiniMessageParser;
import be.achent.suspiciousblockgenerator.ChatColorHandler.parsers.custom.PlaceholderAPIParser;
import be.achent.suspiciousblockgenerator.Event.SuspiciousBlocGeneratorWorldGuardChecker;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class SuspiciousBlockGenerator extends JavaPlugin implements Listener {

    public static SuspiciousBlockGenerator plugin;
    private Messages messages;
    private WorldGuardPlugin worldGuard;
    private SuspiciousBlocGeneratorWorldGuardChecker worldGuardChecker;

    @Override
    public void onEnable() {
        plugin = this;
        this.messages = new Messages();
        this.messages.saveDefaultConfig();

        getLogger().info(getMessage("messages.plugin_enabled"));

        getServer().getPluginManager().registerEvents(this, this);

        getCommand("suspiciousblockgenerator").setExecutor(new SuspiciousBlockGenerator());

        if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            worldGuard = WorldGuardPlugin.inst();
            worldGuardChecker = new SuspiciousBlocGeneratorWorldGuardChecker(worldGuard, getConfig().getStringList("worldguard_regions"));
        } else {
            getLogger().warning(getMessage("messages.worldguard_not_found"));
            worldGuard = null;
            worldGuardChecker = null;
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(getMessage("messages.plugin_disabled"));
    }

    public static SuspiciousBlockGenerator getInstance() {
        return plugin;
    }

    public String getMessage(String path) {
        String message = this.messages.get().getString(path);
        if (message != null) {
            String prefix = this.messages.get().getString("messages.prefix");
            message = message.replace("{prefix}", prefix);
            return ChatColorHandler.translateAlternateColorCodes(message, List.of(PlaceholderAPIParser.class, MiniMessageParser.class));
        }
        return "";
    }

    public void reloadMessages() {
        this.messages.reload();
    }

    public void saveDefaultsMessages() {
        this.messages.saveDefaultConfig();
    }
}