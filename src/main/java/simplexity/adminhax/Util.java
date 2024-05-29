package simplexity.adminhax;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.adminhax.config.LocaleHandler;

public class Util {
    private static final MiniMessage miniMessage = AdminHax.getMiniMessage();
    public static final NamespacedKey FLY_STATUS = new NamespacedKey(AdminHax.getInstance(), "flystatus");
    public static final NamespacedKey GODMODE_STATUS = new NamespacedKey(AdminHax.getInstance(), "godmodestatus");
    public static final Permission WALK_SPEED_PERMISSION = new Permission("adminhax.commands.speed.walk");
    public static final Permission WALK_SPEED_OTHER_PERMISSION =  new Permission("adminhax.commands.other.speed.walk");
    public static final Permission FLY_SPEED_PERMISSION =  new Permission("adminhax.commands.speed.fly");
    public static final Permission FLY_SPEED_OTHER_PERMISSION =  new Permission("adminhax.commands.other.speed.fly");
    public static final Permission FLY_PERMISSION =  new Permission("adminhax.commands.fly");
    public static final Permission FLY_OTHER_PERMISSION =  new Permission("adminhax.commands.other.fly");
    public static final Permission REPAIR_HAND_PERMISSION =  new Permission("adminhax.commands.repair.hand");
    public static final Permission REPAIR_HAND_OTHER_PERMISSION =  new Permission("adminhax.commands.other.repair.hand");
    public static final Permission REPAIR_HOT_BAR_PERMISSION =  new Permission("adminhax.commands.repair.hotbar");
    public static final Permission REPAIR_HOT_BAR_OTHER_PERMISSION =  new Permission("adminhax.commands.other.repair.hotbar");
    public static final Permission REPAIR_ARMOR_PERMISSION =  new Permission("adminhax.commands.repair.armor");
    public static final Permission REPAIR_ARMOR_OTHER_PERMISSION =  new Permission("adminhax.commands.other.repair.armor");
    public static final Permission REPAIR_INVENTORY_PERMISSION =  new Permission("adminhax.commands.repair.inventory");
    public static final Permission REPAIR_INVENTORY_OTHER_PERMISSION =  new Permission("adminhax.commands.other.repair.inventory");
    public static final Permission REPAIR_ALL_PERMISSION =  new Permission("adminhax.commands.repair.all");
    public static final Permission REPAIR_ALL_OTHER_PERMISSION =  new Permission("adminhax.commands.other.repair.all");
    public static final Permission HEAL_PERMISSION =  new Permission("adminhax.heal");
    public static final Permission HEAL_OTHER_PERMISSION =  new Permission("adminhax.heal.other");
    public static final Permission FEED_PERMISSION =  new Permission("adminhax.commands.feed");
    public static final Permission FEED_OTHER_PERMISSION =  new Permission("adminhax.commands.other.feed");
    public static final Permission SMITE_PERMISSION =  new Permission("adminhax.smite");
    public static final Permission GODMODE_PERMISSION =  new Permission("adminhax.commands.godmode");
    public static final Permission GODMODE_GET_PERMISSION =  new Permission("adminhax.commands.godmode.get");
    public static final Permission GODMODE_OTHER_PERMISSION =  new Permission("adminhax.commands.other.godmode");
    public static final Permission GODMODE_GET_OTHER_PERMISSION =  new Permission("adminhax.commands.other.godmode.get");

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

    public static void flipPDCState(Player player, NamespacedKey namespacedKey, boolean defaultState){
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        if (playerPDC.getOrDefault(namespacedKey, PersistentDataType.BOOLEAN, defaultState)){
            playerPDC.set(namespacedKey, PersistentDataType.BOOLEAN, false);
        } else {
            playerPDC.set(namespacedKey, PersistentDataType.BOOLEAN, true);
        }
    }

    public static boolean isPDCStateEnabled(Player player, NamespacedKey namespacedKey, boolean defaultState){
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        return playerPDC.getOrDefault(namespacedKey, PersistentDataType.BOOLEAN, defaultState);
    }
}

