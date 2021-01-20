package com.krxdevelops.hadesmod.capabilities.stygius;

public class Stygius implements IStygius
{
    protected boolean isCharging;
    protected long ticksWhenStartedCharging;

    public Stygius()
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
    public boolean isAbleToSmash(long worldTimeIn)
    {
        double ticksPassed = worldTimeIn - getTicksWhenStartedCharging();
        return ticksPassed >= 30;
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
