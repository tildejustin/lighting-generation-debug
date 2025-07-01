package dev.tildejustin.lightdebug.mixin;

import dev.tildejustin.lightdebug.LightingGenerationDebug;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin {
    @Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;"))
    private void logGeneratedMushrooms(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        System.out.println("mushrooms generated");
        for (BlockPos place : LightingGenerationDebug.mushrooms) {
            System.out.println(place);
        }
        LightingGenerationDebug.mushrooms.clear();
    }
}
