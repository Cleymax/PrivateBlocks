package fr.cleymax.privateblocks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * File <b>LocationParser</b> located on fr.cleymax.privateblocks.utils is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */

public class LocationParser {

	public static String fromLocation(Location location)
	{
		return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
	}

	public static Location fromString(String s) throws WorldNotFoundException
	{
		final String[] part  = s.split(";");
		final World    world = Bukkit.getWorld(part[0]);
		if (world == null)
			throw new WorldNotFoundException(part[0]);
		return new Location(world, Integer.parseInt(part[1]), Integer.parseInt(part[2]), Integer.parseInt(part[3]));
	}
}
