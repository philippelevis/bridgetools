package net.philippe.fuckspigot.bridgetools.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.philippe.fuckspigot.bridgetools.client.bridges.Berry;
import net.philippe.fuckspigot.bridgetools.client.bridges.Ninja;
import net.philippe.fuckspigot.bridgetools.client.bridges.SideNinja;
import net.philippe.fuckspigot.bridgetools.client.mixin.IMinecraftClient;
import net.philippe.fuckspigot.bridgetools.client.mixin.blockPlaceMixin;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class BridgetoolsClient implements ClientModInitializer {
    private static KeyBinding fastplace;
    private static KeyBinding ninjabind;
    private static KeyBinding sideninjabind;
    private static KeyBinding fruitbind;
    public static HashMap<String, Boolean> Modules;
    static {
        Modules = new HashMap<>();
        Modules.put("fastPlace",false);
        Modules.put("Ninja",false);
        Modules.put("SideNinja",false);
        Modules.put("fruitberry",false);
    }

    @Override
    public void onInitializeClient() {
        Ninja ninja = new Ninja();
        SideNinja sideNinja = new SideNinja();
        Berry berry = new Berry();
        ClientTickEvents.START_CLIENT_TICK.register(ninja);
        ClientTickEvents.START_CLIENT_TICK.register(sideNinja);
        ClientTickEvents.START_CLIENT_TICK.register(berry);
        fastplace = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bridgetools.fastplace", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H,"category.bridgetools.name"));
        ninjabind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bridgetools.ninja", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,"category.bridgetools.name"));
        sideninjabind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bridgetools.sideninja", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G,"category.bridgetools.name"));
        fruitbind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.bridgetools.fruit", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_BRACKET,"category.bridgetools.name"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {while(fastplace.wasPressed()){client.player.sendMessage(Text.literal("fastplace toggled"),true);Modules.put("fastPlace",!Modules.get("fastPlace"));}});
        ClientTickEvents.END_CLIENT_TICK.register(client -> {while(ninjabind.wasPressed()){client.player.sendMessage(Text.literal("ninja toggled"),true);Modules.put("Ninja",!Modules.get("Ninja"));if(MinecraftClient.getInstance().options.backKey.isPressed()){MinecraftClient.getInstance().options.backKey.setPressed(false);MinecraftClient.getInstance().options.sneakKey.setPressed(false);};}});
        ClientTickEvents.END_CLIENT_TICK.register(client -> {while(sideninjabind.wasPressed()){client.player.sendMessage(Text.literal("sideninja toggled"),true);Modules.put("SideNinja",!Modules.get("SideNinja"));if(MinecraftClient.getInstance().options.backKey.isPressed()){MinecraftClient.getInstance().options.backKey.setPressed(false);MinecraftClient.getInstance().options.leftKey.setPressed(false);MinecraftClient.getInstance().options.sneakKey.setPressed(false);sideNinja.alreadysetup = false;};}});
        ClientTickEvents.END_CLIENT_TICK.register(client -> {while(fruitbind.wasPressed()){client.player.sendMessage(Text.literal("fruit toggled"),true);Modules.put("fruitberry",!Modules.get("fruitberry"));if(MinecraftClient.getInstance().options.backKey.isPressed()){MinecraftClient.getInstance().options.backKey.setPressed(false);MinecraftClient.getInstance().options.forwardKey.setPressed(false);};}});
    }

}
