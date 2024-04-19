package net.philippe.fuckspigot.bridgetools.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.philippe.fuckspigot.bridgetools.client.BridgetoolsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(InventoryScreen.class)
public class setScreenMixin {
    @Inject(method = "init",at=@At("HEAD"))
    public void resetmodules(CallbackInfo ci){
        HashMap<String, Boolean> Modules = BridgetoolsClient.Modules;
        Modules.put("Ninja",false);
        Modules.put("SideNinja",false);
        Modules.put("fruitberry",false);
    }
}
