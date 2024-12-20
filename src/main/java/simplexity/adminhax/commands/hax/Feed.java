package simplexity.adminhax.commands.hax;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.util.Util;
import simplexity.adminhax.config.Message;

public class Feed extends AbstractHaxCommand {
    public Feed(Permission basicPermission, Permission adminPermission) {
        super(basicPermission, adminPermission);
    }

    @Override
    public void runLogic(Player player, CommandSender sender, String[] args, boolean runningOnOther) {
        player.setFoodLevel(20);
        player.setSaturation(20f);
        super.runLogic(player, sender, args, runningOnOther);

    }

    @Override
    public void sendSelfMessage(Player player) {
        Util.sendUserMessage(player, Message.FEED_SELF.getMessage());
    }

    @Override
    public void sendOtherMessage(CommandSender sender, Player player) {
        Util.sendUserMessage(sender, Message.FEED_OTHER.getMessage(), null, player);
    }

}
