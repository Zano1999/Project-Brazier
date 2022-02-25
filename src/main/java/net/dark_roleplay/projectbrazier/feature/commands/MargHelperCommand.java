package net.dark_roleplay.projectbrazier.feature.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MargHelperCommand {
	public static final LiteralArgumentBuilder<CommandSourceStack> MARG_GENERATE =
			Commands.literal("marg_generate")
					.then(Commands.argument("target", BlockPosArgument.blockPos())
							.executes((context) -> generate(context))
					);

	private static int generate(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
		BlockPos starterPos = BlockPosArgument.getLoadedBlockPos(context, "target");
		BlockPos.MutableBlockPos pos = starterPos.mutable();
		Direction[] dirs = {null, Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};


		Level lvl = context.getSource().getLevel();

		String modid = getSignText(lvl, pos);
		pos.move(0, 0, 2);

		List<String> materials = new ArrayList<>();

		BlockState checkedState = lvl.getBlockState(pos);
		while(checkedState.getBlock() != Blocks.AIR){
			materials.add(getSignText(lvl, pos));
			pos.move(0, 0, 2);
			checkedState = lvl.getBlockState(pos);
		}

		Map<String, Block[]> materialBlocks = new HashMap<>();
		Map<String, Set<ResourceLocation>> materialTextures = new HashMap<>();
		pos.set(starterPos).move(2, 0, -1);

		BlockState initState = lvl.getBlockState(pos);
		int limitTypes = 50;
		while(initState.getBlock() != Blocks.BEDROCK && --limitTypes > 0){
			String material = getSignText(lvl, pos);
			pos.move(0, 0, 3);
			Block[] blocks = new Block[materials.size()];
			Set<ResourceLocation> textures = new HashSet<>();
			for(int i = 0; i < blocks.length - 1; i++){
				BlockState currentState = lvl.getBlockState(pos);
				blocks[i] = currentState.getBlock();
				if(blocks[i] == Blocks.AIR)
					blocks[i] = null;
				pos.move(0, 0, 2);

				BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(currentState);
				if(model != Minecraft.getInstance().getModelManager().getMissingModel()){
					for(Direction dir : dirs){
						List<BakedQuad> quads = model.getQuads(currentState, dir, new Random(), EmptyModelData.INSTANCE);
						for(BakedQuad quad : quads)
							textures.add(quad.getSprite().getName());
					}
				}
			}
			pos.setZ(starterPos.getZ() - 1).move(2, 0, 0);
			initState = lvl.getBlockState(pos);

			materialBlocks.put(material, blocks);
			materialTextures.put(material, textures);
		}

		//WRITE
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for(String material : materialBlocks.keySet()){
			try {
				Path filePath = Path.of("./marg/" + modid + "/" + material + ".json");
				if(Files.notExists(filePath.getParent()))
					Files.createDirectories(filePath.getParent());
				if(Files.notExists(filePath))
					Files.createFile(filePath);
				FileWriter writer = new FileWriter(filePath.toFile());

				JsonObject obj = new JsonObject();
				obj.addProperty("materialType", "wood");
				obj.addProperty("requiredMod", modid);
				obj.addProperty("name", material);

				Block[] b = materialBlocks.get(material);

				JsonObject itemsJson = new JsonObject();
				JsonObject blocksJson = new JsonObject();
				for(int i = 0; i < b.length; i++){
					if(b[i] == null) continue;
					Block b2 = b[i];
					blocksJson.addProperty(materials.get(i), b2.getRegistryName().toString());
					if(b2.asItem() != Items.AIR)
						itemsJson.addProperty(materials.get(i), b2.asItem().getRegistryName().toString());
				}
				obj.add("blocks", blocksJson);
				obj.add("items", itemsJson);

				JsonObject texturesJson = new JsonObject();
				Set<ResourceLocation> textures = materialTextures.get(material);
				int i = 0;
				for(ResourceLocation loc : textures) {
					texturesJson.addProperty(i + "", loc.toString());
					i++;
				}
				obj.add("textures", texturesJson);

				gson.toJson(obj, writer);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("test");

		if(true) return 0;
//		String materialName = getSignText(lvl, posStart);
//
//		if(materialName == null) return -1;
//
//		BlockPos.MutableBlockPos pos = posStart.mutable();
//		pos.move(0, 1, 1);
//
//		Map<String,String> blocks = new HashMap<>();
//
//		BlockState state;
//		while((state = lvl.getBlockState(pos)).getBlock() != Blocks.AIR){
//
//		}

		return 1;
	}

	private static String getSignText(Level level, BlockPos pos) {
		if (!(level.getBlockEntity(pos) instanceof SignBlockEntity be)) return null;
		String text = "";
		for(int i = 0; i < 4; i++)
			text += be.getMessage(i, false).getString();
		return text.isEmpty() ? null : text;
	}
}
