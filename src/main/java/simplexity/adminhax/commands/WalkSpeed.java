package simplexity.adminhax.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;

public class WalkSpeed extends AbstractSpeedCommand {
    public WalkSpeed(Permission basicPermission, Permission adminPermission, float defaultSpeed) {
        super(basicPermission, adminPermission, defaultSpeed);
    }

    public void setSpeedLogic(CommandSender sender, Player player, String[] args, Float speed) {
        super.setSpeedLogic(sender, player, args, speed);
        speed /= 10f;
        if (!isInRange(speed, ConfigHandler.getInstance().getMinFlySpeed(), ConfigHandler.getInstance().getMaxFlySpeed())) {
            sendOutOfRangeMessage(sender);
            return;
        }
        player.setFlySpeed(speed);
        String speedType = LocaleHandler.getInstance().getFlyspeed();
        String speedString = String.valueOf(speed);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString, setByOther, setOther);
            return;
        }
        sendOwnMessage(speedType, player, speedString, setOwn);
    }

    @Override
    public void resetSpeedLogic(CommandSender sender, Player player, String[] args) {
        player.setWalkSpeed(defaultSpeed);
        String speedType = LocaleHandler.getInstance().getWalkspeed();
        String speedString = String.valueOf(defaultSpeed * 10f);
        player.setFlySpeed(defaultSpeed);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString, resetByOther, resetOther);
            return;
        }
        sendOwnMessage(speedType, player, speedString, resetOwn);
    }

    @Override
    public void getSpeedLogic(CommandSender sender, Player player, String[] args) {
        String speedType = LocaleHandler.getInstance().getWalkspeed();
        String speedString = String.valueOf(player.getFlySpeed() * 10f);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString, getOther, null);
            return;
        }
        sendOwnMessage(speedType, player, speedString, getOwn);
    }
}
