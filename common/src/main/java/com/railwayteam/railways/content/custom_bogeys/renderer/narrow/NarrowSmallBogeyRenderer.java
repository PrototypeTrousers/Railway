/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2024 The Railways Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.railwayteam.railways.content.custom_bogeys.renderer.narrow;

import com.jozufozu.flywheel.api.MaterialManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.entity.CarriageBogey;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.nbt.CompoundTag;

import static com.railwayteam.railways.registry.CRBlockPartials.NARROW_FRAME;
import static com.railwayteam.railways.registry.CRBlockPartials.NARROW_WHEELS;

public class NarrowSmallBogeyRenderer extends BogeyRenderer {
    @Override
    public void initialiseContraptionModelData(MaterialManager materialManager, CarriageBogey carriageBogey) {
        createModelInstance(materialManager, NARROW_FRAME);
        createModelInstance(materialManager, NARROW_WHEELS, 2);
        createModelInstance(materialManager, AllPartialModels.SHAFT_HALF, 2);
    }

    @Override
    public BogeySizes.BogeySize getSize() {
        return BogeySizes.SMALL;
    }

    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
        boolean inInstancedContraption = vb == null;

        BogeyModelData[] secondaryShafts = getTransform(AllPartialModels.SHAFT_HALF, ms, inInstancedContraption, 2);

        for (int i : Iterate.zeroAndOne) {
            secondaryShafts[i].translate(-.5, 1 / 16., -(18 / 16.) + (i * 12 / 16.))
                    .centre()
                    .rotateZ(wheelAngle)
                    .unCentre()
                    .render(ms, light, vb);
        }

        getTransform(NARROW_FRAME, ms, inInstancedContraption)
                .translate(0, 5 / 16f, 0)
                .render(ms, light, vb);

        BogeyModelData[] wheels = getTransform(NARROW_WHEELS, ms, inInstancedContraption, 2);
        for (int side : Iterate.positiveAndNegative) {
            if (!inInstancedContraption)
                ms.pushPose();
            BogeyModelData wheel = wheels[(side + 1) / 2];
            wheel.translate(0, 11 / 16., side * (10 / 16.))
                    .rotateX(wheelAngle)
                    .translate(0, 0, 0)
                    .render(ms, light, vb);
            if (!inInstancedContraption)
                ms.popPose();
        }
    }
}
