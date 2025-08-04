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
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemStack itemInHelmSlot = player.getInventory().getHelmet();
        if (itemInHelmSlot == null && itemInHand.isEmpty()) {
            player.sendRichMessage(Message.ERROR_NO_ITEMS_HAT.getMessage());
            return false;
        }
        if ((itemInHelmSlot != null && !itemInHelmSlot.isEmpty()) && (
                itemInHelmSlot.getItemMeta().hasEnchant(Enchantment.BINDING_CURSE) &&
                ConfigHandler.getInstance().isHatRespectsCurseOfBinding()
        )){
            player.sendRichMessage(Message.ERROR_CURSE_OF_BINDING.getMessage());
            return false;
        }
        if (ConfigHandler.getInstance().getDisabledHatItems().contains(itemInHand.getType())) {
            player.sendRichMessage(Message.ERROR_HAT_NOT_ALLOWED.getMessage());
            return false;
        }
        if (itemInHand.getAmount() > 1) {
            player.getInventory().setHelmet(itemInHand.asOne());
            int amt = itemInHand.getAmount() - 1;
            itemInHand.setAmount(amt);
            player.getInventory().setItemInMainHand(itemInHand);
            if (itemInHelmSlot != null) {
                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(itemInHelmSlot);
                if (!leftover.isEmpty()) {
                    for (Integer integer : leftover.keySet()) {
                        player.getWorld().dropItem(player.getLocation(), leftover.get(integer));
                    }
                }
            }
            player.sendRichMessage(Message.HAT_SUCCESSFUL.getMessage());
        } else {
            player.getInventory().setHelmet(itemInHand);
            if (itemInHelmSlot != null) player.getInventory().setItemInMainHand(itemInHelmSlot);
            player.sendRichMessage(Message.HAT_SUCCESSFUL.getMessage());
        }
        return true;
    }
}
