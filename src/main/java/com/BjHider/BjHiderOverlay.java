package com.BjHider;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ColorUtil;


import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;


public class BjHiderOverlay extends Overlay {

    private BjHiderOverlay plugin;
    private BjHiderConfig config;
    private Client client;
    private TooltipManager tooltipManager;

    private PanelComponent panelComponent = new PanelComponent();
    private boolean hideSomeTOA;

    @Inject
    public BjHiderOverlay(BjHiderPlugin plugin, BjHiderConfig config, Client client, TooltipManager tooltipManager) {
        super(plugin);
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setLayer(OverlayLayer.ABOVE_SCENE);
  //      this.plugin = plugin;  //set plugin field to plugin object given as input
        this.config = config;
        this.client = client;
        this.tooltipManager = tooltipManager;
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Max hit overlay"));
    }


    private void updateConfig() {
        hideSomeTOA = config.hideSomeTOA();
    }
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear();

        if (!hideSomeTOA) {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Max Hit:")
                    .right("Right shit")
                    .build());
        }

        return panelComponent.render(graphics);
    }
}
