package com.krxdevelops.hadesmod.capabilities.coronacht;

public class Coronacht implements ICoronacht
{
    protected long lastSpecialTicks;

    public Coronacht()
    {
        lastSpecialTicks = -1;
    }

    @Override
    public void setLastSpecialTicks(long lastSpecialTicks)
    {
        this.lastSpecialTicks = lastSpecialTicks;
    }

    @Override
    public boolean isAbleToUseSpecial(long worldTimeIn)
    {
        double ticksPassed = worldTimeIn - getLastSpecialTicks();
        return ticksPassed >= 20;
    }

    @Override
    public long getLastSpecialTicks()
    {
        return lastSpecialTicks;
    }
}
