package simplexity.adminhax.commands.hax;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

public class Feed extends AbstractHaxCommand {
    public Feed(Permission basicPermission, Permission adminPermission) {
        super(basicPermission, adminPermission);
    }

    @Override
    public void runLogic(Player player, CommandSender sender, String[] args) {
        player.setFoodLevel(20);
        player.setSaturation(20f);
        super.runLogic(player, sender, args);

    }

    @Override
    public void sendSelfMessage(Player player) {
        Util.sendUserMessage(player, LocaleHandler.getInstance().getHealSelf());
    }

    @Override
    public void sendOtherMessage(CommandSender sender, Player player) {
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getHealOther(), null, player);
    }

}
