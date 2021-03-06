package com.bergerkiller.bukkit.common.internal;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import com.bergerkiller.bukkit.common.EntityMap;
import com.bergerkiller.bukkit.common.PluginBase;
import com.bergerkiller.bukkit.common.Task;
import com.bergerkiller.bukkit.common.utils.CommonUtil;
import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.bergerkiller.bukkit.common.utils.NativeUtil;

@SuppressWarnings("unused")
class CommonListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	private void onPluginEnable(final PluginEnableEvent event) {
		String name = LogicUtil.fixNull(event.getPlugin().getName(), "");
		for (PluginBase pb : CommonPlugin.getInstance().plugins) {
			pb.updateDependency(event.getPlugin(), name, true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPluginDisable(PluginDisableEvent event) {
		String name = LogicUtil.fixNull(event.getPlugin().getName(), "");
		for (PluginBase pb : CommonPlugin.getInstance().plugins) {
			pb.updateDependency(event.getPlugin(), name, false);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onWorldInit(WorldInitEvent event) {
		CommonPlugin.getInstance().notifyWorldAdded(event.getWorld());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onWorldUnload(WorldUnloadEvent event) {
		if (event.isCancelled()) {
			return;
		}
		CommonWorldListener listener = CommonPlugin.getInstance().worldListeners.remove(event.getWorld());
		if (listener != null) {
			listener.disable();
		}
	}
}
