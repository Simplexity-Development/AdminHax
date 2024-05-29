package simplexity.adminhax.commands.hax;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

public class Fly extends AbstractHaxCommand {

    private final NamespacedKey flyStatus = Util.FLY_STATUS;

    public Fly(Permission basicPermission, Permission adminPermission) {
        super(basicPermission, adminPermission);
    }

    @Override
    public void runLogic(Player player, CommandSender sender, String[] args, boolean runningOnOther) {
        switchFlyState(player);
        super.runLogic(player, sender, args, runningOnOther);
    }

    @Override
    public void sendSelfMessage(Player player) {
        String value = getValueString(player);
        Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetOwn(), value);
    }

    @Override
    public void sendOtherMessage(CommandSender sender, Player player) {
        String value = getValueString(player);
        Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetOwn(), value);
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getFlySetOther(), value, player);
    }

    private String getValueString(Player player){
        String value;
        if (isFlyEnabled(player)) {
            value = LocaleHandler.getInstance().getEnabled();
        } else {
            value = LocaleHandler.getInstance().getDisabled();
        }
        return value;
    }

    private boolean isFlyEnabled(Player player) {
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        return playerPDC.getOrDefault(flyStatus, PersistentDataType.BOOLEAN, Boolean.FALSE);
    }

    private void switchFlyState(Player player) {
        boolean flyState = Util.isPDCStateEnabled(player, flyStatus, false);
        //If they have no set flystate, or it's off, set true, set flying
        if (!flyState) {
            Util.flipPDCState(player, flyStatus, false);
            player.setAllowFlight(true);
            player.setFlying(true);
            return;
        }
        //If their current flystate is on, set false
        Util.flipPDCState(player, flyStatus, false);
        player.setAllowFlight(false);
        player.setFlying(false);
        return;
    }
}
