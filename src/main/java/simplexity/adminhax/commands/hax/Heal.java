package simplexity.adminhax.commands.hax;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.util.Util;
import simplexity.adminhax.config.Message;

public class Heal extends AbstractHaxCommand {

    public Heal(Permission basicPermission, Permission adminPermission) {
        super(basicPermission, adminPermission);
    }

    @Override
    public void runLogic(Player player, CommandSender sender, String[] args, boolean runningOnOther){
        double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(health);
        super.runLogic(player, sender, args, runningOnOther);
    }

    @Override
    public void sendSelfMessage(Player player) {
        Util.sendUserMessage(player, Message.HEAL_SELF.getMessage());
    }

    @Override
    public void sendOtherMessage(CommandSender sender, Player player) {
        Util.sendUserMessage(sender, Message.HEAL_OTHER.getMessage(), null, player);
    }

}
