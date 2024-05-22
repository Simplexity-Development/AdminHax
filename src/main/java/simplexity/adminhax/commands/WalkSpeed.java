package simplexity.adminhax.commands;

import org.bukkit.permissions.Permission;

public class WalkSpeed extends AbstractSpeedCommand {
    public WalkSpeed(Permission basicPermission, Permission adminPermission, boolean isFlySpeed) {
        super(basicPermission, adminPermission, isFlySpeed);
    }
}
