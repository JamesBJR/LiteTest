package com.BjHider;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Seizure Hider")
public interface BjHiderConfig extends Config {
	String GROUP = "Seizure Hider";

	@ConfigItem(
			position = 1,
			keyName = "ToA",
			name = "Hide ToA Disco",
			description = "Configures whether or not certain render-ables are shown"
	)
	default boolean hideSomeTOA() {
		return true;
	}
}