package com.krxdevelops.hadesmod.capabilities.malphon;

public class Malphon implements IMalphon
{
    protected boolean isCharging;
    protected long ticksWhenStartedCharging;

    public Malphon()
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
    public boolean isAbleToUppercut(long worldTimeIn)
    {
        double ticksPassed = worldTimeIn - getTicksWhenStartedCharging();
        return ticksPassed >= 20;
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
