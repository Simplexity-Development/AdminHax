package simplexity.adminhax.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import simplexity.adminhax.AdminHax;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class ConfigHandler {
    private static ConfigHandler instance;
    private final Logger logger = AdminHax.getInstance().getLogger();

    private ConfigHandler() {
    }

    public static ConfigHandler getInstance() {
        if (instance == null) instance = new ConfigHandler();
        return instance;
    }

    private float maxWalkSpeed, minWalkSpeed, maxFlySpeed, minFlySpeed;
    private boolean sessionPersistentFlight, worldChangePersistentFlight, respawnPersistentFlight,
            gamemodeChangePersistentFlight, hatRespectsCurseOfBinding;
    private int maxRenameCharacters;
    private static final HashSet<Material> disabledHatItems = new HashSet<>();

    public void reloadConfigValues() {
        AdminHax.getInstance().reloadConfig();
        FileConfiguration config = AdminHax.getInstance().getConfig();
        maxWalkSpeed = checkFloat(10, "speed.walk.max", config);
        minWalkSpeed = checkFloat(-10, "speed.walk.min", config);
        maxFlySpeed = checkFloat(10, "speed.fly.max", config);
        minFlySpeed = checkFloat(-10, "speed.fly.min", config);
        sessionPersistentFlight = config.getBoolean("flight.persistent.session", true);
        worldChangePersistentFlight = config.getBoolean("flight.persistent.world-change", true);
        respawnPersistentFlight = config.getBoolean("flight.persistent.respawn", true);
        gamemodeChangePersistentFlight = config.getBoolean("flight.persistent.gamemode-change", true);
        maxRenameCharacters = config.getInt("rename.max-characters", 50);
        hatRespectsCurseOfBinding = config.getBoolean("hat.respect-curse-of-binding", true);
        List<String> disabledItems = config.getStringList("hat.disabled-items");
        disabledHatItems.clear();
        if (!disabledItems.isEmpty()) {
            for (String string : disabledItems) {
                Material material = Material.getMaterial(string);
                if (material == null) {
                    logger.info(string + " is not a valid material, please check your syntax");
                    continue;
                }
                disabledHatItems.add(material);
            }
        }
    }

    private float checkFloat(float defaultValue, String configPath, FileConfiguration config) {
        String configString = config.getString(configPath);
        if (configString == null) {
            logger.warning("Value at " + configPath + " is null, please configure this value. Setting to default value.");
            return defaultValue;
        }
        float configFloat;
        try {
            configFloat = Float.parseFloat(configString);
        } catch (NumberFormatException e) {
            logger.warning("Value at " + configPath + " is not a number, please configure this value. Setting to default value.");
            return defaultValue;
        }
        if (configFloat > 10.0 || configFloat < -10.0) {
            logger.warning("Value for " + configPath + " is out of range (-10.0 to 10.0). Setting to default value");
            return defaultValue;
        }
        return configFloat;
    }

    public float getMaxWalkSpeed() {
        return maxWalkSpeed;
    }

    public float getMinWalkSpeed() {
        return minWalkSpeed;
    }

    public float getMaxFlySpeed() {
        return maxFlySpeed;
    }

    public float getMinFlySpeed() {
        return minFlySpeed;
    }

    public boolean isSessionPersistentFlight() {
        return sessionPersistentFlight;
    }

    public boolean isWorldChangePersistentFlight() {
        return worldChangePersistentFlight;
    }

    public boolean isRespawnPersistentFlight() {
        return respawnPersistentFlight;
    }

    public boolean isGamemodeChangePersistentFlight() {
        return gamemodeChangePersistentFlight;
    }

    public int getMaxRenameCharacters() {
        return maxRenameCharacters;
    }

    public boolean isHatRespectsCurseOfBinding() {
        return hatRespectsCurseOfBinding;
    }

    public HashSet<Material> getDisabledHatItems(){
        return disabledHatItems;
    }
}
