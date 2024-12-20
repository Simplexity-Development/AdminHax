package simplexity.adminhax.commands.basic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.Message;
import simplexity.adminhax.util.Permissions;
import simplexity.adminhax.util.RenamePermission;

public class RenameItem implements CommandExecutor {

    private final MiniMessage miniMessage = AdminHax.getMiniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ERROR_MUST_BE_PLAYER.getMessage());
            return false;
        }
        String renameString = String.join(" ", args);
        renameString = renameString.trim();
        String strippedString = miniMessage.stripTags(renameString);
        if ( strippedString.length() > ConfigHandler.getInstance().getMaxRenameCharacters() && !sender.hasPermission(Permissions.BYPASS_RENAME_LENGTH)) {
            player.sendRichMessage(Message.ERROR_NAME_TOO_LONG.getMessage());
            return false;
        }
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem.isEmpty() || heldItem.getType().isEmpty()) {
            player.sendRichMessage(Message.ERROR_MUST_HOLD_ITEM.getMessage());
            return false;
        }
        Component newName = parsedName(player, renameString);
        setItemName(newName, heldItem);
        player.sendRichMessage(Message.RENAME_SUCCESSFUL.getMessage(),
                Placeholder.unparsed("item", heldItem.translationKey()),
                Placeholder.component("name", newName));
        return true;
    }
    //Stolen from https://github.com/YouHaveTrouble/JustChat @YouHaveTrouble - I'm just gonna continue using this over and over
    private Component parsedName(Player player, String name) {
        TagResolver.Builder tagResolver = TagResolver.builder();
        for (RenamePermission permission : RenamePermission.values()) {
            if (player.hasPermission(permission.getPermission())) {
                tagResolver.resolver(permission.getTagResolver());
            }
        }
        MiniMessage nameParser = MiniMessage.builder().tags(tagResolver.build()).build();
        return nameParser.deserialize(name).decoration(TextDecoration.ITALIC, false);
    }

    private void setItemName(Component name, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);
    }
}
