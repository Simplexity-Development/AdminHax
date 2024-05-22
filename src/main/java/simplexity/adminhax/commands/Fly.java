package simplexity.adminhax.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;
import simplexity.adminhax.fly.FlyLogic;

import java.util.List;

public class Fly implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (Util.checkIfPlayerAndPerms(sender, Util.FLY_PERMISSION)) {
                if (FlyLogic.flyEnabled((Player) sender)) {
                    Util.sendUserMessage(sender, LocaleHandler.getInstance().getFlySetOwn(), LocaleHandler.getInstance().getEnabled(), null);
                    return true;
                } else {
                    Util.sendUserMessage(sender, LocaleHandler.getInstance().getFlySetOwn(), LocaleHandler.getInstance().getDisabled(), null);
                    return true;
                }
            } else {
                return false;
            }
        }
        if (!sender.hasPermission(Util.FLY_OTHER_PERMISSION)) return false;
        Player player = AdminHax.getInstance().getServer().getPlayer(args[0]);
        if (player == null) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
            return false;
        }
        if (FlyLogic.flyEnabled(player)) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getFlySetOther(), LocaleHandler.getInstance().getEnabled(), player);
            Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetByOther(), LocaleHandler.getInstance().getEnabled(), sender);
            return true;
        } else {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getFlySetOther(), LocaleHandler.getInstance().getDisabled(), player);
            Util.sendUserMessage(player, LocaleHandler.getInstance().getFlySetOther(), LocaleHandler.getInstance().getDisabled(), sender);
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
