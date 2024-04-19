package net.philippe.fuckspigot.bridgetools.client.bridges;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.philippe.fuckspigot.bridgetools.client.BridgetoolsClient;

public class Berry implements Bridge, ClientTickEvents.StartTick{
    boolean alreadysetup = false;
    boolean justjumped = false;
    int starty = 0;
    @Override
    public void onStartTick(MinecraftClient client) {
        if(BridgetoolsClient.Modules.get("fruitberry")){
            PlayerEntity player = MinecraftClient.getInstance().player;
            GameOptions options = getOptions();
            if(!alreadysetup){setupBridge(player);}
            Bridge(player,options);
        }
    }

    private void setupBridge(PlayerEntity player){
        if(player != null) {
            float yawb4 = player.getYaw();
            //float pitchb4 = player.getPitch();
            float yaw = (float) Math.round(yawb4 / 90) * 90;
            float pitch = 75;
            player.setYaw(yaw);
            player.setPitch(pitch);
            justjumped = true;
            starty = player.getBlockY()-1;
        }
    }

    private void doSafejump(PlayerEntity player) {
        GameOptions options = getOptions();
        if (player.isOnGround() && !player.noClip) {
            Vec3i minusdir = new Vec3i(0,-1,0);
            ClientWorld world = mc.world;
            //player.sendMessage(Text.literal("doing safeWalk"));
            if (world.getBlockState(player.getBlockPos().add(minusdir)).getBlock() == Blocks.AIR){
                //player.sendMessage(Text.literal("doing safeWalk safety"));
                starty = player.getBlockY()-1;
                justjumped = true;
                player.jump();
            }
        }
    }

    public boolean getunsafeberry(PlayerEntity player){
        ClientWorld world = mc.world;
        if(player.getY() > starty+1){
            BlockPos pos = player.getBlockPos().add(0,-2,0);
            Block below = world.getBlockState(pos).getBlock();
            Block belowside = world.getBlockState(pos.add(player.getHorizontalFacing().getVector())).getBlock();
            if(below == Blocks.AIR && belowside == Blocks.AIR){
                return false;
            }
        }else{
            BlockPos pos = player.getBlockPos().add(0,-1,0);
            Block below = world.getBlockState(pos).getBlock();
            Block belowside = world.getBlockState(pos.add(player.getHorizontalFacing().getVector())).getBlock();
            if(below == Blocks.AIR && belowside == Blocks.AIR){
                return false;
            }
        }
        return true;
    }

    public Vec3d floatify(Vec3i v){
        return new Vec3d(v.getX(), v.getY(), v.getZ());
    }

    public void Bridge(PlayerEntity player, GameOptions options){
        if (getunsafeberry(player)){
            options.backKey.setPressed(true);
        }else{
            options.backKey.setPressed(false);
        }

        HitResult hit = MinecraftClient.getInstance().crosshairTarget;
        BlockHitResult hitResult = new BlockHitResult(new Vec3d(0,0,0),Direction.UP,new BlockPos(0,0,0),false);
        if(hit.getType() == HitResult.Type.BLOCK){
            hitResult = (BlockHitResult) hit;
        }
        doSafejump(player);
        if(player.getPos().distanceTo(floatify(player.getBlockPos())) > 0.5 && !justjumped){
            options.forwardKey.setPressed(true);
        }else{
            options.forwardKey.setPressed(false);
        }
        if(!player.isOnGround() && hitResult.getSide() != Direction.UP){
            justjumped = true;
            placeBlock();
        }
    }
}
