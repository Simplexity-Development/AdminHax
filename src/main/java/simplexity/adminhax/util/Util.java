package simplexity.adminhax.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.config.Message;

public class Util {
    private static final MiniMessage miniMessage = AdminHax.getMiniMessage();
    public static final NamespacedKey FLY_STATUS = new NamespacedKey(AdminHax.getInstance(), "flystatus");
    public static final NamespacedKey GODMODE_STATUS = new NamespacedKey(AdminHax.getInstance(), "godmodestatus");

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
                Placeholder.parsed("prefix", Message.INSERT_PREFIX.getMessage()),
                Placeholder.parsed("value", value),
                Placeholder.parsed("speedtype", speedType),
                Placeholder.component("target", parsedName),
                Placeholder.component("initiator", parsedName)));
    }

    public static Player getPlayerFromArgs(Permission runForOtherPerm, CommandSender sender, String[] args) {
        if (!sender.hasPermission(runForOtherPerm)) {
            sendUserMessage(sender, Message.ERROR_INVALID_COMMAND.getMessage());
            return null;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(args[0]);
        if (player == null) {
            sendUserMessage(sender, Message.ERROR_INVALID_PLAYER.getMessage());
        }
        return player;
    }

    public static boolean checkIfPlayerAndPerms(CommandSender sender, Permission permission) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(miniMessage.deserialize(Message.ERROR_MUST_BE_PLAYER.getMessage()));
            return false;
        }
        if (!player.hasPermission(permission)) {
            sender.sendMessage(miniMessage.deserialize(Message.ERROR_NO_PERMISSION.getMessage()));
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

    public static void setPdcState(Player player, NamespacedKey namespacedKey, boolean state){
        PersistentDataContainer playerPdc = player.getPersistentDataContainer();
        playerPdc.set(namespacedKey, PersistentDataType.BOOLEAN, state);
    }

    public static boolean isPDCStateEnabled(Player player, NamespacedKey namespacedKey, boolean defaultState){
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();
        return playerPDC.getOrDefault(namespacedKey, PersistentDataType.BOOLEAN, defaultState);
    }
}

