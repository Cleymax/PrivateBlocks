package fr.cleymax.privateblocks;

import fr.cleymax.privateblocks.utils.WorldNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.*;
import java.util.stream.Collectors;

import static fr.cleymax.privateblocks.utils.LocationParser.fromLocation;
import static fr.cleymax.privateblocks.utils.LocationParser.fromString;

/**
 * File <b>ProtectedBlock</b> located on fr.cleymax.privateblocks is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */

public class ProtectedBlock {

	private Block      block;
	private List<UUID> access;
	private String     message;
	private boolean    cancelPiston;

	public ProtectedBlock(Block block)
	{
		this(block, Collections.emptyList());
	}

	public ProtectedBlock(Block block, String message)
	{
		this(block, Collections.emptyList(), message);
	}

	public ProtectedBlock(Block block, List<UUID> access)
	{
		this(block, access, null);
	}

	public ProtectedBlock(Block block, List<UUID> access, String message)
	{
		this(block, access, message, false);
	}

	public ProtectedBlock(Block block, List<UUID> access, String message, boolean cancelPiston)
	{
		this.block = block;
		this.access = access;
		this.message = message;
		this.cancelPiston = cancelPiston;
	}

	public Block getBlock()
	{
		return this.block;
	}

	public List<UUID> getAccess()
	{
		return this.access;
	}

	public Location getLocation()
	{
		return this.block.getLocation();
	}

	public boolean cancelPiston()
	{
		return this.cancelPiston;
	}

	public String getStringLocation()
	{
		return fromLocation(this.block.getLocation());
	}

	public String getMessage()
	{
		return this.message;
	}

	public static ProtectedBlock of(YamlConfiguration configuration, String key)
	{
		try
		{
			final String     path     = "blocks." + key;
			final Location   location = fromString(key);
			final String     message  = configuration.contains(path + ".message") ? configuration.getString(path + ".message") : null;
			final List<UUID> owners   = configuration.contains(path + ".owners") ? configuration.getStringList(path + ".owners").stream().map(UUID::fromString).collect(Collectors.toList()) : Collections.emptyList();
			final boolean    piston   = configuration.getBoolean(path + ".piston", false);
			return new ProtectedBlock(location.getBlock(), owners, message, piston);
		}
		catch (WorldNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public void checkAccessAndCancel(Cancellable event, Player player)
	{
		if (!player.hasPermission("privateblocks.admin") && !player.isOp() && !getAccess().contains(player.getUniqueId()))
		{
			event.setCancelled(true);
			player.sendMessage(new Message("blocks.default-message", "{prefix}&cThis block is protected!").color().prefix().get());
		}
	}

	public boolean hasAccess(Player player)
	{
		if (!player.hasPermission("privateblocks.admin") && !player.isOp() && !getAccess().contains(player.getUniqueId()))
		{
			player.sendMessage(new Message("blocks.default-message", "{prefix}&cThis block is protected!").color().prefix().get());
			return false;
		} else return true;
	}

}
