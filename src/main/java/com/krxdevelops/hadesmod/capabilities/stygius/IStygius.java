package com.krxdevelops.hadesmod.capabilities.stygius;

public interface IStygius
{
    public void setChargingState(boolean state);
    public void setTicksWhenStartedCharging(long ticksWhenStartedCharging);

    public boolean isAbleToSmash(long worldTimeIn);

    public boolean getChargingState();
    public long getTicksWhenStartedCharging();
}
