package net.philippe.fuckspigot.bridgetools.client.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(MinecraftClient.class)
public interface IMinecraftClient {

    @Invoker("doItemUse")
    public void idoItemUse();

    @Invoker("doAttack")
    public boolean idoAttack();
}
