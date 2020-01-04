package fr.cleymax.privateblocks.utils;

import org.bukkit.World;

/**
 * File <b>WorldNotFoundException</b> located on fr.cleymax.privateblocks.utils is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */

public class WorldNotFoundException extends Exception {

	public WorldNotFoundException(String worldName)
	{
		super("A world was not found on the server! Named: " + worldName);
	}
}
