package com.railwayteam.railways.content.custom_bogeys.blocks.gauge.standard.large;

import com.railwayteam.railways.content.custom_bogeys.CRBogeyBlock;
import com.railwayteam.railways.registry.CRBogeyStyles;
import com.simibubi.create.content.trains.bogey.BogeySizes;
import com.simibubi.create.content.trains.bogey.BogeyStyle;
import net.minecraft.world.phys.Vec3;

public class LargeCreateStyle080BogeyBlock extends CRBogeyBlock {
    public LargeCreateStyle080BogeyBlock(Properties props) {
        this(props, CRBogeyStyles.LARGE_CREATE_STYLED_0_8_0, BogeySizes.LARGE);
    }

    protected LargeCreateStyle080BogeyBlock(Properties props, BogeyStyle defaultStyle, BogeySizes.BogeySize size) {
        super(props, defaultStyle, size);
    }

    //fixme fix offset
    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32f, 86 / 32f);
    }
}
