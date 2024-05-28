package simplexity.adminhax.fly;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.adminhax.Util;

public class FlyLogic {
    
    private static final NamespacedKey flyStatus = Util.FLY_STATUS;
    
    public static void switchFlyState(Player player) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        Boolean flyState = playerPDC.get(flyStatus, PersistentDataType.BOOLEAN);
        //If they have no set flystate, or it's off, set true, set flying
        if (flyState == null || !flyState) {
            playerPDC.set(flyStatus, PersistentDataType.BOOLEAN, true);
            player.setAllowFlight(true);
            player.setFlying(true);
            return;
        }
        //If their current flystate is on, set false
        playerPDC.set(flyStatus, PersistentDataType.BOOLEAN, false);
        player.setAllowFlight(false);
        player.setFlying(false);
        return;
    }

    public static boolean isFlyEnabled(Player player) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        return playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, Boolean.FALSE);
    }
}
