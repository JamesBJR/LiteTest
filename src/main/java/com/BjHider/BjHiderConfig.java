package com.BjHider;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface BjHiderConfig extends Config {
	String GROUP = "BjRhider";

	@ConfigItem(
			position = 1,
			keyName = "hideSpells",
			name = "Hide Spells",
			description = "Configures whether or not certain projectiles are shown"
	)
	default boolean hideSomeTOA() {
		return true;
	}
}