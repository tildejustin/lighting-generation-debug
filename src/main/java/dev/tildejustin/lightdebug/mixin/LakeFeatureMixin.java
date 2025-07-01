package dev.tildejustin.lightdebug.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Random;

@Mixin(LakeFeature.class)
public abstract class LakeFeatureMixin {
    @ModifyArg(
            method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/SingleStateFeatureConfig;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getLightLevel(Lnet/minecraft/world/LightType;Lnet/minecraft/util/math/BlockPos;)I")
    )
    private BlockPos capturePos(BlockPos pos, @Share("pos") LocalRef<BlockPos> targetPos) {
        targetPos.set(pos);
        return pos;
    }

    @ModifyExpressionValue(
            method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/SingleStateFeatureConfig;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getLightLevel(Lnet/minecraft/world/LightType;Lnet/minecraft/util/math/BlockPos;)I")
    )
    private int logLakeLight(int original, StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, SingleStateFeatureConfig singleStateFeatureConfig, @Share("pos") LocalRef<BlockPos> targetPos) {
        BlockPos pos = targetPos.get();
        System.out.println("checked light lake " + original + " " + pos + " " + world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.EMPTY, false).getStatus());
        return original;
    }
}
