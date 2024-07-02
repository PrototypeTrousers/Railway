/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2024 The Railways Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.railwayteam.railways.mixin;

import com.railwayteam.railways.mixin_interfaces.ILimitedGlobalStation;
import com.railwayteam.railways.mixin_interfaces.RedstoneControlled;
import com.railwayteam.railways.multiloader.ServerGetter;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import com.simibubi.create.content.trains.station.GlobalStation;
import io.github.fabricators_of_create.porting_lib.util.ServerLifecycleHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = GlobalStation.class, remap = false)
public abstract class MixinGlobalStation extends SingleBlockEntityEdgePoint implements ILimitedGlobalStation, RedstoneControlled {
    @Shadow @Nullable public abstract Train getNearestTrain();

    @Shadow @Nullable public abstract Train getImminentTrain();
    @Unique
    public boolean redstonePowered = false;

    private boolean limitEnabled;

    @Override
    public boolean isStationEnabled() {
        return !limitEnabled || (getNearestTrain()) == null;
    }

    @Override
    public void railways$setRedstonePowered(boolean value) {
        redstonePowered = value;
    }

    @Override
    public boolean isRedstonePowered() {
        return redstonePowered;
    }

    @Override
    public Train getDisablingTrain() {
        if (!limitEnabled)
            return null;
        return (getNearestTrain());
    }

    @Override
    public Train orDisablingTrain(Train before, Train except) {
        if (before == null || before == except)
            before = getDisablingTrain();
        return before;
    }

    @Override
    public void setLimitEnabled(boolean limitEnabled) {
        this.limitEnabled = limitEnabled;
    }

    @Override
    public boolean isLimitEnabled() {
        return limitEnabled;
    }

    @Inject(method = "blockEntityAdded", at = @At("TAIL"))
    private void railways$blockEntityAdded(BlockEntity blockEntity, boolean front, CallbackInfo ci){
        redstonePowered = blockEntity.getLevel().hasNeighborSignal(blockEntity.getBlockPos());
    }

    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"), remap = true)
    private void readLimit(CompoundTag nbt, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        limitEnabled = nbt.getBoolean("LimitEnabled");
        if (nbt.contains("RedstonePowered")) {
            redstonePowered = nbt.getBoolean("RedstonePowered");
        } else {
            redstonePowered = ServerGetter.get().getLevel(this.blockEntityDimension).hasNeighborSignal(this.blockEntityPos);
        }
    }

    @Inject(method = "read(Lnet/minecraft/network/FriendlyByteBuf;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"), remap = true)
    private void readNetLimit(FriendlyByteBuf buffer, DimensionPalette dimensions, CallbackInfo ci) {
        limitEnabled = buffer.readBoolean();
        redstonePowered = buffer.readBoolean();
    }

    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"), remap = true)
    private void writeLimit(CompoundTag nbt, DimensionPalette dimensions, CallbackInfo ci) {
        nbt.putBoolean("LimitEnabled", limitEnabled);
        nbt.putBoolean("RedstonePowered", redstonePowered);
    }

    @Inject(method = "write(Lnet/minecraft/network/FriendlyByteBuf;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"), remap = true)
    private void writeNetLimit(FriendlyByteBuf buffer, DimensionPalette dimensions, CallbackInfo ci) {
        buffer.writeBoolean(limitEnabled);
        buffer.writeBoolean(redstonePowered);
    }
}
