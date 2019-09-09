package dev.latvian.kubejs;

import dev.latvian.kubejs.net.KubeJSNetHandler;
import dev.latvian.kubejs.net.MessageSendData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;

/**
 * @author LatvianModder
 */
public class KubeJSCommon
{
	public void sendData(EntityPlayer playerEntity, String channel, @Nullable Object data)
	{
		if (!playerEntity.world.isRemote && !channel.isEmpty())
		{
			KubeJSNetHandler.net.sendTo(new MessageSendData(channel, data), (EntityPlayerMP) playerEntity);
		}
	}

	@Nullable
	public EntityPlayer getClientPlayer()
	{
		return null;
	}
}