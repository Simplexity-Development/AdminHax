package simplexity.adminhax.commands.basic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.config.Message;

public class BroadcastMsg implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendRichMessage(Message.ERROR_INVALID_COMMAND.getMessage());
            return false;
        }
        StringBuilder msg = new StringBuilder();
        for (String arg : args) {
            msg.append(arg).append(" ");
        }
        String message = msg.toString().trim();
        message = Message.BROADCAST_SERVER_PREFIX.getMessage() + message;
        AdminHax.getInstance().getServer().sendMessage(AdminHax.getMiniMessage().deserialize(message));
        return true;
    }
}
