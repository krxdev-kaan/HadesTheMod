package com.krxdevelops.hadesmod.util;

import com.krxdevelops.hadesmod.client.GuiOverlay;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface IHasOverlay {
    public void renderOverlay(RenderGameOverlayEvent.Post event, GuiOverlay gui);
}
