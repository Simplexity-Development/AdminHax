package simplexity.adminhax.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;

public class FlyListeners implements Listener {
    
    private static final NamespacedKey flyStatus = Util.FLY_STATUS;
    
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent joinEvent) {
        if (!ConfigHandler.getInstance().isSessionPersistentFlight()) {
            return;
        }
        Player player = joinEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Bukkit.getScheduler().runTaskLater(AdminHax.getInstance(), () -> {
            boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
            if (flyEnabled && player.hasPermission(Util.FLY_PERMISSION)) {
                player.setAllowFlight(true);
                if (player.getFallDistance() > 0f) {
                    player.setFlying(true);
                }
                Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetOwn(), LocaleHandler.getInstance().getEnabled(), null);
                return;
            }
            if (flyEnabled && !player.hasPermission(Util.FLY_PERMISSION)) {
                playerPDC.set(flyStatus, PersistentDataType.BOOLEAN, false);
            }
        }, 10);
    }
    
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent worldEvent) {
        if (!ConfigHandler.getInstance().isWorldChangePersistentFlight()) return;
        Player player = worldEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
        if (flyEnabled) {
            player.setAllowFlight(true);
            if (player.getFallDistance() > 0f) {
                player.setFlying(true);
            }
        }
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent respawnEvent) {
        if (!ConfigHandler.getInstance().isRespawnPersistentFlight()) return;
        Player player = respawnEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
        if (flyEnabled) {
            player.setAllowFlight(true);
        }
    }
    
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent gameModeChangeEvent) {
        if (!ConfigHandler.getInstance().isGamemodeChangePersistentFlight()) return;
        Player player = gameModeChangeEvent.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Bukkit.getScheduler().runTaskLater(AdminHax.getInstance(), () -> {
            Boolean flyEnabled = playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, false);
            if (flyEnabled) {
                player.setAllowFlight(true);
                if (player.getFallDistance() > 0f) {
                    player.setFlying(true);
                }
            }
        }, 10);
    }
    
    
}
