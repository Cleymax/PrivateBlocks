package fr.cleymax.privateblocks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * File <b>Message</b> located on fr.cleymax.privateblocks is a part of PrivateBlocks.
 * <p>
 * Copyright (c) 2020 Cleymax.
 * <p>
 *
 * @author Clément P. (Cleymax), {@literal <contact@cleymax.fr>} Created the 03/01/2020
 */

public class Message {

	private String message;

	public Message(String path, String defaul)
	{
		this.message = PrivateBlocks.getInstance().getConfig().getString(path, defaul);
	}

	public Message prefix()
	{
		this.message = this.message.replaceAll("\\{prefix}", ChatColor.translateAlternateColorCodes('&', PrivateBlocks.getInstance().getConfig().getString("prefix", "&bPrivateBlock &f: &r")));
		return this;
	}

	public Message player(Player player)
	{
		this.message = this.message.replaceAll("\\{player}", player.getName());
		return this;
	}

	public Message number(int number, String replace)
	{
		this.message = this.message.replaceAll("\\{" + number + "}", replace);
		return this;
	}

	public Message color()
	{
		this.message = ChatColor.translateAlternateColorCodes('&', message);
		return this;
	}

	public String get()
	{
		return message;
	}
}
