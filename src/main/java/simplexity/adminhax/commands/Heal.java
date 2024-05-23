package simplexity.adminhax.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

public class Heal extends AbstractHaxCommand {

    public Heal(Permission basicPermission, Permission adminPermission) {
        super(basicPermission, adminPermission);
    }

    @Override
    public void runLogic(Player player, CommandSender sender){
        double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(health);
        super.runLogic(player, sender);
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
