package io.github.minecraftalus.ironbundles.data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import com.google.common.hash.Hashing;
import io.github.minecraftalus.ironbundles.IronBundleListUtils;
import io.github.minecraftalus.ironbundles.IronBundles;
import io.github.minecraftalus.ironbundles.item.IronBundlesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class IronBundlesTextureProvider implements DataProvider {
    private final FabricDataOutput dataOutput;
    private final PackOutput.PathProvider pathProvider;
    private final Map<String, BufferedImage> imageCache = new HashMap<>();

    public IronBundlesTextureProvider(FabricDataOutput packOutput) {
        this.dataOutput = packOutput;
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures");
    }

    private void createTextureProvider(Consumer<LayeredTextureHolder> textureConsumer) {
        generateBundleTierTextures(IronBundlesItems.IRON_BUNDLES, "iron_overlay", textureConsumer);
    }

    private void generateBundleTierTextures(List<Item> items, String overlayName, Consumer<LayeredTextureHolder> textureConsumer) {
        layeredItemTexture(
            ModelLocationUtils.getModelLocation(items.getFirst()),
            vanillaItemLocation("bundle"),
            modItemLocation(overlayName),
            textureConsumer);

        for (int i = 1; i < items.size() - 1; i++) {
            String color = IronBundleListUtils.dyeNameMappings.get(i - 1).getValue();
            layeredItemTexture(
                ModelLocationUtils.getModelLocation(items.get(i)),
                vanillaItemLocation(color + "_bundle"),
                modItemLocation(overlayName),
                textureConsumer);

        }

    }

    private void layeredItemTexture(
        ResourceLocation location, ResourceLocation layer0, ResourceLocation layer1, Consumer<LayeredTextureHolder> textureConsumer) {
        textureConsumer.accept(
            new LayeredTextureHolder(
                location,
                layer0,
                layer1));
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cachedOutput) {
        Set<LayeredTextureHolder> textures = new HashSet<>();
        createTextureProvider(textures::add);

        for (LayeredTextureHolder texture : textures) {
            Path filePath = pathProvider.file(texture.file, "png");
            try {
                BufferedImage layer0 = loadItemTexture(texture.layer0);
                BufferedImage layer1 = loadItemTexture(texture.layer1);

                saveImage(overlayImages(layer0, layer1), filePath, cachedOutput);
            } catch (IOException e) {
                throw new RuntimeException("Failed to composite image " + texture.file, e);
            }
        }

        return CompletableFuture.completedFuture(textures);
    }

    public static BufferedImage overlayImages(BufferedImage baseImage, BufferedImage overlayImage) {
        BufferedImage combined = new BufferedImage(
            baseImage.getWidth(),
            baseImage.getHeight(),
            BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combined.createGraphics();
        g2d.drawImage(baseImage, 0, 0, null);
        g2d.drawImage(overlayImage, 0, 0, null);
        g2d.dispose();

        return combined;
    }

    private static void saveImage(BufferedImage image, Path path, CachedOutput cachedOutput) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        cachedOutput.writeIfNeeded(path, bytes, Hashing.sha1().hashBytes(bytes));
    }

    private static ResourceLocation vanillaItemLocation(String path) {
        return ResourceLocation.withDefaultNamespace("item/" + path);
    }

    private static ResourceLocation modItemLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(IronBundles.MOD_ID, "item/" + path);
    }

    @Override
    public @NotNull String getName() {
        return "IronBundlesTextureProvider";
    }

    private record LayeredTextureHolder(ResourceLocation file, ResourceLocation layer0, ResourceLocation layer1) {}

    // Hacky way to load images, probably shouldnt do this but whatever
    private BufferedImage loadItemTexture(ResourceLocation location) throws IOException {
        String assetPath = "assets/" + location.getNamespace() + "/textures/" + location.getPath() + ".png";

        BufferedImage existingImage = imageCache.get(assetPath);
        if (existingImage != null)
            return existingImage;

        Path foundPath = null;

        if (location.getNamespace().equals(IronBundles.MOD_ID)) {
            foundPath = dataOutput.getModContainer().findPath(assetPath).orElse(null);
        }

        if (foundPath == null) {
            var url = Thread.currentThread().getContextClassLoader().getResource(assetPath);
            if (url != null) {
                try {
                    foundPath = Path.of(url.toURI());
                } catch (Exception ignored) {
                    try (var inputStream = url.openStream()) {
                        BufferedImage img = ImageIO.read(inputStream);
                        imageCache.put(assetPath, img);
                        return img;
                    }
                }
            }
        }

        if (foundPath != null && Files.exists(foundPath)) {
            try (var inputStream = Files.newInputStream(foundPath)) {
                BufferedImage img = ImageIO.read(inputStream);
                imageCache.put(assetPath, img);
                return img;
            }
        }

        throw new IOException("Could not find texture file for " + location + " at expected path: " + assetPath);
    }
}
