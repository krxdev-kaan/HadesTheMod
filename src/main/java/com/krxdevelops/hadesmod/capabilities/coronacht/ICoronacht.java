package com.krxdevelops.hadesmod.capabilities.coronacht;

public interface ICoronacht
{
    public void setLastSpecialTicks(long lastSpecialTicks);

    public boolean isAbleToUseSpecial(long worldTimeIn);

    public long getLastSpecialTicks();
}
