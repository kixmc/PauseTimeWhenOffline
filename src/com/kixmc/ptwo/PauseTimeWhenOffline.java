package com.kixmc.ptwo;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PauseTimeWhenOffline extends JavaPlugin implements Listener {

    boolean on = false;

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        setTime(false);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!on && !this.getServer().getOnlinePlayers().isEmpty()) setTime(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (on && this.getServer().getOnlinePlayers().isEmpty()) setTime(false);
        }, 1L);
    }

    public void setTime(boolean t) {
        for(World w : Bukkit.getWorlds()) w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, t);
        on = t;
    }

}