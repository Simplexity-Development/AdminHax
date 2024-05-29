package simplexity.adminhax.commands.withargs;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

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
        argToBasicPerm.put(GET_ARG, Util.GODMODE_GET_PERMISSION);
        argToBasicPerm.put(TOGGLE_ARG, Util.GODMODE_PERMISSION);
        argToAdminPerm.put(TOGGLE_ARG, Util.GODMODE_OTHER_PERMISSION);
        argToAdminPerm.put(GET_ARG, Util.GODMODE_GET_OTHER_PERMISSION);
    }

    @Override
    public void runLogic(CommandSender sender, Player player, String arg) {
        switch (arg.toLowerCase()) {
            case GET_ARG:
                if (!runningOnOther) {
                    sendOwnMessage(player, LocaleHandler.getInstance().getGodmodeGetSelf());
                } else {
                    sendOtherMessage(sender, LocaleHandler.getInstance().getGodmodeGetOther(), player);
                }
                return;
            case TOGGLE_ARG:
                toggleGodmode(player);
                if (!runningOnOther) {
                    sendOwnMessage(player, LocaleHandler.getInstance().getGodmodeSelf());
                } else {
                    sendOtherMessage(sender, LocaleHandler.getInstance().getGodmodeOther(), player);
                }
                break;
            default:
                Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidCommand());
        }
    }

    public void toggleGodmode(Player player) {
        player.setInvulnerable(!player.isInvulnerable());
        Util.flipPDCState(player, godModeStatus, false);
    }

    public String getGodmodeStatus(Player player) {
        String value;
        if (Util.isPDCStateEnabled(player, godModeStatus, false)) {
            value = LocaleHandler.getInstance().getEnabled();
        } else {
            value = LocaleHandler.getInstance().getDisabled();
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
