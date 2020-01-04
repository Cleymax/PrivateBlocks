package fr.cleymax.privateblocks.listener;

import fr.cleymax.privateblocks.Message;
import fr.cleymax.privateblocks.PrivateBlocks;
import fr.cleymax.privateblocks.ProtectedBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

/**
 * File <b>BlockListener</b> located on fr.cleymax.privateblocks.listener is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */

public final class BlockListener implements Listener {

	private final PrivateBlocks plugin;

	public BlockListener(PrivateBlocks plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.isCancelled())
			return;

		if (this.plugin.getBlocksManager().isProtect(event.getBlock()))
		{
			final ProtectedBlock protectedBlock = this.plugin.getBlocksManager().getProtectedBlock(event.getBlock());
			protectedBlock.checkAccessAndCancel(event, event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.isCancelled() || !event.hasBlock() || event.getClickedBlock().getType() == Material.AIR)
			return;

		if (this.plugin.getBlocksManager().isProtect(event.getClickedBlock()))
		{
			final ProtectedBlock protectedBlock = this.plugin.getBlocksManager().getProtectedBlock(event.getClickedBlock());
			protectedBlock.checkAccessAndCancel(event, event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBlockPistonExtend(BlockPistonExtendEvent event)
	{
		pistonCheck(event, event.getBlocks());
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBlockPistonRetract(BlockPistonRetractEvent event)
	{
		pistonCheck(event, event.getBlocks());
	}

	private void pistonCheck(BlockPistonEvent event, List<Block> blocks)
	{
		if (event.isCancelled() || blocks.isEmpty())
			return;

		blocks.stream().filter(block -> this.plugin.getBlocksManager().isProtect(block)).map(block -> this.plugin.getBlocksManager().getProtectedBlock(block)).filter(ProtectedBlock::cancelPiston).findFirst().ifPresent(protectedBlock -> event.setCancelled(true));
	}
}