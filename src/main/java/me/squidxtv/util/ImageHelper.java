package me.squidxtv.util;

import me.squidxtv.frameui.core.MapItem;
import me.squidxtv.frameui.core.action.scroll.ScrollDirection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public final class ImageHelper {

    // darkest possible black on minecraft maps
    // (0, 0, 0) represents transparency
    private static final Color BLACK = new Color(13, 13, 13);

    private ImageHelper() {
        throw new UnsupportedOperationException();
    }

    // Helper to transform image up and down
    public static void scroll(BufferedImage image, ScrollDirection direction, int pixels) {
        int height = image.getHeight();
        int width = image.getWidth();

        if (direction == ScrollDirection.DOWN) {
            int[] rgb = image.getRGB(0, height - pixels, width, pixels, null, 0, width * pixels);
            Graphics graphics = image.getGraphics();
            graphics.copyArea(0, 0, width, height - pixels, 0, pixels);
            graphics.dispose();
            image.setRGB(0, 0, width, pixels, rgb, 0, width * pixels);
        } else {
            int[] rgb = image.getRGB(0, 0, width, pixels, null, 0, width * pixels);
            Graphics graphics = image.getGraphics();
            graphics.copyArea(0, pixels, width, height - pixels, 0, -pixels);
            graphics.dispose();
            image.setRGB(0, height - pixels, width, pixels, rgb, 0, width * pixels);
        }
    }

    public static BufferedImage background(int width, int height) {
        BufferedImage image = new BufferedImage(width * MapItem.WIDTH, height * MapItem.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(BLACK);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
        return image;
    }

}
