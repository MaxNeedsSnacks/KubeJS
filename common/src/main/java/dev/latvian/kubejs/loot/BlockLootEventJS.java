package dev.latvian.kubejs.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.kubejs.block.BlockStatePredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author LatvianModder
 */
public class BlockLootEventJS extends LootEventJS<BlockLootBuilder>
{
	private static final ResourceLocation AIR_ID = new ResourceLocation("minecraft:air");

	public BlockLootEventJS(Map<ResourceLocation, JsonElement> c)
	{
		super(c);
	}

	@Override
	public BlockLootBuilder newLootBuilder()
	{
		return new BlockLootBuilder();
	}

	@Override
	public void addJson(ResourceLocation id, Object json)
	{
		super.addJson(new ResourceLocation(id.getNamespace(), "blocks/" + id.getPath()), json);
	}

	public void addBlock(Object blocks, Consumer<BlockLootBuilder> b)
	{
		BlockLootBuilder builder = new BlockLootBuilder();
		b.accept(builder);
		JsonObject json = builder.toJson(this);

		for (Block block : BlockStatePredicate.of(blocks).getBlocks())
		{
			ResourceLocation blockId = Registry.BLOCK.getKey(block);

			if (!blockId.equals(AIR_ID))
			{
				addJson(blockId, json);
			}
		}
	}

	public void addSimpleBlock(Object blocks)
	{
		addSimpleBlock(blocks, "");
	}

	public void addSimpleBlock(Object blocks, String item)
	{
		for (Block block : BlockStatePredicate.of(blocks).getBlocks())
		{
			ResourceLocation id = item.isEmpty() ? Registry.ITEM.getKey(block.asItem()) : new ResourceLocation(item);
			ResourceLocation blockId = Registry.BLOCK.getKey(block);

			if (!id.equals(AIR_ID) && !blockId.equals(AIR_ID))
			{
				build(blockId, loot -> {
					loot.pool(pool -> {
						pool.addItem(id);
						pool.survivesExplosion();
					});
				});
			}
		}
	}
}
