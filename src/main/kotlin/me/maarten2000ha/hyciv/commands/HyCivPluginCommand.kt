package me.maarten2000ha.hyciv.commands

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase
import com.hypixel.hytale.server.core.util.EventTitleUtil
import me.maarten2000ha.hyciv.utils.ServerUtil.executeWorld
import javax.annotation.Nonnull

/**
 * Main command for HyCiv plugin.
 *
 * Usage:
 * - /hyc help - Show available commands
 * - /hyc info - Show plugin information
 * - /hyc reload - Reload plugin configuration
 */
class HyCivPluginCommand : AbstractCommandCollection("hyc", "HyCiv plugin commands") {

    init {
        addSubCommand(HelpSubCommand())
        addSubCommand(InfoSubCommand())
        addSubCommand(ReloadSubCommand())
        addSubCommand(ExampleCommand())
    }

    override fun canGeneratePermission(): Boolean = false
}

/**
 * /hyc help - Show available commands
 */
class HelpSubCommand : CommandBase("help", "Show available commands") {

    init {
        setPermissionGroup(null)
    }

    override fun canGeneratePermission(): Boolean = false

    override fun executeSync(context: CommandContext) {
        context.sendMessage(Message.raw(""))
        context.sendMessage(Message.raw("=== HyCiv Commands ==="))
        context.sendMessage(Message.raw("/hyc help - Show this help message"))
        context.sendMessage(Message.raw("/hyc info - Show plugin information"))
        context.sendMessage(Message.raw("/hyc reload - Reload configuration"))
        context.sendMessage(Message.raw("========================"))
    }
}

/**
 * /hyc info - Show plugin information
 */
class InfoSubCommand : CommandBase("info", "Show plugin information") {

    init {
        setPermissionGroup(null)
    }

    override fun canGeneratePermission(): Boolean = false

    override fun executeSync(context: CommandContext) {
        val plugin = me.maarten2000ha.hyciv.HyCivPlugin.instance

        context.sendMessage(Message.raw(""))
        context.sendMessage(Message.raw("=== HyCiv Info ==="))
        context.sendMessage(Message.raw("Name: HyCiv"))
        context.sendMessage(Message.raw("Version: 1.0.0"))
        context.sendMessage(Message.raw("Author: maarten2000ha"))
        context.sendMessage(Message.raw("Status: " + if (plugin != null) "Running" else "Not loaded"))
        context.sendMessage(Message.raw("===================="))
    }
}

/**
 * /hyc reload - Reload plugin configuration
 */
class ReloadSubCommand : CommandBase("reload", "Reload plugin configuration") {

    init {
        setPermissionGroup(null)
    }

    override fun canGeneratePermission(): Boolean = false

    override fun executeSync(context: CommandContext) {
        val plugin = me.maarten2000ha.hyciv.HyCivPlugin.instance

        if (plugin == null) {
            context.sendMessage(Message.raw("Error: Plugin not loaded"))
            return
        }

        context.sendMessage(Message.raw("Reloading HyCiv..."))

        // TODO: Add your reload logic here

        context.sendMessage(Message.raw("HyCiv reloaded successfully!"))
    }
}

class ExampleCommand: CommandBase("example", "Show plugin example") {
    @Nonnull
    private val titleArg: RequiredArg<String?> = this.withRequiredArg<String?>(
        "title", "com.example.plugin.commands.title.arg.title",
        ArgTypes.STRING
    )

    @Nonnull
    private val subtitleArg: RequiredArg<String?> = this.withRequiredArg<String?>(
        "subtitle", "com.example.plugin.commands.title.arg.subtitle",
        ArgTypes.STRING
    )


    init {
        setPermissionGroup(null)
    }

    override fun canGeneratePermission(): Boolean = false

    override fun executeSync(context: CommandContext) {
        val title = context.get<String?>(this.titleArg) as String
        val subtitle = context.get<String?>(this.subtitleArg) as String

        executeWorld(Runnable {
            EventTitleUtil.showEventTitleToUniverse(
                Message.raw(title),
                Message.raw(subtitle),
                false,
                null,
                4f,
                1.5f,
                1.5f
            )
        })

    }
}