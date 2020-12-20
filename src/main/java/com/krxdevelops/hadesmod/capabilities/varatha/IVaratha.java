package com.krxdevelops.hadesmod.capabilities.varatha;

public interface IVaratha
{
    public void setChargingState(boolean state);
    public void setTicksWhenStartedCharging(long ticksWhenStartedCharging);

    public boolean isAbleToThrow(long worldTimeIn);

    public boolean getChargingState();
    public long getTicksWhenStartedCharging();
}
