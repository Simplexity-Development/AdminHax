package simplexity.adminhax.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import simplexity.adminhax.AdminHax;

import java.io.File;
import java.io.IOException;

public class LocaleHandler {
    private static LocaleHandler instance;
    private final String fileName = "locale.yml";
    private final File localeFile = new File(AdminHax.getInstance().getDataFolder(), fileName);
    private final FileConfiguration localeConfig = new YamlConfiguration();
    //Insertion messages
    private String prefix, enabled, disabled, flyspeed, walkspeed, item;
    //Speed messages
    private String speedGetOther, speedGetOwn, speedSetOwn, speedResetOwn, speedSetOther, speedResetOther,
            speedSetByOther, speedResetByOther;
    //Fly messages
    private String flySetOwn, flySetOther, flySetByOther;
    //Feed messages
    private String feedSelf, feedOther;
    //Heal messages
    private String healSelf, healOther;
    //Repair messages
    private String repairSelf, repairOther, repairHeader, noItemsRepaired;
    //GodMode messages
    private String godmodeSelf, godmodeOther, godmodeGetSelf, godmodeGetOther;
    //Broadcast Message
    private String broadcastServerPrefix;
    //Errors
    private String invalidPlayer, noPermission, mustBePlayer, notEnoughArguments, invalidNumber, notInRange,
            invalidCommand, configReloaded, cannotBeRepaired;

    private LocaleHandler() {
        if (!localeFile.exists()) {
            AdminHax.getInstance().saveResource(fileName, false);
        }
    }

    public static LocaleHandler getInstance() {
        if (instance == null)
            instance = new LocaleHandler();
        return instance;
    }

    public FileConfiguration getLocaleConfig() {
        return localeConfig;

    }

