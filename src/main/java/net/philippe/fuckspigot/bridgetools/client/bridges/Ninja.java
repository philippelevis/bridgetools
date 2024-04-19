package net.philippe.fuckspigot.bridgetools.client.bridges;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.philippe.fuckspigot.bridgetools.client.BridgetoolsClient;
import org.jetbrains.annotations.NotNull;


public class Ninja implements Bridge, ClientTickEvents.StartTick {

    @Override
    public void onStartTick(MinecraftClient client) {
        if(BridgetoolsClient.Modules.get("Ninja")) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            GameOptions options = getOptions();
            if(player != null) {
                setupBridge(player);
                Vec3d pos = player.getPos();
                int x = (int) Math.round(pos.getX());
                int y = (int) Math.round(pos.getY());
                int z = (int) Math.round(pos.getZ());
                doSafeWalk(player);
                placeBlock();
                options.backKey.setPressed(true);
            }
        }
    }



    private void setupBridge(PlayerEntity player){
        if(player != null) {
            float yawb4 = player.getYaw();
            //float pitchb4 = player.getPitch();
            float yaw = (float) Math.round(yawb4 / 90) * 90;
            float pitch = 80;
            player.setYaw(yaw);
            player.setPitch(pitch);
        }
    }

    private void doSafeWalk(PlayerEntity player) {
        GameOptions options = getOptions();
        if (player.isOnGround() && !player.noClip) {
            Vec3i minusdir = new Vec3i(0,-1,0);
            ClientWorld world = mc.world;
            //player.sendMessage(Text.literal("doing safeWalk"));
            if (world.getBlockState(player.getBlockPos().add(minusdir)).getBlock() == Blocks.AIR){
                //player.sendMessage(Text.literal("doing safeWalk safety"));
                options.sneakKey.setPressed(true);
            }else{
                //player.sendMessage(Text.literal("doing safeWalk unsafety"));
                options.sneakKey.setPressed(false);
            }
        }
    }
}
