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

package com.railwayteam.railways.content.conductor.fabric;

import com.railwayteam.railways.content.conductor.ConductorCapItem;
import net.minecraft.world.item.DyeColor;

public class ConductorCapItemImpl extends ConductorCapItem {
	protected ConductorCapItemImpl(Properties props, DyeColor color) {
		super(props, color);
	}

	public static ConductorCapItem create(Properties props, DyeColor color) {
		return new ConductorCapItemImpl(props, color);
	}
}
