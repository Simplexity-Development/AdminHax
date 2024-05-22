package simplexity.adminhax.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpeedCommand implements TabExecutor {

    private final Permission basicPermission;
    private final Permission adminPermission;
    private final boolean isFlySpeed;

    public AbstractSpeedCommand(Permission basicPermission, Permission adminPermission, boolean isFlySpeed) {
        this.basicPermission = basicPermission;
        this.adminPermission = adminPermission;
        this.isFlySpeed = isFlySpeed;
    }

    private static final List<String> tabComplete = new ArrayList<>();
    private static final String SET_ARG = "set";
    private static final String RESET_ARG = "reset";
    private static final String GET_ARG = "get";


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getNotEnoughArguments());
            return false;
        }
        String action = args[0].toLowerCase();

        switch (action) {
            case GET_ARG -> handleGetSpeed(sender, args, isFlySpeed);
            case RESET_ARG -> handleResetSpeed(sender, args, isFlySpeed);
            case SET_ARG -> handleSetSpeed(sender, args, isFlySpeed);
            default -> Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidCommand());
        }

        return true;
    }

    private static void handleGetSpeed(CommandSender sender, String[] args, boolean isFlySpeed) {
        if (args.length == 1) {
            SpeedLogic.getOwnSpeed(sender, isFlySpeed);
        } else {
            SpeedLogic.getOtherSpeed(sender, args[1], isFlySpeed);
        }
    }

    private void handleResetSpeed(CommandSender sender, String[] args, boolean isFlySpeed) {
        if (args.length == 1) {
            SpeedLogic.resetOwnSpeed(sender, isFlySpeed);
        } else {
            SpeedLogic.resetOtherSpeed(sender, args[1], isFlySpeed);
        }
    }

    private void handleSetSpeed(CommandSender sender, String[] args, boolean isFlySpeed) {
        if (args.length < 2) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getNotEnoughArguments());
            return;
        }
        if (args.length == 2) {
            SpeedLogic.setOwnSpeed(sender, args[1], isFlySpeed);
        } else {
            SpeedLogic.setOtherPlayerSpeed(sender, args[1], args[2], isFlySpeed);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            tabComplete.clear();
            tabComplete.add(GET_ARG);
            tabComplete.add(RESET_ARG);
            tabComplete.add(SET_ARG);
        }
        if (args.length == 2 && sender.hasPermission(adminPermission)) {
            tabComplete.clear();
            return null;
        }
        if (args.length == 3 && args[1].equalsIgnoreCase(SET_ARG)) {
            tabComplete.clear();
            return List.of("");
        }
        return tabComplete;
    }
}

