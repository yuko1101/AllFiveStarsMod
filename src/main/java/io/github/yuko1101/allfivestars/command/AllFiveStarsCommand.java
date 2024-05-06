package io.github.yuko1101.allfivestars.command;

import emu.lunarcore.command.Command;
import emu.lunarcore.command.CommandArgs;
import emu.lunarcore.command.CommandHandler;
import io.github.yuko1101.allfivestars.config.ConfigManager;

@Command(
        label = "allfivestars",
        permission = "player.allfivestars",
        desc = "/allfivestars <subcommand>. Main command for AllFiveStars Mod"
)
public class AllFiveStarsCommand implements CommandHandler {
    @Override
    public void execute(CommandArgs commandArgs) {
        String args = commandArgs.getRaw();
        if (args.isEmpty()) {
            commandArgs.sendMessage("You need to specify a subcommand. Use /allfivestars help for more information.");
            return;
        }
        String subcommand = args.split(" ")[0];
        switch (subcommand.toLowerCase()) {
            case "help":
                commandArgs.sendMessage("/allfivestars <help|toggle|pickupchance> ...");
                break;
            case "toggle":
                ConfigManager.setIsEnabled(!ConfigManager.isEnabled());
                commandArgs.sendMessage("Toggled AllFiveStars Mod. Status: " + (ConfigManager.isEnabled() ? "Enabled" : "Disabled"));
                break;
            case "pickupchance":
                int pickupChance = -1;
                if (args.split(" ").length > 1) {
                    try {
                        pickupChance = Integer.parseInt(args.split(" ")[1]);
                    } catch (NumberFormatException e) {
                        commandArgs.sendMessage("Invalid number.");
                        return;
                    }
                }
                ConfigManager.setPickupChance(pickupChance);
                commandArgs.sendMessage("Set option \"pickupChance\" to " + ConfigManager.getPickupChance() + ". (default is -1)");
                break;
            default:
                commandArgs.sendMessage("Unknown subcommand. Use /allfivestars help for more information.");
        }
    }
}