    public void loadLocale() {
        try {
            localeConfig.load(localeFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        prefix = localeConfig.getString("insertion.prefix", "<green>[<yellow>SF</yellow>]</green> ");
        enabled = localeConfig.getString("insertion.enabled", "<green>enabled</green>");
        disabled = localeConfig.getString("insertion.disabled", "<red>disabled</red>");
        flyspeed = localeConfig.getString("insertion.flyspeed", "<yellow>flyspeed</yellow>");
        walkspeed = localeConfig.getString("insertion.walkspeed", "<yellow>walkspeed</yellow>");
        item = localeConfig.getString("insertion.item", "\n<gray>• <lang:<item>></gray>");
        flySetOwn = localeConfig.getString("fly.own", "Your fly has been <value>");
        flySetOther = localeConfig.getString("fly.other", "<target>'s fly has been <value>");
        flySetByOther = localeConfig.getString("fly.by-other",  "<green>Your fly has been <value></green>");
        speedGetOther = localeConfig.getString("speed.get.other", "<grey><target>'s current <speedtype> is <value></grey>");
        speedGetOwn = localeConfig.getString("speed.get.own","<grey>Your <speedtype> is currently set to <value></grey>");
        speedSetOwn = localeConfig.getString("speed.set.own", "<green>Your <speedtype> has been set to <value></green>");
        speedResetOwn = localeConfig.getString("speed.reset.own", "<green>Your <speedtype> has been reset</green>");
        speedSetOther = localeConfig.getString("speed.set.other", "<green>You set <target>'s <speedtype> to <value></green>");
        speedResetOther = localeConfig.getString("speed.reset.other", "<green><target>'s <speedtype> has been reset</green>");
        speedSetByOther = localeConfig.getString("speed.set.by-other", "<green>Your <speedtype> has been set to <value> by <initiator></green>");
        speedResetByOther = localeConfig.getString("speed.reset.by-other", "<green>Your <speedtype> has been reset by <initiator></green>");
        feedSelf = localeConfig.getString("feed.self", "<green>You have been fed</green>");
        feedOther = localeConfig.getString("feed.other", "<green>You have fed <target></green>");
        healSelf = localeConfig.getString("heal.self", "<green>You have been healed</green>");
        healOther = localeConfig.getString("heal.other", "<green>You have healed <target></green>");
        repairSelf = localeConfig.getString("repair.self", "<green><hover:show_text:'<items>'>Your items have been repaired!</hover></green>");
        repairOther = localeConfig.getString("repair.other", "<green><hover:show_text:'<items>'>You have repaired <target>'s items!</hover><green>");
        repairHeader = localeConfig.getString("repair.header", "<aqua><u>Repaired Items</u></aqua>");
        godmodeSelf = localeConfig.getString("godmode.self", "<yellow>God-Mode has been <value></yellow>");
        godmodeOther = localeConfig.getString("godmode.other", "<yellow>God-Mode has been <value> for <target></yellow>");
        godmodeGetSelf = localeConfig.getString("godmode.get.self", "<yellow>God-Mode is currently <value></yellow>");
        godmodeGetOther = localeConfig.getString("godmode.get.other", "<yellow><target>'s God-Mode is currently <value></yellow>");
        noItemsRepaired = localeConfig.getString("repair.no-items-repaired",  "<gray>No items were able to be repaired</gray>");
        invalidPlayer = localeConfig.getString("error.invalid-player", "<red>That is not a valid player. Please check your spelling and try again</red>");
        noPermission = localeConfig.getString("error.no-permission",  "<red>You do not have permission to run this command</red>");
        mustBePlayer = localeConfig.getString("error.must-be-player", "<red>You must be a player to run this command</red>");
        notEnoughArguments = localeConfig.getString("error.not-enough-arguments", "<red>You did not provide enough arguments. Please check your syntax and try again</red>");
        invalidNumber = localeConfig.getString("error.invalid-number",  "<red>Sorry, you did not enter a valid flyspeed, please try again</red>");
        notInRange = localeConfig.getString("error.not-in-range", "<red>Sorry, you must provide a number between <min> and <max></red>");
        invalidCommand = localeConfig.getString("error.invalid-command", "<red>Sorry, that subcommand is invalid. Please check your syntax and try again</red>");
        cannotBeRepaired = localeConfig.getString("error.cannot-be-repaired", "<red>That item cannot be repaired</red>");
        configReloaded = localeConfig.getString("plugin-messages.config-reloaded", "<gold>Config Reloaded</gold>");
        broadcastServerPrefix = localeConfig.getString("broadcast.server-prefix", "<green>[Server]</green> » ");
    }

    public String getPrefix() {
        return prefix;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getDisabled() {
        return disabled;
    }

    public String getSpeedResetOther() {
        return speedResetOther;
    }

    public String getSpeedGetOther() {
        return speedGetOther;
    }

    public String getSpeedGetOwn() {
        return speedGetOwn;
    }

    public String getSpeedSetOwn() {
        return speedSetOwn;
    }

    public String getSpeedResetOwn() {
        return speedResetOwn;
    }

    public String getSpeedSetOther() {
        return speedSetOther;
    }

    public String getSpeedSetByOther() {
        return speedSetByOther;
    }

    public String getSpeedResetByOther() {
        return speedResetByOther;
    }

    public String getInvalidPlayer() {
        return invalidPlayer;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getMustBePlayer() {
        return mustBePlayer;
    }

    public String getNotEnoughArguments() {
        return notEnoughArguments;
    }

    public String getInvalidNumber() {
        return invalidNumber;
    }

    public String getNotInRange() {
        return notInRange;
    }

    public String getInvalidCommand() {
        return invalidCommand;
    }

    public String getConfigReloaded() {
        return configReloaded;
    }

    public String getFlyspeed() {
        return flyspeed;
    }

    public String getWalkspeed() {
        return walkspeed;
    }

    public String getFlySetOwn() {
        return flySetOwn;
    }

    public String getFlySetOther() {
        return flySetOther;
    }

    public String getFlySetByOther() {
        return flySetByOther;
    }

    public String getFeedSelf() {
        return feedSelf;
    }

    public String getFeedOther() {
        return feedOther;
    }

    public String getHealSelf() {
        return healSelf;
    }

    public String getHealOther() {
        return healOther;
    }

    public String getCannotBeRepaired() {
        return cannotBeRepaired;
    }

    public String getItem() {
        return item;
    }

    public String getRepairSelf() {
        return repairSelf;
    }

    public String getRepairOther() {
        return repairOther;
    }

    public String getRepairHeader() {
        return repairHeader;
    }

    public String getNoItemsRepaired() {
        return noItemsRepaired;
    }

    public String getGodmodeSelf() {
        return godmodeSelf;
    }

    public String getGodmodeOther() {
        return godmodeOther;
    }

    public String getGodmodeGetSelf() {
        return godmodeGetSelf;
    }

    public String getGodmodeGetOther() {
        return godmodeGetOther;
    }

    public String getBroadcastServerPrefix() {
        return broadcastServerPrefix;
    }
}
