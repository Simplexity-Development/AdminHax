package simplexity.adminhax.commands.speed;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.Message;

public class FlySpeed extends AbstractSpeedCommand {


    public FlySpeed(Permission basicPermission, Permission adminPermission, float defaultSpeed) {
        super(basicPermission, adminPermission, defaultSpeed);
    }
    @Override
    public void setSpeedLogic(CommandSender sender, Player player, String[] args, Float speed) {
        super.setSpeedLogic(sender, player, args, speed);
        if (outOfRange(speed, ConfigHandler.getInstance().getMinFlySpeed(), ConfigHandler.getInstance().getMaxFlySpeed())) {
            sendOutOfRangeMessage(sender);
            return;
        }
        speed /= 10f;
        player.setFlySpeed(speed);
        String speedType = Message.INSERT_FLY_SPEED.getMessage();
        String speedString = String.valueOf(speed * 10f);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString, setByOther, setOther);
            return;
        }
        sendOwnMessage(speedType, player, speedString, setOwn);
    }

    @Override
    public void resetSpeedLogic(CommandSender sender, Player player, String[] args) {
        super.resetSpeedLogic(sender, player, args);
        player.setFlySpeed(defaultSpeed);
        String speedType = Message.INSERT_FLY_SPEED.getMessage();
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
        super.getSpeedLogic(sender, player, args);
        String speedType = Message.INSERT_FLY_SPEED.getMessage();
        String speedString = String.valueOf(player.getFlySpeed() * 10f);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString, null, getOther);
            return;
        }
        sendOwnMessage(speedType, player, speedString, getOwn);
    }
}
