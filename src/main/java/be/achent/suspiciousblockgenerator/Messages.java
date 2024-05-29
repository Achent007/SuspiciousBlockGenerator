package be.achent.suspiciousblockgenerator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.*;

public class Messages {
    private FileConfiguration languageConfig = null;

    private File languageConfigFile = null;

    public void reload() {
        SuspiciousBlockGenerator plugin = SuspiciousBlockGenerator.getInstance();
        if (this.languageConfigFile == null)
            this.languageConfigFile = new File(plugin.getDataFolder(), "language.yml");
        this.languageConfig = YamlConfiguration.loadConfiguration(this.languageConfigFile);
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(plugin.getResource("language.yml"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.languageConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration get() {
        if (this.languageConfig == null)
            reload();
        return this.languageConfig;
    }

    public void saveConfig() {
        if (this.languageConfig == null || this.languageConfigFile == null)
            return;
        try {
            get().save(this.languageConfigFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        SuspiciousBlockGenerator plugin = SuspiciousBlockGenerator.getInstance();
        if (this.languageConfigFile == null)
            this.languageConfigFile = new File(plugin.getDataFolder(), "language.yml");
        if (!this.languageConfigFile.exists())
            plugin.saveResource("language.yml", false);
    }
}

