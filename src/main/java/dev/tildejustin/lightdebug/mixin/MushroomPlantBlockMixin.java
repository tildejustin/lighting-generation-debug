package dev.tildejustin.lightdebug.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.tildejustin.lightdebug.LightingGenerationDebug;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MushroomPlantBlock.class)
public abstract class MushroomPlantBlockMixin {
    @ModifyExpressionValue(method = "canPlaceAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldView;getBaseLightLevel(Lnet/minecraft/util/math/BlockPos;I)I"))
    private int logLightLevelCalls(int original, BlockState state, WorldView world, BlockPos pos) {
        System.out.println("checked light mushroom " + original + " " + pos + " " + world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.EMPTY, false).getStatus());
        return original;
    }

    @WrapMethod(method = "canPlaceAt")
    private boolean registerallfailures(BlockState state, WorldView world, BlockPos pos, Operation<Boolean> original) {
        boolean result = original.call(state, world, pos);
        if (world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false) == null) return result;
        if (!result) {
            System.out.println("post processing removed " + pos);
            LightingGenerationDebug.mushrooms.removeIf(a -> a.equals(pos));
        }
        return result;
    }
}
