package simplexity.adminhax.commands.withargs;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.util.Permissions;
import simplexity.adminhax.util.Util;
import simplexity.adminhax.config.Message;

public class GodMode extends AbstractArgsCommands {
    public static final String GET_ARG = "get";
    public static final String TOGGLE_ARG = "toggle";
    private NamespacedKey godModeStatus = Util.GODMODE_STATUS;

    public GodMode(Permission DEFAULT_PERMISSION, String DEFAULT_ARG) {
        super(DEFAULT_PERMISSION, DEFAULT_ARG);
    }


    public void setupMaps() {
        validArgs.add(GET_ARG);
        validArgs.add(TOGGLE_ARG);
        argToBasicPerm.put(GET_ARG, Permissions.GODMODE_GET_PERMISSION);
        argToBasicPerm.put(TOGGLE_ARG, Permissions.GODMODE_PERMISSION);
        argToAdminPerm.put(TOGGLE_ARG, Permissions.GODMODE_OTHER_PERMISSION);
        argToAdminPerm.put(GET_ARG, Permissions.GODMODE_GET_OTHER_PERMISSION);
    }

    @Override
    public void runLogic(CommandSender sender, Player player, String arg) {
        switch (arg.toLowerCase()) {
            case GET_ARG:
                if (!runningOnOther) {
                    sendOwnMessage(player, Message.GODMODE_GET_SELF.getMessage());
                } else {
                    sendOtherMessage(sender, Message.GODMODE_GET_OTHER.getMessage(), player);
                }
                return;
            case TOGGLE_ARG:
                toggleGodmode(player);
                if (!runningOnOther) {
                    sendOwnMessage(player, Message.GODMODE_SELF.getMessage());
                } else {
                    sendOtherMessage(sender, Message.GODMODE_OTHER.getMessage(), player);
                }
                break;
            default:
                Util.sendUserMessage(sender, Message.ERROR_INVALID_COMMAND.getMessage());
        }
    }

    public void toggleGodmode(Player player) {
        player.setInvulnerable(!player.isInvulnerable());
        Util.flipPDCState(player, godModeStatus, false);
    }

    public String getGodmodeStatus(Player player) {
        String value;
        if (Util.isPDCStateEnabled(player, godModeStatus, false)) {
            value = Message.INSERT_ENABLED.getMessage();
        } else {
            value = Message.INSERT_DISABLED.getMessage();
        }
        return value;
    }

    public void sendOwnMessage(Player player, String getOrSet) {
        Util.sendUserMessage(player, getOrSet, getGodmodeStatus(player));
    }

    public void sendOtherMessage(CommandSender sender, String getOrSet, Player player) {
        Util.sendUserMessage(sender, getOrSet, getGodmodeStatus(player), player);
    }
}
