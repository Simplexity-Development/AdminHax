package simplexity.adminhax.util;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.permissions.Permission;

public enum RenamePermission {

    COLOR(new Permission("adminhax.commands.rename.color"), StandardTags.color()),
    GRADIENT(new Permission("adminhax.commands.rename.color.gradient"), StandardTags.gradient()),
    RAINBOW(new Permission("adminhax.commands.rename.color.rainbow"), StandardTags.rainbow()),
    STRIKETHROUGH(new Permission("adminhax.commands.rename.format.strikethrough"), StandardTags.decorations(TextDecoration.STRIKETHROUGH)),
    ITALIC(new Permission("adminhax.commands.rename.format.italic"), StandardTags.decorations(TextDecoration.ITALIC)),
    BOLD(new Permission("adminhax.commands.rename.format.bold"), StandardTags.decorations(TextDecoration.BOLD)),
    //I swear the word OBFUSCATED is literally just a keysmash - I can never spell it right
    OBFUSCATED(new Permission("adminhax.commands.rename.format.obfuscated"), StandardTags.decorations(TextDecoration.OBFUSCATED));

    private final Permission permission;
    private final TagResolver resolver;

    RenamePermission(Permission permission, TagResolver resolver) {
        this.permission = permission;
        this.resolver = resolver;
    }

    public Permission getPermission() {
        return permission;
    }

    public TagResolver getTagResolver() {
        return resolver;
    }
}
