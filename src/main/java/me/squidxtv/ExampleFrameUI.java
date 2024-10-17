package me.squidxtv;

import me.squidxtv.command.ScreenCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleFrameUI extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("screen").setExecutor(new ScreenCommand(this));
    }

}
