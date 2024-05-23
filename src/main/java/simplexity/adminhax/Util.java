package simplexity.adminhax;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.config.LocaleHandler;

public class Util {
    private static final MiniMessage miniMessage = AdminHax.getMiniMessage();
    public static final NamespacedKey FLY_STATUS = new NamespacedKey(AdminHax.getInstance(), "flystatus");
    public static final Permission WALK_SPEED_PERMISSION = new Permission("adminhax.commands.speed.walk");
    public static final Permission WALK_SPEED_OTHER_PERMISSION =  new Permission("adminhax.commands.other.speed.walk");
    public static final Permission FLY_SPEED_PERMISSION =  new Permission("adminhax.commands.speed.fly");
    public static final Permission FLY_SPEED_OTHER_PERMISSION =  new Permission("adminhax.commands.other.speed.fly");
    public static final Permission FLY_PERMISSION =  new Permission("adminhax.commands.fly");
    public static final Permission FLY_OTHER_PERMISSION =  new Permission("adminhax.commands.other.fly");
    public static final Permission REPAIR_PERMISSION =  new Permission("adminhax.repair");
    public static final Permission REPAIR_OTHER_PERMISSION =  new Permission("adminhax.repair.other");
    public static final Permission HEAL_PERMISSION =  new Permission("adminhax.heal");
    public static final Permission HEAL_OTHER_PERMISSION =  new Permission("adminhax.heal.other");
    public static final Permission FEED_PERMISSION =  new Permission("adminhax.commands.feed");
    public static final Permission FEED_OTHER_PERMISSION =  new Permission("adminhax.commands.other.feed");
    public static final Permission SMITE_PERMISSION =  new Permission("adminhax.smite");
    public static final Permission GODMODE_PERMISSION =  new Permission("adminhax.godmode");

    public static void sendUserMessage(CommandSender sender, String message) {
        sendUserMessage(sender, message, null, null, null);
    }

    public static void sendUserMessage(CommandSender sender, String message, String value) {
        sendUserMessage(sender, message, value, null, null);
    }

    public static void sendUserMessage(CommandSender sender, String message, String value, CommandSender userToParse) {
        sendUserMessage(sender, message, value, null, userToParse);
    }

    public static void sendUserMessage(CommandSender sender, String message, String value, String speedType, CommandSender userToParse) {
        if (message == null || message.isEmpty()) return;
        Component parsedName = (userToParse == null) ? Component.empty() : userToParse.name();
        value = (value == null) ? "" : value;
        speedType = (speedType == null) ? "" : speedType;

        sender.sendMessage(miniMessage.deserialize(message,
                Placeholder.parsed("prefix", LocaleHandler.getInstance().getPrefix()),
                Placeholder.parsed("value", value),
                Placeholder.parsed("speedtype", speedType),
                Placeholder.component("target", parsedName),
                Placeholder.component("initiator", parsedName)));
    }

    public static Player getPlayerFromArgs(Permission runForOtherPerm, CommandSender sender, String[] args) {
        if (!sender.hasPermission(runForOtherPerm)) {
            sendUserMessage(sender, LocaleHandler.getInstance().getInvalidCommand());
            return null;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(args[0]);
        if (player == null) {
            sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
        }
        return player;
    }

    public static boolean checkIfPlayerAndPerms(CommandSender sender, Permission permission) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(miniMessage.deserialize(LocaleHandler.getInstance().getMustBePlayer()));
            return false;
        }
        if (!player.hasPermission(permission)) {
            sender.sendMessage(miniMessage.deserialize(LocaleHandler.getInstance().getNoPermission()));
            return false;
        }
        return true;
    }
}

