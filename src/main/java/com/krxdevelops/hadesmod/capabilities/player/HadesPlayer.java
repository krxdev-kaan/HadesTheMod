package com.krxdevelops.hadesmod.capabilities.player;

public class HadesPlayer implements IHadesPlayer
{
    private boolean isNextFallCanceled;

    public HadesPlayer()
    {
        isNextFallCanceled = false;
    }

    @Override
    public void setNextFallCanceled(boolean isCanceled)
    {
        isNextFallCanceled = isCanceled;
    }

    @Override
    public boolean getNextFallCanceled()
    {
        return isNextFallCanceled;
    }
}
