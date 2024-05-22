package simplexity.adminhax.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

public class Heal implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0) {
            Player player = Util.checkAdminPerms(Util.FEED_OTHER_PERMISSION, sender, args);
            if (player == null) {
                Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
                return false;
            }
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            player.setHealth(maxHealth);
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getHealOther(), null, player);
            Util.sendUserMessage(player, LocaleHandler.getInstance().getHealSelf());
            //todo add a cooldown
            return true;
        }
        if (Util.checkIfPlayerAndPerms(sender, Util.FEED_PERMISSION)){
            Player player = (Player) sender;
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            player.setHealth(maxHealth);
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getHealSelf());
            //todo add a cooldown
            return true;
        }
        return false;
    }

}
