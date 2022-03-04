package com.krxdevelops.hadesmod.capabilities.exagryph;

public interface IExagryph
{
    public void decreaseAmmo();
    public void setAmmo(int ammo);
    public int getAmmo();

    public int getMaxAmmo();

    public void setLastReloadTicks(long lastReloadTicks);
    public long getLastReloadTicks();

    public void setLastRocketTicks(long lastRocketTicks);
    public long getLastRocketTicks();

    public void setReloadingState(boolean state);
    public boolean getReloadingState();

    public boolean isAbleToFulfill(long worldTimeIn);
    public boolean isAbleToShoot();
    public boolean isAbleToRocket(long worldTimeIn);
}
