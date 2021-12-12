package net.dark_roleplay.projectbrazier.experimental_features.fixed_data;

import com.google.common.base.Joiner;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FixedDataPack {

	private static Map<String, FixedDataPack> FIXED_DATA_PACKS = new HashMap<>();

	@Nullable
	public static FixedDataPack getPackForMod(String modid) {
		return FIXED_DATA_PACKS.computeIfAbsent(modid, key -> {
			Optional<? extends ModContainer> mod = ModList.get().getModContainerById(key);

			if (!mod.isPresent())
				return null;

			IModFileInfo file = mod.get().getModInfo().getOwningFile();
			return new FixedDataPack(file.getFile());
		});
	}

	private final IModFile file;

	public FixedDataPack(IModFile file) {
		this.file = file;
	}

	public Collection<ResourceLocation> getResources(String resourceNamespace, String pathIn, int maxDepth, Predicate<String> filter) {
		try {
			Path root = resolve("fixed_data", resourceNamespace).toAbsolutePath();
			Path inputPath = root.getFileSystem().getPath(pathIn);

			return Files.walk(root)
					.map(root::relativize)
					.filter(path -> path.getNameCount() <= maxDepth && !path.toString().endsWith(".mcmeta") && path.startsWith(inputPath))
					.filter(path -> filter.test(path.getFileName().toString()))
					// It is VERY IMPORTANT that we do not rely on Path.toString as this is inconsistent between operating systems
					// Join the path names ourselves to force forward slashes
					.map(path -> new ResourceLocation(resourceNamespace, Joiner.on('/').join(path)))
					.collect(Collectors.toList());
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}

	protected InputStream getResource(String name) throws IOException {
		final Path path = resolve(name);
		if (!Files.exists(path))
			throw new FileNotFoundException("Can't find resource " + name + " at " + this.file.getFileName());
		return Files.newInputStream(path, StandardOpenOption.READ);
	}

	public InputStream getResource(ResourceLocation location) throws IOException {
		return this.getResource("fixed_data/" + location.getNamespace() + "/" + location.getPath());
	}

	protected Path resolve(String... paths) {
		return this.file.findResource(paths);
	}
}
