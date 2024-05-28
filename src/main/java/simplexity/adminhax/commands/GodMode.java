package simplexity.adminhax.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.adminhax.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GodMode extends AbstractArgsCommands{
    boolean runningOnOther;
    public static String GET_ARG = "get";
    public static String ON_ARG = "on";
    public static String OFF_ARG = "off";
    public static Set<String> VALID_ARGS = new HashSet<>();
    public static final List<String> tabComplete = new ArrayList<>();
    public final HashMap<String, Permission> argToBasicPerm = new HashMap<>();
    public final HashMap<String, Permission> argToAdminPerm = new HashMap<>();

    public GodMode() {
        super();
    }

    public void setupMaps() {
        VALID_ARGS.add(GET_ARG);
        VALID_ARGS.add(ON_ARG);
        VALID_ARGS.add(OFF_ARG);
        argToBasicPerm.put(GET_ARG, Util.GODMODE_GET_PERMISSION);
        argToBasicPerm.put(ON_ARG, Util.GODMODE_PERMISSION);
        argToBasicPerm.put(OFF_ARG, Util.GODMODE_PERMISSION);
        argToAdminPerm.put(ON_ARG, Util.GODMODE_OTHER_PERMISSION);
        argToAdminPerm.put(OFF_ARG, Util.GODMODE_OTHER_PERMISSION);
        argToAdminPerm.put(GET_ARG, Util.GODMODE_GET_OTHER_PERMISSION);
    }

    @Override
    public void runLogic(CommandSender sender, Player player, String arg) {

    }
}
