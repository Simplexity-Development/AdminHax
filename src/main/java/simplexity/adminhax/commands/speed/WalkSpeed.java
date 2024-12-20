package simplexity.adminhax.commands.speed;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.Message;

public class WalkSpeed extends AbstractSpeedCommand {
    public WalkSpeed(Permission basicPermission, Permission adminPermission, float defaultSpeed) {
        super(basicPermission, adminPermission, defaultSpeed);
    }

    public void setSpeedLogic(CommandSender sender, Player player, String[] args, Float speed) {
        super.setSpeedLogic(sender, player, args, speed);
        if (outOfRange(speed, ConfigHandler.getInstance().getMinWalkSpeed(), ConfigHandler.getInstance().getMaxWalkSpeed())) {
            sendOutOfRangeMessage(sender);
            return;
        }
        speed /= 10f;
        player.setWalkSpeed(speed);
        String speedType = Message.INSERT_WALK_SPEED.getMessage();
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
        player.setWalkSpeed(defaultSpeed);
        String speedType = Message.INSERT_WALK_SPEED.getMessage();
        String speedString = String.valueOf(defaultSpeed * 10f);
        player.setWalkSpeed(defaultSpeed);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString, resetByOther, resetOther);
            return;
        }
        sendOwnMessage(speedType, player, speedString, resetOwn);
    }

    @Override
    public void getSpeedLogic(CommandSender sender, Player player, String[] args) {
        super.getSpeedLogic(sender, player, args);
        String speedType = Message.INSERT_WALK_SPEED.getMessage();
        String speedString = String.valueOf(player.getWalkSpeed() * 10f);
        if (isRunningOnAnotherPlayer()) {
            sendOtherMessage(speedType, sender, player, speedString,  null, getOther);
            return;
        }
        sendOwnMessage(speedType, player, speedString, getOwn);
    }
}
