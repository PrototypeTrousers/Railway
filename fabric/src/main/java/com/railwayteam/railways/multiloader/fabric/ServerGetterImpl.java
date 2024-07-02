/*
 * Steam 'n' Rails
 * Copyright (c) 2024 The Railways Team
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

package com.railwayteam.railways.multiloader.fabric;

import com.railwayteam.railways.multiloader.ServerGetter;
import io.github.fabricators_of_create.porting_lib.util.ServerLifecycleHooks;
import net.minecraft.server.MinecraftServer;

public class ServerGetterImpl extends ServerGetter {
    public static MinecraftServer get() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
