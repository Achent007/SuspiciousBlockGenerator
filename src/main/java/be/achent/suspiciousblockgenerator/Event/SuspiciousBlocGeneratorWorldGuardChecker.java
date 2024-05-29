package be.achent.suspiciousblockgenerator.Event;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;
import java.util.List;

public class SuspiciousBlocGeneratorWorldGuardChecker {

    private final WorldGuardPlugin worldGuard;
    private final List<String> allowedRegions;

    public SuspiciousBlocGeneratorWorldGuardChecker(WorldGuardPlugin worldGuard, List<String> allowedRegions) {
        this.worldGuard = worldGuard;
        this.allowedRegions = allowedRegions;
    }

    public boolean isPlayerInAllowedRegion(Player player) {
        if (worldGuard == null) {
            return false;
        }
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        com.sk89q.worldedit.util.Location weLocation = BukkitAdapter.adapt(player.getLocation());
        ApplicableRegionSet regions = query.getApplicableRegions(weLocation);
        for (String regionName : allowedRegions) {
            if (regions.getRegions().stream().anyMatch(region -> region.getId().equalsIgnoreCase(regionName))) {
                return true;
            }
        }
        return false;
    }
}