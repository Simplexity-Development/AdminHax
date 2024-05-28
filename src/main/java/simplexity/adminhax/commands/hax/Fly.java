package simplexity.adminhax.commands.hax;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;
import simplexity.adminhax.fly.FlyLogic;

public class Fly extends AbstractHaxCommand {


    public Fly(Permission basicPermission, Permission adminPermission) {
        super(basicPermission, adminPermission);
    }

    @Override
    public void runLogic(Player player, CommandSender sender, String[] args, boolean runningOnOther) {
        FlyLogic.switchFlyState(player);
        super.runLogic(player, sender, args, runningOnOther);
    }

    @Override
    public void sendSelfMessage(Player player) {
        String value = getValueString(player);
        Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetOwn(), value);
    }

    @Override
    public void sendOtherMessage(CommandSender sender, Player player) {
        String value = getValueString(player);
        Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetOwn(), value);
        Util.sendUserMessage(sender, LocaleHandler.getInstance().getFlySetOther(), value, player);
    }

    private String getValueString(Player player){
        String value;
        if (FlyLogic.isFlyEnabled(player)) {
            value = LocaleHandler.getInstance().getEnabled();
        } else {
            value = LocaleHandler.getInstance().getDisabled();
        }
        return value;
    }
}
