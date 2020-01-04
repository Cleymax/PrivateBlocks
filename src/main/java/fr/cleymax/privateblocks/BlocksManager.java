package fr.cleymax.privateblocks;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import static fr.cleymax.privateblocks.utils.LocationParser.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * File <b>BlocksManager</b> located on fr.cleymax.privateblocks is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */

public final class BlocksManager {

	private final PrivateBlocks     plugin;
	private final File              dataFile;
	private final YamlConfiguration configuration;

	public BlocksManager(PrivateBlocks plugin)
	{
		this.plugin = plugin;
		this.dataFile = new File(plugin.getDataFolder(), "data.yml");

		final long start = System.currentTimeMillis();

		if (!this.dataFile.getParentFile().exists())
			if (!this.dataFile.mkdir())
				plugin.getLogger().log(Level.CONFIG, "Unable to create the folder: {0}.", this.dataFile.getAbsolutePath());

		if (!this.dataFile.exists())
		{
			try
			{
				if (!this.dataFile.createNewFile())
				{
					plugin.getLogger().log(Level.CONFIG, "Unable to create the data file: {0}.", this.dataFile.getAbsolutePath());
				}
			}
			catch (IOException e)
			{
				plugin.getLogger().log(Level.CONFIG, "Unable to create the data file !", e);
			}
		}
		this.configuration = YamlConfiguration.loadConfiguration(this.dataFile);

		plugin.getLogger().log(Level.INFO, "Configuration loaded in {0}ms.", (System.currentTimeMillis() - start));
	}

	public boolean isProtect(Block block)
	{
		return this.configuration.contains("blocks") && this.configuration.contains("blocks." + fromLocation(block.getLocation()));
	}

	public void addProtect(ProtectedBlock protectedBlock)
	{
		this.configuration.set("blocks." + protectedBlock.getStringLocation() + ".owners", protectedBlock.getAccess().stream().map(UUID::toString).collect(Collectors.toList()));
		if(protectedBlock.cancelPiston())
			this.configuration.set("blocks." + protectedBlock.getStringLocation() + ".piston", true);
		if(protectedBlock.getMessage() == null)
			this.configuration.set("blocks." + protectedBlock.getStringLocation() + ".message", protectedBlock.getMessage());
		save();
	}

	public void save()
	{
		save(false);
	}

	public void save(boolean log)
	{
		try
		{
			this.configuration.save(this.dataFile);
			if (log)
				this.plugin.getLogger().info("Data file has been saved successfully. ");
		}
		catch (IOException e)
		{
			this.plugin.getLogger().log(Level.CONFIG, "Unable to save the data file !", e);
		}
	}

	public ProtectedBlock getProtectedBlock(Block block)
	{
		return ProtectedBlock.of(this.configuration, fromLocation(block.getLocation()));
	}
}
