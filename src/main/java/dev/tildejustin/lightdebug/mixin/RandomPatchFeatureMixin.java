package dev.tildejustin.lightdebug.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.tildejustin.lightdebug.LightingGenerationDebug;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RandomPatchFeature.class)
public abstract class RandomPatchFeatureMixin {
    @Inject(
            method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/RandomPatchFeatureConfig;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/placer/BlockPlacer;generate(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Ljava/util/Random;)V")
    )
    private void generated(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, RandomPatchFeatureConfig randomPatchFeatureConfig, CallbackInfoReturnable<Boolean> cir, @Local BlockPos.Mutable mutable) {
        BlockState state;
        if (!(randomPatchFeatureConfig.stateProvider instanceof SimpleBlockStateProvider) || ((state = randomPatchFeatureConfig.stateProvider.getBlockState(null, BlockPos.ORIGIN)) != Blocks.BROWN_MUSHROOM.getDefaultState() && state != Blocks.RED_MUSHROOM.getDefaultState()))
            return;
        System.out.println("generated " + mutable);
        LightingGenerationDebug.mushrooms.add(mutable.toImmutable());
    }
}
