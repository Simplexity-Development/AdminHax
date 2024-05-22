package simplexity.adminhax;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.adminhax.commands.Feed;
import simplexity.adminhax.commands.Fly;
import simplexity.adminhax.commands.AbstractSpeedCommand;
import simplexity.adminhax.commands.FlySpeed;
import simplexity.adminhax.commands.WalkSpeed;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;
import simplexity.adminhax.listeners.FlyListeners;

public final class AdminHax extends JavaPlugin {
    /*
    /feed <player>
    /heal <player>
    /repair
    /godmode <player>
    /walkspeed
    /smite
    */
    private static AdminHax instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();



    @Override
    public void onEnable() {
        instance = this;
        LocaleHandler.getInstance().loadLocale();
        this.saveDefaultConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        this.getCommand("flyspeed").setExecutor(new FlySpeed(new Permission("adminhax.commands.speed.fly"), new Permission("adminhax.commands.other.speed.fly"), true));
        this.getCommand("walkspeed").setExecutor(new WalkSpeed(new Permission("adminhax.commands.speed.walk"), new Permission("adminhax.commands.other.speed.walk"), false));
        this.getCommand("fly").setExecutor(new Fly());
        this.getCommand("feed").setExecutor(new Feed());
        this.getServer().getPluginManager().registerEvents(new FlyListeners(), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AdminHax getInstance() {
        return instance;
    }
    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }
}
