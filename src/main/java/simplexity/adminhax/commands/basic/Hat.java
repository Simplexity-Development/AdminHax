package simplexity.adminhax.commands.basic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.Message;

import java.util.HashMap;

public class Hat implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ERROR_MUST_BE_PLAYER.getMessage());
            return false;
        }
        ItemStack itemToHat = player.getInventory().getItemInMainHand();
        ItemStack previousHelm = player.getInventory().getHelmet();
        boolean userHadHelmetBefore = (previousHelm != null && !previousHelm.isEmpty());
        if (!userHadHelmetBefore && itemToHat.isEmpty()) {
            player.sendRichMessage(Message.ERROR_NO_HAT_ITEMS.getMessage());
            return false;
        }
        if (userHadHelmetBefore && (previousHelm.getItemMeta().hasEnchant(Enchantment.BINDING_CURSE) &&
                ConfigHandler.getInstance().shouldRespectBindingCurse())) {
            player.sendRichMessage(Message.ERROR_CURSE_OF_BINDING.getMessage());
            return false;
        }
        if (ConfigHandler.getInstance().getDisabledHatItems().contains(itemToHat.getType())) {
            player.sendRichMessage(Message.ERROR_HAT_NOT_ALLOWED.getMessage());
            return false;
        }
        if (itemToHat.isEmpty()) {
            player.getInventory().setHelmet(null);
            player.getInventory().setItemInMainHand(previousHelm);
            player.sendRichMessage(Message.HAT_REMOVED.getMessage());
            return true;
        }
        player.getInventory().setHelmet(itemToHat.asOne());
        itemToHat.subtract();
        if (!userHadHelmetBefore) {
            player.sendRichMessage(Message.HAT_SUCCESSFUL.getMessage());
            return true;
        }
        if (player.getInventory().getItemInMainHand().isEmpty()) {
            player.getInventory().setItemInMainHand(previousHelm);
            player.sendRichMessage(Message.HAT_SUCCESSFUL.getMessage());
            return true;
        }
        HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(previousHelm);
        if (!leftover.isEmpty()) {
            for (Integer index : leftover.keySet()) {
                player.getWorld().dropItem(player.getLocation(), leftover.get(index));
            }
        }
        player.sendRichMessage(Message.HAT_SUCCESSFUL.getMessage());
        return true;
    }
}
