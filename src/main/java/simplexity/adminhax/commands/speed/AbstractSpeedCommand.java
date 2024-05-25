package simplexity.adminhax.commands.speed;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;

import java.util.List;
import java.util.Set;

public abstract class AbstractSpeedCommand implements TabExecutor {

    public final Permission basicPermission;
    public final Permission adminPermission;
    public final float defaultSpeed;
    public boolean senderHasAdminPermission;
    public boolean senderHasBasicPermission;
    public boolean runningOnAnotherPlayer;
    public String setByOther;
    public String setOther;
    public String setOwn;
    public String resetByOther;
    public String resetOther;
    public String resetOwn;
    public String getOther;
    public String getOwn;

    public AbstractSpeedCommand(Permission basicPermission, Permission adminPermission, float defaultSpeed) {
        this.basicPermission = basicPermission;
        this.adminPermission = adminPermission;
        this.defaultSpeed = defaultSpeed;
    }

    private static final String SET_ARG = "set";
    private static final String RESET_ARG = "reset";
    private static final String GET_ARG = "get";
    private static final Set<String> VALID_ARGS = Set.of(SET_ARG, RESET_ARG, GET_ARG);
    // /speed [get|set|reset] [value|player] [value]

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!passesInitialChecks(sender, args)) return false;
        setPermissionInfo(sender);
        Player player = validatePlayer(sender, args);
        if (player == null) {
            sendInvalidPlayerMessage(sender);
            return false;
        }
        Float speed = getFloatFromArgs(args);
        String arg = args[0];
        switch (arg) {
            case SET_ARG -> setSpeedLogic(sender, player, args, speed);
            case RESET_ARG -> resetSpeedLogic(sender, player, args);
            case GET_ARG -> getSpeedLogic(sender, player, args);
        }
        return true;
    }

    public void setSpeedLogic(CommandSender sender, Player player, String[] args, Float speed) {
        if (speed == null) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidNumber());
            return;
        }
        setByOther = LocaleHandler.getInstance().getSpeedSetByOther();
        setOther = LocaleHandler.getInstance().getSpeedSetOther();
        setOwn = LocaleHandler.getInstance().getSpeedSetOwn();
    }

    public void resetSpeedLogic(CommandSender sender, Player player, String[] args) {
        resetByOther = LocaleHandler.getInstance().getSpeedResetByOther();
        resetOther = LocaleHandler.getInstance().getSpeedResetOther();
        resetOwn = LocaleHandler.getInstance().getSpeedResetOwn();
    }

    public void getSpeedLogic(CommandSender sender, Player player, String[] args) {
        getOther = LocaleHandler.getInstance().getSpeedGetOther();
        getOwn = LocaleHandler.getInstance().getSpeedGetOwn();
    }

    private void setSenderHasBasicPermission(CommandSender sender) {
        senderHasBasicPermission = sender.hasPermission(basicPermission);
    }

    private void setSenderHasAdminPermission(CommandSender sender) {
        senderHasAdminPermission = sender.hasPermission(adminPermission);
    }


    private boolean passesInitialChecks(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sendInvalidCommandMessage(sender);
            return false;
        }
        if (!VALID_ARGS.contains(args[0])) {
            sendInvalidCommandMessage(sender);
            return false;
        }
        return true;
    }

    private void setPermissionInfo(CommandSender sender) {
        setSenderHasAdminPermission(sender);
        setSenderHasBasicPermission(sender);
    }

    private Float getFloatFromArgs(String[] args) {
        float parsedFloat;
        if (args.length < 2) {
            return null;
        }
        if (runningOnAnotherPlayer && args.length < 3) {
            return null;
        } else if (runningOnAnotherPlayer) {
            try {
                parsedFloat = Float.parseFloat(args[2]);
            } catch (NumberFormatException e) {
                return null;
            }
            return parsedFloat;
        }
        try {
            parsedFloat = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            return null;
        }
        return parsedFloat;
    }

    private Player checkIfSenderIsAPlayer(CommandSender sender) {
        if (!(sender instanceof Player playerSender)) {
            return null;
        }
        return playerSender;
    }

    private Player validatePlayer(CommandSender sender, String[] args) {
        Player player = null;
        if (args.length < 2 || !senderHasAdminPermission) {
            runningOnAnotherPlayer = false;
        } else {
            player = AdminHax.getInstance().getServer().getPlayer(args[1]);
        }
        if (player == null) {
            player = checkIfSenderIsAPlayer(sender);
        } else {
            runningOnAnotherPlayer = true;
        }
        return player;
    }


    public boolean outOfRange(float speed, float min, float max) {
        return !(speed >= min) || !(speed <= max);
    }

    public void sendOtherMessage(String speedType, CommandSender sender, Player player, String speed, String byOtherMessage, String setOtherMessage) {
        Util.sendUserMessage(player, byOtherMessage, speed, speedType, sender);
        Util.sendUserMessage(sender, setOtherMessage, speed, speedType, player);
    }

    public void sendOwnMessage(String speedtype, Player player, String speed, String setOwnMessage) {
        Util.sendUserMessage(player, setOwnMessage, speed, speedtype, null);
    }

    private void sendInvalidCommandMessage(CommandSender sender) {
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidCommand());
    }

    private void sendInvalidPlayerMessage(CommandSender sender) {
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
    }

    public void sendOutOfRangeMessage(CommandSender sender) {
        sender.sendRichMessage(LocaleHandler.getInstance().getNotInRange(),
                Placeholder.parsed("min", String.valueOf(ConfigHandler.getInstance().getMinFlySpeed())),
                Placeholder.parsed("max", String.valueOf(ConfigHandler.getInstance().getMaxFlySpeed())));
    }

    public boolean isRunningOnAnotherPlayer() {
        return runningOnAnotherPlayer;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission(basicPermission)) return List.of();
        if (args.length < 2 ) {
            return VALID_ARGS.stream().toList();
        }
        return null;
    }

}

