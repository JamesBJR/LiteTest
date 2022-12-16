package com.BjHider;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Projectile;
import net.runelite.api.Renderable;
import net.runelite.client.callback.Hooks;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.GraphicsObject;
import net.runelite.api.NPC;
import net.runelite.api.DynamicObject;
import net.runelite.api.Animation;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.api.ChatMessageType;
@PluginDescriptor(
		name = "Seizure Hider",
		description = "Hide certain projectiles and objects that cause seizures",
		tags = {"projectiles, hide, seizure, bjr"},
		enabledByDefault = false
)

public class BjHiderPlugin extends Plugin {
	private static final Set<Integer> HideProjectilesList = ImmutableSet.of(
//			1040  // toxic staff projectile 1040
	);
	private static final Set<Integer> HideGraphicsList = ImmutableSet.of(
			2129	 //green poo from crock 2129
	);
	private static final Set<Integer> HideNPCList = ImmutableSet.of(
//
	);
	private static final Set<Integer> HideObjectAniList = ImmutableSet.of(
			 9524 // disco yellow 45751, disco red 45750 (A:9524), poison splat 45403 (A:9520)

	);


	@Inject
	private Client client;

	@Inject
	private BjHiderConfig config;
	@Inject
	private BjHiderOverlay overlay;

	@Inject
	private Hooks hooks;

	@Inject
	private OverlayManager overlayManager;


	private int counter = 0;
	private int lastId = 0;
	private boolean hideSomeTOA;
	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

	@Provides
	BjHiderConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(BjHiderConfig.class);
	}

	@Override
	protected void startUp() {
		updateConfig();
		overlayManager.add(overlay);
		hooks.registerRenderableDrawListener(drawListener);
	}

	@Override
	protected void shutDown() {
		hooks.unregisterRenderableDrawListener(drawListener);
		overlayManager.remove(overlay);
	}

	private void updateConfig() {
		hideSomeTOA = config.hideSomeTOA();
	}

	private void Report(Integer getId, String type){

			if (getId == 9524 & type == "Dynamic Obj"){
				counter++;
			}
	}
	public int HideCount(){
	return counter;
	}
	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI) {

		if (renderable instanceof Projectile) {

			Projectile projectile = (Projectile) renderable;

			if (HideProjectilesList.contains(projectile.getId())) {
				Report(projectile.getId(),"Projectile");
				return !hideSomeTOA;
			}

			return true;

		} else if (renderable instanceof GraphicsObject) {

			GraphicsObject graphicsObject = (GraphicsObject) renderable;

			if (HideGraphicsList.contains(graphicsObject.getId())) {
				Report(graphicsObject.getId(),"GfX Obj");
				return !hideSomeTOA;
			}

			return true;


		} else if (renderable instanceof DynamicObject) {

			DynamicObject object = (DynamicObject) renderable;
			Animation animation = object.getAnimation();

			if (HideObjectAniList.contains(animation.getId())) {
				Report(animation.getId(),"Dynamic Obj");
				return !hideSomeTOA;
			}

			return true;

		} else if (renderable instanceof NPC) {

			NPC npc = (NPC) renderable;

			if (HideNPCList.contains(npc.getId())) {
				Report(npc.getId(),"NPC");
				return !hideSomeTOA;
			}

			return !(drawingUI);

		} else {


			return true;
		}
	}
}
