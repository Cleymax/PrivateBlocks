package fr.cleymax.privateblocks;

import fr.cleymax.privateblocks.listener.BlockListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * File <b>PrivateBlocks</b> located on fr.cleymax.privateblocks is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */
public final class PrivateBlocks extends JavaPlugin {

	private static PrivateBlocks instance;
	private BlocksManager blocksManager;

	@Override
	public void onEnable()
	{
		instance = this;

		saveDefaultConfig();

		this.blocksManager = new BlocksManager(this);

		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
	}

	@Override
	public void onDisable()
	{
		this.blocksManager.save(true);
	}

	public BlocksManager getBlocksManager()
	{
		return this.blocksManager;
	}

	public static PrivateBlocks getInstance()
	{
		return instance;
	}
}
