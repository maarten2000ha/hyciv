package me.maarten2000ha.hyciv

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import com.hypixel.hytale.event.EventRegistry
import com.hypixel.hytale.logger.HytaleLogger
import me.maarten2000ha.hyciv.commands.HyCivPluginCommand
import me.maarten2000ha.hyciv.listeners.PlayerListener
import java.util.logging.Level

/**
 * HyCiv - A Hytale server plugin.
 */
class HyCivPlugin(init: JavaPluginInit) : JavaPlugin(init) {

    companion object {
        private val LOGGER = HytaleLogger.forEnclosingClass()

        @JvmStatic
        var instance: HyCivPlugin? = null
            private set
    }

    init {
        instance = this
    }

    override fun setup() {
        LOGGER.at(Level.INFO).log("[HyCiv] Setting up...")

        // Register commands
        registerCommands()

        // Register event listeners
        registerListeners()

        LOGGER.at(Level.INFO).log("[HyCiv] Setup complete!")
    }

    private fun registerCommands() {
        try {
            commandRegistry.registerCommand(HyCivPluginCommand())
            LOGGER.at(Level.INFO).log("[HyCiv] Registered /hyc command")
        } catch (e: Exception) {
            LOGGER.at(Level.WARNING).withCause(e).log("[HyCiv] Failed to register commands")
        }
    }

    private fun registerListeners() {
        val eventBus: EventRegistry = eventRegistry

        try {
            PlayerListener().register(eventBus)
            LOGGER.at(Level.INFO).log("[HyCiv] Registered player event listeners")
        } catch (e: Exception) {
            LOGGER.at(Level.WARNING).withCause(e).log("[HyCiv] Failed to register listeners")
        }
    }

    override fun start() {
        LOGGER.at(Level.INFO).log("[HyCiv] Started!")
        LOGGER.at(Level.INFO).log("[HyCiv] Use /hyc help for commands")
    }

    override fun shutdown() {
        LOGGER.at(Level.INFO).log("[HyCiv] Shutting down...")
        instance = null
    }
}