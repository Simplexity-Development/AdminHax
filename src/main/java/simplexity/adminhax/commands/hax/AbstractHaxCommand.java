package simplexity.adminhax.commands.hax;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.util.Util;
import simplexity.adminhax.config.Message;

public abstract class AbstractHaxCommand implements CommandExecutor {

    public final Permission basicPermission;
    public final Permission adminPermission;

    public AbstractHaxCommand(Permission basicPermission, Permission adminPermission) {
        this.basicPermission = basicPermission;
        this.adminPermission = adminPermission;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0) {
            Player player = Util.getPlayerFromArgs(adminPermission, sender, args);
            if (player == null) {
                Util.sendUserMessage(sender, Message.ERROR_INVALID_PLAYER.getMessage());
                return false;
            }
            runLogic(player, sender, args, true);
            //todo add a cooldown
            return true;
        }
        if (Util.checkIfPlayerAndPerms(sender, basicPermission)) {
            Player playerSender = (Player) sender;
            runLogic(playerSender, sender, args, false);
            //todo add a cooldown
            return true;
        }
        return false;
    }

    public void runLogic(Player player, CommandSender sender, String[] args, boolean runningOnOther) {
        if (runningOnOther) {
            sendOtherMessage(sender, player);
            sendSelfMessage(player);
        } else {
            sendSelfMessage(player);
        }
    }

    public abstract void sendSelfMessage(Player player);

    public abstract void sendOtherMessage(CommandSender sender, Player player);
}
