package com.BjHider;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TitleComponent;

class BjHiderOverlay extends OverlayPanel
{

    private final BjHiderPlugin plugin;
    private final BjHiderConfig config;


    @Inject
    private BjHiderOverlay(BjHiderPlugin plugin, BjHiderConfig config)
    {
        super(plugin);
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.plugin = plugin;
        this.config = config;
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "BjHider overlay");
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Integer count = plugin.HideCount();

        panelComponent.getChildren().add(TitleComponent.builder()
                .text("ToA Hider Active")
                .build());
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Discos hidden: " + count.toString())
                .build());

        panelComponent.setPreferredSize(new Dimension(
                100,
                1));


        return super.render(graphics);
    }

}