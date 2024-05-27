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
        prefix = localeConfig.getString("insertion.prefix");
        enabled = localeConfig.getString("insertion.enabled");
        disabled = localeConfig.getString("insertion.disabled");
        flyspeed = localeConfig.getString("insertion.flyspeed");
        walkspeed = localeConfig.getString("insertion.walkspeed");
        item = localeConfig.getString("insertion.item");
        flySetOwn = localeConfig.getString("fly.own");
        flySetOther = localeConfig.getString("fly.other");
        flySetByOther = localeConfig.getString("fly.by-other");
        speedGetOther = localeConfig.getString("speed.get.other");
        speedGetOwn = localeConfig.getString("speed.get.own");
        speedSetOwn = localeConfig.getString("speed.set.own");
        speedResetOwn = localeConfig.getString("speed.reset.own");
        speedSetOther = localeConfig.getString("speed.set.other");
        speedResetOther = localeConfig.getString("speed.reset.other");
        speedSetByOther = localeConfig.getString("speed.set.by-other");
        speedResetByOther = localeConfig.getString("speed.reset.by-other");
        feedSelf = localeConfig.getString("feed.self");
        feedOther = localeConfig.getString("feed.other");
        healSelf = localeConfig.getString("heal.self");
        healOther = localeConfig.getString("heal.other");
        repairSelf = localeConfig.getString("repair.self");
        repairOther = localeConfig.getString("repair.other");
        repairHeader = localeConfig.getString("repair.header");
        noItemsRepaired = localeConfig.getString("repair.no-items-repaired");
        invalidPlayer = localeConfig.getString("error.invalid-player");
        noPermission = localeConfig.getString("error.no-permission");
        mustBePlayer = localeConfig.getString("error.must-be-player");
        notEnoughArguments = localeConfig.getString("error.not-enough-arguments");
        invalidNumber = localeConfig.getString("error.invalid-number");
        notInRange = localeConfig.getString("error.not-in-range");
        invalidCommand = localeConfig.getString("error.invalid-command");
        cannotBeRepaired = localeConfig.getString("error.cannot-be-repaired");
        configReloaded = localeConfig.getString("plugin-messages.config-reloaded");
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
}
