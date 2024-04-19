package net.philippe.fuckspigot.bridgetools.client.mixin;

import net.philippe.fuckspigot.bridgetools.client.BridgetoolsClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(MinecraftClient.class)
public class blockPlaceMixin {
    private HashMap<String,Boolean> activitymap = BridgetoolsClient.Modules;

    @Shadow private int itemUseCooldown;

    @Inject(method = "doItemUse",at = @At(value = "RETURN"))
    public void doItemUse(CallbackInfo ci){
        if(activitymap != null && activitymap.get("fastPlace")) {
            this.itemUseCooldown = 0;
        }
    }

}
