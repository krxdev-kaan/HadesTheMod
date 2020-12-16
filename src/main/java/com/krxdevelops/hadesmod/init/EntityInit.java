package com.krxdevelops.hadesmod.init;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.ArrayList;
import java.util.List;

public class EntityInit
{
    public static final List<EntityEntry> ENTITIES = new ArrayList<EntityEntry>();

    public static final EntityEntry entityShieldOfChaos = new EntityEntry(EntityShieldOfChaos.class, "hadesmod:shieldofchaos");
}
