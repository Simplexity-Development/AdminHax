package simplexity.adminhax.commands.basic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;
import simplexity.adminhax.config.Message;

public class ReloadPlugin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ConfigHandler.getInstance().reloadConfigValues();
        LocaleHandler.getInstance().reloadLocale();
        sender.sendRichMessage(Message.PLUGIN_MESSAGES_CONFIG_RELOADED.getMessage());
        return true;
    }
}
