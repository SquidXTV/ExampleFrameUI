package me.squidxtv.command;

import me.squidxtv.frameui.api.ScreenRegistry;
import me.squidxtv.frameui.core.Screen;
import me.squidxtv.frameui.core.ScreenLocation;
import me.squidxtv.frameui.math.Direction;
import me.squidxtv.util.ImageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ScreenCommand implements CommandExecutor {

    private final Plugin plugin;
    private final ScreenRegistry registry;

    private final Collection<BukkitTask> tasks = new ArrayList<>();

    public ScreenCommand(Plugin plugin) {
        this.plugin = plugin;
        this.registry = plugin.getServer().getServicesManager().load(ScreenRegistry.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Command must be used with either spawn or clear");
            return false;
        }

        String subcommand = args[0];
        if (subcommand.equals("spawn")) {
            spawn(sender, args);
        } else if (subcommand.equals("clear")) {
            clear(sender);
        } else {
            sender.sendMessage("Unknown subcommand for screen command: " + subcommand);
            return false;
        }

        return true;
    }

    private void spawn(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            int width = 5;
            int height = 3;
            Direction direction = Direction.NORTH;

            if (args.length == 4) {
                width = Integer.parseInt(args[1]);
                height = Integer.parseInt(args[2]);
                direction = Direction.valueOf(args[3].toUpperCase());
            }

            Screen screen = createExample(plugin, player, width, height, direction);
            screen.open();

            // render every 0.5 seconds
            BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, screen::update, 0, 2);
            tasks.add(task);
        } else {
            sender.sendMessage("Only players can use this subcommand.");
        }
    }

    private void clear(CommandSender sender) {
        if (registry == null) {
            return;
        }

        Iterator<BukkitTask> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            BukkitTask task = iterator.next();
            task.cancel();
            iterator.remove();
        }

        for (Screen screen : registry.getByPlugin(plugin)) {
            screen.close();
        }

        sender.sendMessage("Successfully cleared all active screens.");
    }

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

}
