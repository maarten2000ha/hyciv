package me.maarten2000ha.hyciv.utils

import com.hypixel.hytale.server.core.universe.Universe

object ServerUtil {
    @JvmStatic
    fun executeWorld(task: Runnable) {
        Universe.get().defaultWorld!!.execute(task)
    }
}