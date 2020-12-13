package com.krxdevelops.hadesmod.capabilities.aegis;

public interface IAegis
{
    public void setChargingState(boolean state);
    public void setTicksWhenStartedCharging(long ticksWhenStartedCharging);

    public boolean isAbleToThrow(long worldTimeIn);

    public boolean getChargingState();
    public long getTicksWhenStartedCharging();
}
