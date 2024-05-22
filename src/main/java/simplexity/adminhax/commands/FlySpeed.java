package simplexity.adminhax.commands;

import org.bukkit.permissions.Permission;

public class FlySpeed extends AbstractSpeedCommand{
    public FlySpeed(Permission basicPermission, Permission adminPermission, boolean isFlySpeed) {
        super(basicPermission, adminPermission, isFlySpeed);
    }
}
