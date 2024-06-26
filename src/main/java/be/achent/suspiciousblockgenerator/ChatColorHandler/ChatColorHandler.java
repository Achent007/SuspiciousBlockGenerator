package be.achent.suspiciousblockgenerator.ChatColorHandler;

import be.achent.suspiciousblockgenerator.ChatColorHandler.messengers.LegacyMessenger;
import be.achent.suspiciousblockgenerator.ChatColorHandler.messengers.Messenger;
import be.achent.suspiciousblockgenerator.ChatColorHandler.messengers.MiniMessageMessenger;
import be.achent.suspiciousblockgenerator.ChatColorHandler.parsers.Parsers;
import be.achent.suspiciousblockgenerator.ChatColorHandler.parsers.custom.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatColorHandler {
    private static final String LOGGER_PREFIX = "[ChatColorHandler] ";
    private static final Pattern HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");
    private static Messenger messenger;

    static {
        Parsers.register(new LegacyCharParser(), 100);
        Parsers.register(new HexParser(), 70);

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            Parsers.register(new MiniMessageParser(), 80);
            messenger = (Messenger) new MiniMessageMessenger();

            Bukkit.getLogger().info(LOGGER_PREFIX + "Server running on PaperMC (or fork). MiniMessage support enabled.");
        } catch (ClassNotFoundException ignored) {
            messenger = (Messenger) new LegacyMessenger();
        }

        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        if (pluginManager.getPlugin("PlaceholderAPI") != null && pluginManager.isPluginEnabled("PlaceholderAPI")) {
            Parsers.register(new PlaceholderAPIParser(), 90);
            Bukkit.getLogger().info(LOGGER_PREFIX + "Found plugin \"PlaceholderAPI\". PlaceholderAPI support enabled.");
        }
    }

    /**
     * Sends this recipient a message
     *
     * @param recipient Sender to receive this message
     * @param message Message to be displayed
     */
    public static void sendMessage(@NotNull CommandSender recipient, @Nullable String message) {
        messenger.sendMessage(recipient, message);
    }

    /**
     * Sends this recipient multiple messages
     *
     * @param recipient Sender to receive message
     * @param messages Messages to be displayed
     */
    public static void sendMessage(@NotNull CommandSender recipient, @Nullable String... messages) {
        messenger.sendMessage(recipient, messages);
    }

    /**
     * Sends multiple recipients a message
     *
     * @param recipients Senders to receive message
     * @param message Message to be displayed
     */
    public static void sendMessage(CommandSender[] recipients, @Nullable String message) {
        messenger.sendMessage(recipients, message);
    }

    /**
     * Sends multiple recipients, multiple messages
     *
     * @param recipients Senders to receive this message
     * @param messages Messages to be displayed
     */
    public static void sendMessage(CommandSender[] recipients, @Nullable String... messages) {
        messenger.sendMessage(recipients, messages);
    }

    /**
     * Sends all online players a message
     *
     * @param message Message to be displayed
     */
    public static void broadcastMessage(@Nullable String message) {
        messenger.broadcastMessage(message);
    }

    /**
     * Sends all online players multiple messages
     *
     * @param messages Messages to be displayed
     */
    public static void broadcastMessage(@NotNull String... messages) {
        messenger.broadcastMessage(messages);
    }

    /**
     * Sends this player an ACTION_BAR message
     *
     * @param player Player to receive this action bar message
     * @param message Message to be displayed
     */
    public static void sendActionBarMessage(@NotNull Player player, @Nullable String message) {
        messenger.sendActionBarMessage(player, message);
    }

    /**
     * Sends multiple players an ACTION_BAR message
     *
     * @param players Players to receive this action bar message
     * @param message Message to be displayed
     */
    public static void sendActionBarMessage(@NotNull Player[] players, @Nullable String message) {
        messenger.sendActionBarMessage(players, message);
    }

    /**
     * Translates a string to allow for hex colours and placeholders
     *
     * @param string String to be converted
     */
    public static String translateAlternateColorCodes(@Nullable String string) {
        return translateAlternateColorCodes(string, null, null);
    }

    /**
     * Translates a string to allow for hex colours and placeholders
     *
     * @param string String to be converted
     * @param ignoredParsers Parsers which this message won't be parsed through
     */
    public static String translateAlternateColorCodes(@Nullable String string, List<Class<? extends Parser>> ignoredParsers) {
        if (string == null || string.isBlank()) return "";

        return Parsers.parseString(string, null, ignoredParsers);
    }

    /**
     * Translates a string to allow for hex colours and placeholders
     *
     * @param string String to be converted
     * @param player Player to parse placeholders for
     */
    public static String translateAlternateColorCodes(@Nullable String string, Player player) {
        return translateAlternateColorCodes(string, player, null);
    }

    /**
     * Translates a string to allow for hex colours and placeholders
     *
     * @param string String to be converted
     * @param player Player to parse placeholders for
     * @param ignoredParsers Parsers which this message won't be parsed through
     */
    public static String translateAlternateColorCodes(@Nullable String string, Player player, List<Class<? extends Parser>> ignoredParsers) {
        if (string == null || string.isBlank()) return "";

        return Parsers.parseString(string, player, ignoredParsers);
    }

    /**
     * Translates multiple strings to allow for hex colours and placeholders
     *
     * @param strings Strings to be converted
     */
    public static List<String> translateAlternateColorCodes(@Nullable List<String> strings) {
        return translateAlternateColorCodes(strings, null, null);
    }

    /**
     * Translates a string to allow for hex colours and placeholders
     *
     * @param strings Strings to be converted
     * @param ignoredParsers Parsers which this message won't be parsed through
     */
    public static List<String> translateAlternateColorCodes(@Nullable List<String> strings, List<Class<? extends Parser>> ignoredParsers) {
        return translateAlternateColorCodes(strings, null, ignoredParsers);
    }

    /**
     * Translates multiple strings to allow for hex colours and placeholders
     *
     * @param strings Strings to be converted
     * @param player Player to parse placeholders for
     */
    public static List<String> translateAlternateColorCodes(@Nullable List<String> strings, Player player) {
        return translateAlternateColorCodes(strings, player, null);
    }

    /**
     * Translates a string to allow for hex colours and placeholders
     *
     * @param strings Strings to be converted
     * @param player Player to parse placeholders for
     * @param ignoredParsers Parsers which this message won't be parsed through
     */
    public static List<String> translateAlternateColorCodes(@Nullable List<String> strings, Player player, List<Class<? extends Parser>> ignoredParsers) {
        if (strings == null || strings.isEmpty()) return Collections.emptyList();

        List<String> outputList = new ArrayList<>();
        for (String string : strings) {
            outputList.add(translateAlternateColorCodes(string, player));
        }
        return outputList;
    }

    /**
     * Strips colour from string
     *
     * @param string String to be converted
     */
    public static String stripColor(@Nullable String string) {
        if (string == null || string.isBlank()) return "";

        // Parse message through MiniMessage
//        if (isMiniMessageEnabled) {
//            string = TinyMessageTranslator.translateFromMiniMessage(string);
//        }

        // Replace legacy character
        string = string.replaceAll("§", "&");

        // Parse message through Default Hex in format "&#rrggbb"
        Matcher match = HEX_PATTERN.matcher(string);
        while (match.find()) {
            String color = string.substring(match.start() + 1, match.end());
            string = string.replace("&" + color, ChatColor.of(color) + "");
            match = HEX_PATTERN.matcher(string);
        }
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string));
    }

    /**
     * Strips colour from strings
     *
     * @param strings Strings to be converted
     */
    public static List<String> stripColor(@Nullable List<String> strings) {
        if (strings == null || strings.isEmpty()) return Collections.emptyList();

        List<String> outputList = new ArrayList<>();
        for (String string : strings) {
            outputList.add(stripColor(string));
        }
        return outputList;
    }
}
