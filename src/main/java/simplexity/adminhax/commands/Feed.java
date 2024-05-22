package simplexity.adminhax.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

public class Feed implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0) {
            Player player = Util.checkAdminPerms(Util.FEED_OTHER_PERMISSION, sender, args);
            if (player == null) {
                Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
                return false;
            }
            player.setFoodLevel(20);
            player.setSaturation(20);
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getFeedOther(), null, player);
            Util.sendUserMessage(player, LocaleHandler.getInstance().getFeedSelf());
            //todo add a cooldown
            return true;
        }
        if (Util.checkIfPlayerAndPerms(sender, Util.FEED_PERMISSION)){
            ((Player) sender).setFoodLevel(20);
            ((Player) sender).setSaturation(20);
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getFeedSelf());
            //todo add a cooldown
            return true;
        }
        return false;
    }

}
