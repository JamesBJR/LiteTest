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
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.NpcUtil;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import net.runelite.api.GraphicsObject;
import net.runelite.api.NPC;
import net.runelite.api.NullNpcID;
import net.runelite.api.DynamicObject;
import net.runelite.api.Animation;

@PluginDescriptor(
		name = "BjR Hider",
		description = "Hide certain projectiles",
		tags = {"projectiles"},
		enabledByDefault = false
)

public class BjHiderPlugin extends Plugin {
	private static final Set<Integer> HideProjectilesList = ImmutableSet.of(
//			1040, NullNpcID.NULL_10881, NullNpcID.NULL_10884  // toxic staff projectile, Lesser Thrall (ghost, skeleton, zombie
	);
	private static final Set<Integer> HideGraphicsList = ImmutableSet.of(
//			2129, NullNpcID.NULL_10884  //green poo from crock
	);
	private static final Set<Integer> HideNPCList = ImmutableSet.of(
			NullNpcID.NULL_10879, NullNpcID.NULL_10882, NullNpcID.NULL_10885 // Superior Thrall (ghost, skeleton, zombie)
	);
	private static final Set<Integer> HideObjectAniList = ImmutableSet.of(
			9520, 9524 // disco yellow 45751, disco red 45750 (A:9524), poison splat 45403 (A:9520)
	);


	@Inject
	private Client client;

	@Inject
	private BjHiderConfig config;

	@Inject
	private Hooks hooks;

	@Inject
	private NpcUtil npcUtil;

	private boolean hideSomeTOA;
	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;

	@Provides
	BjHiderConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(BjHiderConfig.class);
	}

	@Override
	protected void startUp() {
		updateConfig();

		hooks.registerRenderableDrawListener(drawListener);
	}

	@Override
	protected void shutDown() {
		hooks.unregisterRenderableDrawListener(drawListener);
	}

	private void updateConfig() {
		hideSomeTOA = config.hideSomeTOA();

	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e) {
		if (e.getGroup().equals(BjHiderConfig.GROUP)) {
			updateConfig();
		}
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI) {

		if (renderable instanceof Projectile) {

			Projectile projectile = (Projectile) renderable;
//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "","output: " + projectile.getId(), null);
			if (HideProjectilesList.contains(projectile.getId())) {
				return !hideSomeTOA;
			}
			return true;

		} else if (renderable instanceof GraphicsObject) {

			GraphicsObject graphicsObject = (GraphicsObject) renderable;
			//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "","output: " + graphicsObject.getId() + graphicsObject.getAnimation(), null);
			if (HideGraphicsList.contains(graphicsObject.getId())) {
				return !hideSomeTOA;
			}
			return true;


		} else if (renderable instanceof DynamicObject) {

			DynamicObject object = (DynamicObject) renderable;
			Animation animation = object.getAnimation();

		//			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "","Ani:" + animation.getId() +"  model:"+ object.getModel(), null);


			if (HideObjectAniList.contains(animation.getId())) {

				return !hideSomeTOA;
			}

			return true;
		} else if (renderable instanceof NPC) {

			NPC npc = (NPC) renderable;


			if (HideNPCList.contains(npc.getId())) {
				return !hideSomeTOA;
			}
			return !(drawingUI);
		} else {

		//	client.addChatMessage(ChatMessageType.GAMEMESSAGE, "","output: " + renderable.getModel() , null);

			return true;
		}
	}
}
