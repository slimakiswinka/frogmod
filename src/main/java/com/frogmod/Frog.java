package com.frogmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frog implements ModInitializer {

    private static final Random RANDOM = new Random();

    private static final List<ItemStack> DROP_ITEMS = new ArrayList<>();

    @Override
    public void onInitialize() {
        DROP_ITEMS.add(new ItemStack(Items.DIRT, 1));
        DROP_ITEMS.add(new ItemStack(Items.COARSE_DIRT, 1));
        DROP_ITEMS.add(new ItemStack(Items.MOSS_BLOCK, 1));

        ServerTickEvents.START_WORLD_TICK.register(this::onWorldTick);
    }

    private void onWorldTick(ServerWorld world) {
        if (world.getTime() % (20 * 60 * (3 + RANDOM.nextInt(3))) == 0) {
            world.getEntitiesByType(EntityType.FROG, frog -> true).forEach(frogEntity -> {
                BlockPos frogPos = frogEntity.getBlockPos();
				double itemX = frogPos.getX();
                double itemY = frogPos.getY() + 0.5;
                double itemZ = frogPos.getZ();
                ItemStack randomItem = DROP_ITEMS.get(RANDOM.nextInt(DROP_ITEMS.size())).copy();
                world.spawnEntity(new net.minecraft.entity.ItemEntity(world, itemX, itemY, itemZ, randomItem));
            });
        }
    }
}
