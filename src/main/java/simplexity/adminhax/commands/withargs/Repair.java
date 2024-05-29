package simplexity.adminhax.commands.withargs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.permissions.Permission;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

import java.util.ArrayList;
import java.util.List;

public class Repair extends AbstractArgsCommands {
    public static final String HOT_BAR = "hotbar";
    public static final String INVENTORY = "inventory";
    public static final String ALL = "all";
    public static final String ARMOR = "armor";
    public static final String HAND = "hand";
    private static final MiniMessage miniMessage = AdminHax.getMiniMessage();
    public String cannotBeRepaired;
    public String repairSelf;
    public String repairOther;
    public boolean runningOnOther;

    public Repair(Permission DEFAULT_PERMISSION, String DEFAULT_ARG) {
        super(DEFAULT_PERMISSION, DEFAULT_ARG);
    }

    public void setupMaps() {
        argToBasicPerm.put(HOT_BAR, Util.REPAIR_HOT_BAR_PERMISSION);
        argToBasicPerm.put(INVENTORY, Util.REPAIR_INVENTORY_PERMISSION);
        argToBasicPerm.put(ALL, Util.REPAIR_ALL_PERMISSION);
        argToBasicPerm.put(ARMOR, Util.REPAIR_ARMOR_PERMISSION);
        argToBasicPerm.put(HAND, Util.REPAIR_HAND_PERMISSION);
        argToAdminPerm.put(HOT_BAR, Util.REPAIR_HOT_BAR_OTHER_PERMISSION);
        argToAdminPerm.put(INVENTORY, Util.REPAIR_INVENTORY_OTHER_PERMISSION);
        argToAdminPerm.put(ALL, Util.REPAIR_ALL_OTHER_PERMISSION);
        argToAdminPerm.put(ARMOR, Util.REPAIR_ARMOR_OTHER_PERMISSION);
        argToAdminPerm.put(HAND, Util.REPAIR_HAND_OTHER_PERMISSION);
        validArgs.add(HOT_BAR);
        validArgs.add(INVENTORY);
        validArgs.add(ALL);
        validArgs.add(ARMOR);
        validArgs.add(HAND);
    }

    public void runLogic(CommandSender sender, Player player, String repairType) {
        switch (repairType) {
            case HAND:
                repairHand(player, sender);
                break;
            case HOT_BAR:
                repairHotBar(player, sender);
                break;
            case INVENTORY:
                repairInventory(player, sender);
                break;
            case ALL:
                repairAll(player, sender);
                break;
            case ARMOR:
                repairArmor(player, sender);
                break;
            default:
                Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidCommand());
                break;
        }
    }

    // 0-8 hot bar, 9- 35 inventory, 36-39 armor, 40 offhand
    private void repairHand(Player player, CommandSender sender) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(item.getItemMeta() instanceof Damageable damageable)) {
            Util.sendUserMessage(sender, cannotBeRepaired);
            return;
        }
        if (!damageable.hasDamage()) {
            Util.sendUserMessage(sender, cannotBeRepaired);
            return;
        }
        int currentDamage = damageable.getDamage();
        player.getInventory().getItemInMainHand().damage(-(currentDamage), player);
        sendUserMessage(sender, player, List.of(item));
    }

    private void repairHotBar(Player player, CommandSender sender) {
        List<ItemStack> itemList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getItemMeta() == null) continue;
            if (!(item.getItemMeta() instanceof Damageable damageable)) {
                continue;
            }
            if (!damageable.hasDamage()) {
                continue;
            }
            int currentDamage = damageable.getDamage();
            player.getInventory().getItem(i).damage(-(currentDamage), player);
            itemList.add(item);
        }
        sendUserMessage(sender, player, itemList);
    }

    private void repairInventory(Player player, CommandSender sender) {
        List<ItemStack> itemList = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getItemMeta() == null) continue;
            if (!(item.getItemMeta() instanceof Damageable damageable)) {
                continue;
            }
            if (!damageable.hasDamage()) {
                continue;
            }
            int currentDamage = damageable.getDamage();
            player.getInventory().getItem(i).damage(-(currentDamage), player);
            itemList.add(item);
        }
        sendUserMessage(sender, player, itemList);
    }

    private void repairAll(Player player, CommandSender sender) {
        List<ItemStack> itemList = new ArrayList<>();
        for (int i = 0; i < 41; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getItemMeta() == null) continue;
            if (!(item.getItemMeta() instanceof Damageable damageable)) {
                continue;
            }
            if (!damageable.hasDamage()) {
                continue;
            }
            int currentDamage = damageable.getDamage();
            player.getInventory().getItem(i).damage(-(currentDamage), player);
            itemList.add(item);
        }
        sendUserMessage(sender, player, itemList);
    }

    private void repairArmor(Player player, CommandSender sender) {
        List<ItemStack> itemList = new ArrayList<>();
        for (int i = 36; i < 40; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getItemMeta() == null) continue;
            if (!(item.getItemMeta() instanceof Damageable damageable)) {
                continue;
            }
            if (!damageable.hasDamage()) {
                continue;
            }
            int currentDamage = damageable.getDamage();
            player.getInventory().getItem(i).damage(-(currentDamage), player);
            itemList.add(item);
        }
        sendUserMessage(sender, player, itemList);
    }


    private void sendUserMessage(CommandSender sender, Player player, List<ItemStack> itemList) {
        if (itemList.isEmpty()) {
            sender.sendMessage(miniMessage.deserialize(LocaleHandler.getInstance().getNoItemsRepaired()));
            return;
        }
        Component repairedItems = repairedItemsList(itemList);
        if (runningOnOther) {
            sender.sendMessage(miniMessage.deserialize(repairOther,
                    Placeholder.component("items", repairedItems),
                    Placeholder.component("target", player.displayName())));
            player.sendMessage(miniMessage.deserialize(repairSelf,
                    Placeholder.component("items", repairedItems)));
            return;
        }
        sender.sendMessage(miniMessage.deserialize(repairSelf,
                Placeholder.component("items", repairedItems)));
    }

    private Component repairedItemsList(List<ItemStack> itemList) {
        Component repairedItems = Component.empty();
        if (itemList.isEmpty()) return repairedItems;
        repairedItems = repairedItems.append(miniMessage.deserialize(LocaleHandler.getInstance().getRepairHeader()));
        for (ItemStack itemStack : itemList) {
            String itemStringName = itemStack.getType().getItemTranslationKey();
            Component itemName = miniMessage.deserialize(LocaleHandler.getInstance().getItem(), Placeholder.parsed("item", itemStringName));
            repairedItems = repairedItems.append(itemName);
        }
        return repairedItems;
    }
}
