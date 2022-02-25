package net.dark_roleplay.projectbrazier.feature_client.listeners;

import com.google.j2objc.annotations.Weak;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ResourceReloadUtil implements PreparableReloadListener {

	private static Map<Object, Runnable> RELOAD_LISTENERS =  new WeakHashMap<Object, Runnable>();

	public static void addReloadListener(Object owner, Runnable run){
		RELOAD_LISTENERS.put(owner, run);
	}

	public static void registerReloadListeners(RegisterClientReloadListenersEvent event){
		event.registerReloadListener(new ResourceReloadUtil());
	}

	@Override
	public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager resourceManager, ProfilerFiller p_10640_, ProfilerFiller p_10641_, Executor p_10642_, Executor p_10643_) {
		return CompletableFuture.runAsync(() -> {
			for(Runnable runnable : RELOAD_LISTENERS.values()) runnable.run();
		}).thenCompose(barrier::wait);
	}
}
