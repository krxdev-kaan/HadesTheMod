package com.krxdevelops.hadesmod.capabilities.exagryph;

public interface IExagryph
{
    public void decreaseAmmo();
    public void setAmmo(int ammo);
    public int getAmmo();

    public void setLastReloadTicks(long lastReloadTicks);
    public long getLastReloadTicks();

    public void setLastRocketTicks(long lastRocketTicks);
    public long getLastRocketTicks();

    public boolean isAbleToFulfill(long worldTimeIn);
    public boolean isAbleToShoot();
    public boolean isAbleToRocket(long worldTimeIn);
}
