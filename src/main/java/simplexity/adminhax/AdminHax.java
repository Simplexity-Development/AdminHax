package simplexity.adminhax;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.adminhax.commands.basic.BroadcastMsg;
import simplexity.adminhax.commands.basic.Hat;
import simplexity.adminhax.commands.basic.ReloadPlugin;
import simplexity.adminhax.commands.basic.RenameItem;
import simplexity.adminhax.commands.hax.Feed;
import simplexity.adminhax.commands.hax.Fly;
import simplexity.adminhax.commands.hax.Heal;
import simplexity.adminhax.commands.speed.FlySpeed;
import simplexity.adminhax.commands.speed.WalkSpeed;
import simplexity.adminhax.commands.withargs.GodMode;
import simplexity.adminhax.commands.withargs.Repair;
import simplexity.adminhax.config.ConfigHandler;
import simplexity.adminhax.config.LocaleHandler;
import simplexity.adminhax.listeners.FlyListeners;
import simplexity.adminhax.util.Permissions;

public final class AdminHax extends JavaPlugin {

    private static AdminHax instance;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();


    @Override
    public void onEnable() {
        instance = this;
        LocaleHandler.getInstance().reloadLocale();
        this.saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        ConfigHandler.getInstance().reloadConfigValues();
        this.getCommand("flyspeed").setExecutor(new FlySpeed(Permissions.FLY_SPEED_PERMISSION, Permissions.FLY_SPEED_OTHER_PERMISSION, 0.1f));
        this.getCommand("walkspeed").setExecutor(new WalkSpeed(Permissions.WALK_SPEED_PERMISSION, Permissions.WALK_SPEED_OTHER_PERMISSION, 0.2f));
        this.getCommand("fly").setExecutor(new Fly(Permissions.FLY_PERMISSION, Permissions.FLY_OTHER_PERMISSION));
        this.getCommand("feed").setExecutor(new Feed(Permissions.FEED_PERMISSION, Permissions.FEED_OTHER_PERMISSION));
        this.getCommand("heal").setExecutor(new Heal(Permissions.HEAL_PERMISSION, Permissions.HEAL_OTHER_PERMISSION));
        this.getCommand("repair").setExecutor(new Repair(Permissions.REPAIR_HAND_PERMISSION, "hand"));
        this.getCommand("godmode").setExecutor(new GodMode(Permissions.GODMODE_PERMISSION, "toggle"));
        this.getCommand("broadcastmsg").setExecutor(new BroadcastMsg());
        this.getCommand("adminhaxreload").setExecutor(new ReloadPlugin());
        this.getCommand("rename").setExecutor(new RenameItem());
        this.getCommand("hat").setExecutor(new Hat());
        this.getServer().getPluginManager().registerEvents(new FlyListeners(), this);
    }

    public static AdminHax getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }
}
