package com.krxdevelops.hadesmod.capabilities.aegis;

import net.minecraft.client.Minecraft;

public class Aegis implements IAegis
{
    protected boolean isCharging;
    protected long ticksWhenStartedCharging;

    public Aegis()
    {
        isCharging = false;
        ticksWhenStartedCharging = -1;
    }

    @Override
    public void setChargingState(boolean state)
    {
        isCharging = state;
    }

    @Override
    public void setTicksWhenStartedCharging(long ticksWhenStartedCharging)
    {
        this.ticksWhenStartedCharging = ticksWhenStartedCharging;
    }

    @Override
    public boolean isAbleToThrow(long worldTimeIn)
    {
        double ticksPassed = worldTimeIn - getTicksWhenStartedCharging();
        return ticksPassed >= 60;
    }

    @Override
    public boolean getChargingState()
    {
        return isCharging;
    }

    @Override
    public long getTicksWhenStartedCharging()
    {
        return ticksWhenStartedCharging;
    }
}
