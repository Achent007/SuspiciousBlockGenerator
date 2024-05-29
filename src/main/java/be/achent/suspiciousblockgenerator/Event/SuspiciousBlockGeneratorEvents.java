package be.achent.suspiciousblockgenerator.Event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SuspiciousBlockGeneratorEvents extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private final Player player;
    private final Block block;

    public SuspiciousBlockGeneratorEvents(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static SuspiciousBlockGeneratorEvents trigger(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SUSPICIOUS_GRAVEL) {
            SuspiciousBlockGeneratorEvents gravelBrushEvent = new SuspiciousBlockGeneratorEvents(event.getPlayer(), event.getClickedBlock());
            event.getPlayer().getServer().getPluginManager().callEvent(gravelBrushEvent);
            return gravelBrushEvent;
        }
        return null;
    }
}