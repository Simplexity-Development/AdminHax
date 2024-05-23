package simplexity.adminhax.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

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
                Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
                return false;
            }
            runLogic(player, sender);
            //todo add a cooldown
            return true;
        }
        if (Util.checkIfPlayerAndPerms(sender, basicPermission)) {
            Player playerSender = (Player) sender;
            runLogic(playerSender, sender);
            //todo add a cooldown
            return true;
        }
        return false;
    }

    public void runLogic(Player player, CommandSender sender) {
        sendSelfMessage(player);
        if (!(sender instanceof Player)) return;
        sendOtherMessage(sender, player);
    }

    public abstract void sendSelfMessage(Player player);

    public abstract void sendOtherMessage(CommandSender sender, Player player);
}
