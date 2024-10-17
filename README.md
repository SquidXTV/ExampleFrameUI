# FrameUI Example Plugin

This example plugin demonstrates how to use the [FrameUI](https://github.com/SquidXTV/FrameUI)
library to create interactive screens in Minecraft. It provides a simple command (`/screen`) to
spawn an example screen in the world, allowing you to interact with it by clicking and scrolling.

### Usage
Use the following command to spawn a screen at your current location:
```
/screen spawn <width> <height> <direction>
```
- `<width>`: The width of the screen in blocks
- `<height>`: The height of the screen in blocks
- `<direction>`: The direction the screen is facing (`NORTH`, `SOUTH`, `EAST`, `WEST`)

### Functionality
In this example, the screen starts with a solid black background. 
Players can interact with the screen in two ways:
- **Clicking:** When a player clicks on the screen, the clicked pixel will change from black to white, visually marking the interaction.
- **Scrolling:** Players can scroll up or down on the screen, shifting the entire image vertically in the direction of the scroll.

### [Showcase Video](https://youtu.be/2skIM_jczg8)
[![Showcase](https://img.youtube.com/vi/2skIM_jczg8/maxresdefault.jpg)](https://youtu.be/2skIM_jczg8)

### Code
The following method is responsible for spawning and defining the screen with the desired functionality:
```java
private static Screen createExample(Plugin plugin, Player player, int width, int height, Direction direction) {
    // location where the screen will spawn
    ScreenLocation location = new ScreenLocation(player.getLocation(), direction);

    // create custom BufferedImage
    // this is going to use BufferedImageRenderer
    BufferedImage background = ImageHelper.background(width, height);
    Screen screen = new Screen(plugin, width, height, location, background);

    // set behavior on a click
    // this is going to change the pixel color from "black" to white
    screen.setOnClick((_, clickX, clickY) -> {
        background.setRGB(clickX, clickY, Color.WHITE.getRGB());
        player.sendMessage("Clicked at " + clickX + ", " + clickY + ".");
    });

    // set behavior on a scroll
    // this is going to transform the image based on the scroll direction
    screen.setOnScroll((_, scrollDirection, scrollX, scrollY) -> {
        ImageHelper.scroll(background, scrollDirection, 3);
        player.sendMessage("Scrolled at " + scrollX + ", " + scrollY + ".");
    });

    // Add player as a viewer
    screen.addViewer(player);

    return screen;
}
```

## Conclusion
This example provides a simple demonstration on how to use FrameUI to create screens within Minecraft.
