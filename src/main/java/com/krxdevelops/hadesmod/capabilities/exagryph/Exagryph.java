package com.krxdevelops.hadesmod.capabilities.exagryph;

public class Exagryph implements IExagryph
{
    protected long lastReloadTicks;
    protected long lastRocketTicks;
    protected int ammo;
    
    public Exagryph()
    {
        lastReloadTicks = -1;
        lastRocketTicks = -1;
        ammo = 20;
    }

    @Override
    public void decreaseAmmo()
    {
        this.ammo -= 1;
    }

    @Override
    public void setAmmo(int ammo)
    {
        this.ammo = ammo;
    }

    @Override
    public int getAmmo()
    {
        return this.ammo;
    }

    @Override
    public void setLastReloadTicks(long lastReloadTicks)
    {
        this.lastReloadTicks = lastReloadTicks;
    }

    @Override
    public long getLastReloadTicks()
    {
        return this.lastReloadTicks;
    }

    @Override
    public void setLastRocketTicks(long lastRocketTicks)
    {
        this.lastRocketTicks = lastRocketTicks;
    }

    @Override
    public long getLastRocketTicks()
    {
        return this.lastRocketTicks;
    }

    @Override
    public boolean isAbleToFulfill(long worldTimeIn)
    {
        double ticksPassed = worldTimeIn - getLastReloadTicks();
        return ticksPassed >= 20;
    }

    @Override
    public boolean isAbleToShoot()
    {
        return getAmmo() > 0;
    }

    @Override
    public boolean isAbleToRocket(long worldTimeIn)
    {
        double ticksPassed = worldTimeIn - getLastRocketTicks();
        return ticksPassed >= 20;
    }
}
