package com.BjHider;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface BjHiderConfig extends Config {
	String GROUP = "BjRhider";

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