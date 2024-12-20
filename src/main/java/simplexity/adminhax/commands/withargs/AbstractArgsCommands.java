package simplexity.adminhax.commands.withargs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.util.Util;
import simplexity.adminhax.config.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractArgsCommands implements TabExecutor {

    public ArrayList<String> validArgs;
    public Permission DEFAULT_PERMISSION;
    public final String DEFAULT_ARG;
    public static List<String> tabComplete;
    public final HashMap<String, Permission> argToBasicPerm;
    public final HashMap<String, Permission> argToAdminPerm;
    public boolean runningOnOther;

    public AbstractArgsCommands(Permission DEFAULT_PERMISSION, String DEFAULT_ARG){
        this.DEFAULT_PERMISSION = DEFAULT_PERMISSION;
        this.DEFAULT_ARG = DEFAULT_ARG;
        argToBasicPerm = new HashMap<>();
        argToAdminPerm = new HashMap<>();
        tabComplete = new ArrayList<>();
        validArgs = new ArrayList<>();
        setupMaps();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String invalidCommand = Message.ERROR_INVALID_COMMAND.getMessage();
        String mustBePlayer = Message.ERROR_MUST_BE_PLAYER.getMessage();
        String invalidPlayer = Message.ERROR_INVALID_PLAYER.getMessage();
        switch (args.length) {
            case 0: {
                if (!isSenderPlayer(sender)) {
                    Util.sendUserMessage(sender, mustBePlayer);
                    return false;
                }
                if (!sender.hasPermission(DEFAULT_PERMISSION)) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                Player player = (Player) sender;
                runningOnOther = false;
                runLogic(sender, player, DEFAULT_ARG);
                return true;
            }
            case 1: {
                if (!isValidArg(args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                if (!(isSenderPlayer(sender))) {
                    Util.sendUserMessage(sender, mustBePlayer);
                    return false;
                }
                if (!userHasBasicPermission(sender, args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                Player player = (Player) sender;
                runningOnOther = false;
                runLogic(sender, player, args[0]);
                return true;
            }
            case 2: {
                if (!isValidArg(args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                Player player = getPlayerFromArgs(sender, args);
                if (player == null) {
                    if (!userHasAdminPermission(sender, args[0])) {
                        Util.sendUserMessage(sender, invalidCommand);
                        return false;
                    }
                    Util.sendUserMessage(sender, invalidPlayer);
                    return false;
                }
                if (!userHasAdminPermission(sender, args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                runningOnOther = true;
                runLogic(sender, player, args[0]);
                return true;
            }
            default: {
                Util.sendUserMessage(sender, invalidCommand);
                return false;
            }
        }
    }

    public abstract void setupMaps();
    public abstract void runLogic(CommandSender sender, Player player, String arg);

    public boolean isSenderPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public boolean isValidArg(String arg) {
        return validArgs.contains(arg.toLowerCase());
    }

    private boolean userHasBasicPermission(CommandSender sender, String arg) {
        if (sender instanceof Player player) {
            return player.hasPermission(argToBasicPerm.get(arg.toLowerCase()));
        }
        return false;
    }

    private boolean userHasAdminPermission(CommandSender sender, String arg) {
        return sender.hasPermission(argToAdminPerm.get(arg.toLowerCase()));
    }

    private Player getPlayerFromArgs(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return null;
        }
        String type = args[0];
        if (!userHasAdminPermission(sender, type)) {
            Util.sendUserMessage(sender, Message.ERROR_INVALID_COMMAND.getMessage());
            return null;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(args[1]);
        if (player == null) {
            Util.sendUserMessage(sender, Message.ERROR_INVALID_PLAYER.getMessage());
            return null;
        }
        return player;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        tabComplete.clear();
        if (args.length < 2) {
            for (String arg : validArgs) {
                if (sender.hasPermission(argToBasicPerm.get(arg)) || sender.hasPermission(argToAdminPerm.get(arg))) {
                    tabComplete.add(arg);
                }
            }
        }
        if (args.length < 2 && tabComplete.isEmpty()) {
            return List.of();
        }
        if (args.length >= 2) {
            return null;
        }
        return tabComplete;
    }
}
