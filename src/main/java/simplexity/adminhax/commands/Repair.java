package simplexity.adminhax.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simplexity.adminhax.AdminHax;
import simplexity.adminhax.Util;
import simplexity.adminhax.config.LocaleHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repair implements TabExecutor {
    public static final String HOT_BAR = "hotbar";
    public static final String INVENTORY = "inventory";
    public static final String ALL = "all";
    public static final String ARMOR = "armor";
    public static final String HAND = "hand";
    private static final MiniMessage miniMessage = AdminHax.getMiniMessage();
    public static final Set<String> VALID_ARGS = new HashSet<>(Arrays.asList(HOT_BAR, INVENTORY, ALL, ARMOR, HAND));
    public static final HashMap<String, Permission> basicPermissions = new HashMap<>();
    public static final HashMap<String, Permission> adminPermissions = new HashMap<>();
    public static final List<String> tabComplete = new ArrayList<>();
    public String cannotBeRepaired;
    public String repairSelf;
    public String repairOther;
    public boolean runningOnOther;

    public static void setupMaps() {
        basicPermissions.put(HOT_BAR, Util.REPAIR_HOT_BAR_PERMISSION);
        basicPermissions.put(INVENTORY, Util.REPAIR_INVENTORY_PERMISSION);
        basicPermissions.put(ALL, Util.REPAIR_ALL_PERMISSION);
        basicPermissions.put(ARMOR, Util.REPAIR_ARMOR_PERMISSION);
        basicPermissions.put(HAND, Util.REPAIR_HAND_PERMISSION);
        adminPermissions.put(HOT_BAR, Util.REPAIR_HOT_BAR_OTHER_PERMISSION);
        adminPermissions.put(INVENTORY, Util.REPAIR_INVENTORY_OTHER_PERMISSION);
        adminPermissions.put(ALL, Util.REPAIR_ALL_OTHER_PERMISSION);
        adminPermissions.put(ARMOR, Util.REPAIR_ARMOR_OTHER_PERMISSION);
        adminPermissions.put(HAND, Util.REPAIR_HAND_OTHER_PERMISSION);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String invalidCommand = LocaleHandler.getInstance().getInvalidCommand();
        String mustBePlayer = LocaleHandler.getInstance().getMustBePlayer();
        String invalidPlayer = LocaleHandler.getInstance().getInvalidPlayer();
        cannotBeRepaired = LocaleHandler.getInstance().getCannotBeRepaired();
        repairSelf = LocaleHandler.getInstance().getRepairSelf();
        repairOther = LocaleHandler.getInstance().getRepairOther();
        switch (args.length) {
            case 0: {
                if (!isSenderPlayer(sender)) {
                    Util.sendUserMessage(sender, mustBePlayer);
                    return false;
                }
                if (!sender.hasPermission(basicPermissions.get(HAND))) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                Player player = (Player) sender;
                runningOnOther = false;
                runLogic(sender, player, HAND);
                return true;
            }
            case 1: {
                if (!isValidArg(args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                if (!(isSenderPlayer(sender))) {
                    Util.sendUserMessage(sender, mustBePlayer);
                    return false;
                }
                if (!userHasBasicPermission(sender, args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                Player player = (Player) sender;
                runningOnOther = false;
                runLogic(sender, player, args[0]);
                return true;
            }
            case 2: {
                if (!isValidArg(args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                Player player = getPlayerFromArgs(sender, args);
                if (player == null) {
                    if (!userHasAdminPermission(sender, args[0])) {
                        Util.sendUserMessage(sender, invalidCommand);
                        return false;
                    }
                    Util.sendUserMessage(sender, invalidPlayer);
                    return false;
                }
                if (!userHasAdminPermission(sender, args[0])) {
                    Util.sendUserMessage(sender, invalidCommand);
                    return false;
                }
                runningOnOther = true;
                runLogic(sender, player, args[0]);
                return true;
            }
            default: {
                Util.sendUserMessage(sender, invalidCommand);
                return false;
            }
        }
    }

    private boolean isValidArg(String arg) {
        return VALID_ARGS.contains(arg.toLowerCase());
    }

    private boolean isSenderPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    private boolean userHasBasicPermission(CommandSender sender, String arg) {
        if (sender instanceof Player player) {
            return player.hasPermission(basicPermissions.get(arg.toLowerCase()));
        }
        return false;
    }

    private boolean userHasAdminPermission(CommandSender sender, String arg) {
        return sender.hasPermission(adminPermissions.get(arg.toLowerCase()));
    }

    private Player getPlayerFromArgs(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return null;
        }
        String type = args[0];
        if (!userHasAdminPermission(sender, type)) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidCommand());
            return null;
        }
        Player player = AdminHax.getInstance().getServer().getPlayer(args[1]);
        if (player == null) {
            Util.sendUserMessage(sender, LocaleHandler.getInstance().getInvalidPlayer());
            return null;
        }
        return player;
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        tabComplete.clear();
        if (args.length < 2) {
            for (String arg : VALID_ARGS) {
                if (sender.hasPermission(basicPermissions.get(arg)) || sender.hasPermission(adminPermissions.get(arg))) {
                    tabComplete.add(arg);
                }
            }
        }
        if (args.length < 2 && tabComplete.isEmpty()) {
            return List.of();
        }
        if (args.length >= 2) {
            return null;
        }
        return tabComplete;
    }


}
