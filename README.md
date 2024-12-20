# AdminHax

## Commands

| Command        | Permission                   | Description                            | Usage                                                       | Aliases      |
|----------------|------------------------------|----------------------------------------|-------------------------------------------------------------|--------------|
| flyspeed       | adminhax.commands.speed.fly  | change your fly speed                  | /flyspeed [set \| get \|reset] [value \|player] <value>     | fs           |
| walkspeed      | adminhax.commands.speed.walk | change your walk speed                 | /walkspeed [set \| get \|reset] [value \|player] <value>    | ws           |
| fly            | adminhax.commands.fly        | fly                                    | /fly <player>                                               |              |
| feed           | adminhax.commands.feed       | fill your hunger bar                   | /feed <player>                                              |              |
| heal           | adminhax.commands.heal       | heal                                   | /heal <player>                                              |              |
| repair         | adminhax.commands.repair     | repair items                           | /repair [hand \| hotbar \|inventory \|armor \|all] <player> |              |
| rename         | adminhax.commands.rename     | rename items                           | /rename <name>                                              |              |
| godmode        | adminhax.commands.godmode    | toggle godmode/invincibility           | /godmode [get \| toggle] <player>                           |              |
| broadcast      | adminhax.commands.broadcast  | Send a message to the whole server     | /broadcast <message>                                        | broadcastmsg |
| adminhaxreload | adminhax.reload              | reloads the AdminHax config and locale | /adminhaxreload                                             |              |

## Permissions

| Speed Permission                     | Default | Description                  |
|:-------------------------------------|:-------:|:-----------------------------|
| `adminhax.commands.speed.walk`       |   op    | change your walk speed       |
| `adminhax.commands.speed.fly`        |   op    | change your fly speed        |
| ADMIN PERMISSIONS                    |  ----   | ---------------------------- | 
| `adminhax.commands.other.speed.walk` |   op    | change others' walk speed    |
| `adminhax.commands.other.speed.fly`  |   op    | change others' fly speed     |

| Ability Permissions                   | Default | Description                        |
|:--------------------------------------|:-------:|:-----------------------------------|
| `adminhax.commands.fly`               |   op    | fly                                |
| `adminhax.commands.feed`              |   op    | fill your hunger bar               |
| `adminhax.commands.heal`              |   op    | heal to max health                 |
| `adminhax.commands.godmode`           |   op    | be invincible                      |
| `adminhax.commands.godmode.get`       |   op    | see what your godmode status is    |
| ADMIN PERMISSIONS                     |  ----   | ----------------------------       |
| `adminhax.commands.other.fly`         |   op    | make others fly                    |
| `adminhax.commands.other.feed`        |   op    | fill others' hunger bar            |
| `adminhax.commands.other.heal`        |   op    | heal others to max health          |
| `adminhax.commands.other.godmode`     |   op    | make others invincible             |
| `adminhax.commands.other.godmode.get` |   op    | see what others' godmode status is |

| Repair Permissions                         | Default | Description                                            |
|:-------------------------------------------|:-------:|:-------------------------------------------------------|
| `adminhax.commands.repair`                 |   op    | repair items                                           |
| `adminhax.commands.repair.hand`            |   op    | repair the item in your hand                           |
| `adminhax.commands.repair.hotbar`          |   op    | repair the items in your hotbar                        |
| `adminhax.commands.repair.inventory`       |   op    | repair the items in your inventory                     |
| `adminhax.commands.repair.armor`           |   op    | repair your armor                                      |
| `adminhax.commands.repair.all`             |   op    | repair everything in your inventory and armor slots    |
| ADMIN PERMISSIONS                          |  ----   | ----------------------------                           |
| `adminhax.commands.other.repair`           |   op    | repair other people's items                            |
| `adminhax.commands.other.repair.hand`      |   op    | repair the item in others' hand                        |
| `adminhax.commands.other.repair.hotbar`    |   op    | repair the items in others' hotbar                     |
| `adminhax.commands.other.repair.inventory` |   op    | repair the items in others' inventory                  |
| `adminhax.commands.other.repair.armor`     |   op    | repair others' armor                                   |
| `adminhax.commands.other.repair.all`       |   op    | repair everything in others' inventory and armor slots |

| Rename Permissions                              | Default | Description                           |
|-------------------------------------------------|:-------:|---------------------------------------|
| `adminhax.commands.rename`                      |   op    | allows the use of `/rename`           |
| `adminhax.commands.rename.color`                |   op    | allows colors in `/rename`            |
| `adminhax.commands.rename.color.gradient`       |   op    | allows gradients in `/rename`         |
| `adminhax.commands.rename.color.rainbow`        |   op    | allows rainbow tag in `/rename`       |
| `adminhax.commands.rename.format`               |   op    | allows format tags in `/rename`       |
| `adminhax.commands.rename.format.strikethrough` |   op    | allows strikethrough tag in `/rename` |
| `adminhax.commands.rename.format.italic`        |   op    | allows italic tag in `/rename`        | 
| `adminhax.commands.rename.format.bold`          |   op    | allows bold tag in `/rename`          |
| `adminhax.commands.rename.format.obfuscated`    |   op    | allows obfuscated tag in `/rename     |

| Misc Admin Permissions          | Default | Description                                              |
|---------------------------------|:-------:|----------------------------------------------------------|
| `adminhax.commands.broadcast`   |   op    | broadcast a message server-wide                          |
| `adminhax.reload `              |   op    | reload the plugin configuration                          |
| `adminhax.bypass.rename.length` |   op    | bypass the configured max character length for `/rename` |

---
