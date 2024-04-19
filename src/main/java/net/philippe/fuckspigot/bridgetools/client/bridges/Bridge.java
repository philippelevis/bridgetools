package net.philippe.fuckspigot.bridgetools.client.bridges;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.philippe.fuckspigot.bridgetools.client.mixin.IMinecraftClient;

public interface Bridge{
    public int distance = 0;
    public String type = "none";
    public PlayerEntity player = MinecraftClient.getInstance().player;
    final MinecraftClient mc = MinecraftClient.getInstance();
    public default int placeBlock(){
        PlayerEntity player = MinecraftClient.getInstance().player;
        MinecraftClient mc = MinecraftClient.getInstance();
        int blockSlot = getBlockInHotbar(player);
        if(blockSlot != -1){
            player.getInventory().selectedSlot = blockSlot;
            ((IMinecraftClient)mc).idoItemUse();
            return 0;
        } else{
            return 1;//no block found
        }

    }

    public default GameOptions getOptions(){
        return MinecraftClient.getInstance().options;
    }

    public default int getBlockInHotbar(PlayerEntity player){
        PlayerInventory inv = player.getInventory();
        for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
            if(inv.getStack(i).getItem() instanceof BlockItem){
                return i;
            }
        }
        return -1;
    }
}
