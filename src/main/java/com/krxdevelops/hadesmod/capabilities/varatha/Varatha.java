package com.krxdevelops.hadesmod.capabilities.varatha;

import com.krxdevelops.hadesmod.capabilities.varatha.recover.IVarathaRecover;

public class Varatha implements IVaratha
{
    protected boolean isCharging;
    protected long ticksWhenStartedCharging;

    public Varatha()
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
        return ticksPassed >= 15;
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
