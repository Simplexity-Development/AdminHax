package simplexity.adminhax.config;

public enum Message {
    BROADCAST_SERVER_PREFIX("broadcast.server-prefix", "<green>[Server]</green> » "),
    ERROR_CANNOT_BE_REPAIRED("error.cannot-be-repaired", "<red>That item cannot be repaired</red>"),
    ERROR_INVALID_COMMAND("error.invalid-command", "<red>Sorry, that subcommand is invalid. Please check your syntax and try again</red>"),
    ERROR_INVALID_NUMBER("error.invalid-number", "<red>Sorry, you did not enter a valid flyspeed, please try again</red>"),
    ERROR_INVALID_PLAYER("error.invalid-player", "<red>That is not a valid player. Please check your spelling and try again</red>"),
    ERROR_MUST_BE_PLAYER("error.must-be-player", "<red>You must be a player to run this command</red>"),
    ERROR_MUST_HOLD_ITEM_TO_RENAME("error.must-hold-item", "<red>You must be holding item to rename</red>"),
    ERROR_NO_ITEMS_HAT("error.no-hat-items", "<red>You must be holding an item or have an item in your helmet slot to use this command</red>"),
    ERROR_CURSE_OF_BINDING("error.curse-of-binding", "<red>You currently are wearing something with curse of binding! Sorry!</red>"),
    ERROR_HAT_NOT_ALLOWED("error.hat-not-allowed", "<red>Hats of this type are not allowed</red>"),
    ERROR_NAME_TOO_LONG("error.name-too-long", "<red>Sorry, that item name is too long! The max characters for an item name is <value></red>"),
    ERROR_NO_PERMISSION("error.no-permission", "<red>You do not have permission to run this command</red>"),
    ERROR_NOT_ENOUGH_ARGUMENTS("error.not-enough-arguments", "<red>You did not provide enough arguments. Please check your syntax and try again</red>"),
    ERROR_NOT_IN_RANGE("error.not-in-range", "<red>Sorry, you must provide a number between <min> and <max></red>"),
    HAT_SUCCESSFUL("hat.success", "<green>Enjoy your new hat!</green>"),
    FEED_OTHER("feed.other", "<green>You have fed <target></green>"),
    FEED_SELF("feed.self", "<green>You have been fed</green>"),
    FLY_SET_BY_OTHER("fly.by-other", "<green>Your fly has been <value></green>"),
    FLY_SET_OTHER("fly.other", "<target>'s fly has been <value>"),
    FLY_SET_OWN("fly.own", "Your fly has been <value>"),
    GODMODE_GET_OTHER("godmode.get.other", "<yellow><target>'s God-Mode is currently <value></yellow>"),
    GODMODE_GET_SELF("godmode.get.self", "<yellow>God-Mode is currently <value></yellow>"),
    GODMODE_OTHER("godmode.other", "<yellow>God-Mode has been <value> for <target></yellow>"),
    GODMODE_SELF("godmode.self", "<yellow>God-Mode has been <value></yellow>"),
    HEAL_OTHER("heal.other", "<green>You have healed <target></green>"),
    HEAL_SELF("heal.self", "<green>You have been healed</green>"),
    INSERT_DISABLED("insertion.disabled", "<red>disabled</red>"),
    INSERT_ENABLED("insertion.enabled", "<green>enabled</green>"),
    INSERT_FLY_SPEED("insertion.flyspeed", "<yellow>flyspeed</yellow>"),
    INSERT_ITEM("insertion.item", "\n<gray>• <lang:<item>></gray>"),
    INSERT_PREFIX("insertion.prefix", "<green>[<yellow>SF</yellow>]</green> "),
    INSERT_WALK_SPEED("insertion.walkspeed", "<yellow>walkspeed</yellow>"),
    PLUGIN_MESSAGES_CONFIG_RELOADED("plugin-messages.config-reloaded", "<gold>Config Reloaded</gold>"),
    RENAME_SUCCESSFUL("rename.successful", "<green>Successfully renamed <item> to <name></green>"),

    REPAIR_HEADER("repair.header", "<aqua><u>Repaired Items</u></aqua>"),
    REPAIR_NO_ITEMS_REPAIRED("repair.no-items-repaired", "<gray>No items were able to be repaired</gray>"),
    REPAIR_OTHER("repair.other", "<green><hover:show_text:'<items>'>You have repaired <target>'s items!</hover><green>"),
    REPAIR_SELF("repair.self", "<green><hover:show_text:'<items>'>Your items have been repaired!</hover></green>"),
    SPEED_GET_OTHER("speed.get.other", "<grey><target>'s current <speedtype> is <value></grey>"),
    SPEED_GET_OWN("speed.get.own", "<grey>Your <speedtype> is currently set to <value></grey>"),
    SPEED_RESET_BY_OTHER("speed.reset.by-other", "<green>Your <speedtype> has been reset by <initiator></green>"),
    SPEED_RESET_OTHER("speed.reset.other", "<green><target>'s <speedtype> has been reset</green>"),
    SPEED_RESET_OWN("speed.reset.own", "<green>Your <speedtype> has been reset</green>"),
    SPEED_SET_BY_OTHER("speed.set.by-other", "<green>Your <speedtype> has been set to <value> by <initiator></green>"),
    SPEED_SET_OTHER("speed.set.other", "<green>You set <target>'s <speedtype> to <value></green>"),
    SPEED_SET_OWN("speed.set.own", "<green>Your <speedtype> has been set to <value></green>");
    private final String path;
    private String message;

    Message(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
