package simplexity.adminhax.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;

public class SpeedLogic {
    private static final float DEFAULT_FLY_SPEED = 0.1f;
    private static final float DEFAULT_WALK_SPEED = 0.2f;

    public static void getOwnSpeed(CommandSender sender, boolean isFlySpeed) {
        if (!Util.checkIfPlayerAndPerms(sender, isFlySpeed ? Util.FLY_SPEED_PERMISSION : Util.WALK_SPEED_PERMISSION))
            return;
        Player player = (Player) sender;
        float speed = isFlySpeed ? player.getFlySpeed() * 10 : player.getWalkSpeed() * 10;
        String speedType = isFlySpeed ? LocaleHandler.getInstance().getFlyspeed() : LocaleHandler.getInstance().getWalkspeed();
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getSpeedGetOwn(), String.valueOf(speed), speedType, player);
    }

    public static void setOwnSpeed(CommandSender sender, String speedValue, boolean isFlySpeed) {
        if (!Util.checkIfPlayerAndPerms(sender, isFlySpeed ? Util.FLY_SPEED_PERMISSION : Util.WALK_SPEED_PERMISSION))
            return;
        Player player = (Player) sender;
        setSpeed(player, speedValue, isFlySpeed, sender, true);
    }

    public static void resetOwnSpeed(CommandSender sender, boolean isFlySpeed) {
        if (!Util.checkIfPlayerAndPerms(sender, isFlySpeed ? Util.FLY_SPEED_PERMISSION : Util.WALK_SPEED_PERMISSION))
            return;
        Player player = (Player) sender;
        resetSpeed(player, isFlySpeed, sender, true);
    }

    public static void getOtherSpeed(CommandSender sender, String playerName, boolean isFlySpeed) {
        if (!sender.hasPermission(isFlySpeed ? Util.FLY_SPEED_OTHER_PERMISSION : Util.WALK_SPEED_OTHER_PERMISSION)) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getNoPermission());
            return;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(playerName);
        if (player == null) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
            return;
        }
        float speed = isFlySpeed ? player.getFlySpeed() * 10 : player.getWalkSpeed() * 10;
        String speedType = isFlySpeed ? LocaleHandler.getInstance().getFlyspeed() : LocaleHandler.getInstance().getWalkspeed();
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getSpeedGetOther(), String.valueOf(speed), speedType, player);
    }

    public static void setOtherPlayerSpeed(CommandSender sender, String playerName, String speedValue, boolean isFlySpeed) {
        if (!sender.hasPermission(isFlySpeed ? Util.FLY_SPEED_OTHER_PERMISSION : Util.WALK_SPEED_OTHER_PERMISSION)) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getNoPermission());
            return;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(playerName);
        if (player == null) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
            return;
        }
        setSpeed(player, speedValue, isFlySpeed, sender, false);
    }

    public static void resetOtherSpeed(CommandSender sender, String playerName, boolean isFlySpeed) {
        if (!sender.hasPermission(isFlySpeed ? Util.FLY_SPEED_OTHER_PERMISSION : Util.WALK_SPEED_OTHER_PERMISSION)) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getNoPermission());
            return;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(playerName);
        if (player == null) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
            return;
        }
        resetSpeed(player, isFlySpeed, sender, false);
    }

    private static void setSpeed(Player player, String speedValue, boolean isFlySpeed, CommandSender sender, boolean isOwn) {
        float speed;
        try {
            speed = Float.parseFloat(speedValue) / 10f;
        } catch (NumberFormatException e) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidNumber());
            return;
        }
        ConfigHandler configHandler = ConfigHandler.getInstance();
        float minSpeed = isFlySpeed ? configHandler.getMinFlySpeed() : configHandler.getMinWalkSpeed();
        float maxSpeed = isFlySpeed ? configHandler.getMaxFlySpeed() : configHandler.getMaxWalkSpeed();
        if (speed < minSpeed / 10f || speed > maxSpeed / 10f) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getNotInRange().replace("<min>", String.valueOf(minSpeed)).replace("<max>", String.valueOf(maxSpeed)));
            return;
        }
        if (isFlySpeed) {
            player.setFlySpeed(speed);
        } else {
            player.setWalkSpeed(speed);
        }
        String speedType = isFlySpeed ? LocaleHandler.getInstance().getFlyspeed() : LocaleHandler.getInstance().getWalkspeed();
        String messageToSend = isOwn ? LocaleHandler.getInstance().getSpeedSetOwn() : LocaleHandler.getInstance().getSpeedSetOther();
        Util.sendUserMessage(sender, messageToSend, speedValue, speedType, player);
        if (!isOwn) {
            Util.sendUserMessage(player, LocaleHandler.getInstance().getSpeedSetByOther(), speedValue, speedType, sender);
        }
    }

    private static void resetSpeed(Player player, boolean isFlySpeed, CommandSender sender, boolean isOwn) {
        if (isFlySpeed) {
            player.setFlySpeed(DEFAULT_FLY_SPEED);
        } else {
            player.setWalkSpeed(DEFAULT_WALK_SPEED);
        }
        String speedType = isFlySpeed ? LocaleHandler.getInstance().getFlyspeed() : LocaleHandler.getInstance().getWalkspeed();
        String messageToSend = isOwn ? LocaleHandler.getInstance().getSpeedResetOwn() : LocaleHandler.getInstance().getSpeedResetOther();
        Util.sendUserMessage(sender, messageToSend, null, speedType, player);
        if (!isOwn) {
            Util.sendUserMessage(player, LocaleHandler.getInstance().getSpeedResetByOther(), null, speedType, sender);
        }
    }
}
