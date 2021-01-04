package com.krxdevelops.hadesmod.capabilities.malphon;

public interface IMalphon
{
    public void setChargingState(boolean state);
    public void setTicksWhenStartedCharging(long ticksWhenStartedCharging);

    public boolean isAbleToUppercut(long worldTimeIn);

    public boolean getChargingState();
    public long getTicksWhenStartedCharging();
}
