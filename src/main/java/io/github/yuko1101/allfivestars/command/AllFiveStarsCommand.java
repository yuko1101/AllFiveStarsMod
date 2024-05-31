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
    public void execute(CommandArgs args) {
        if (args.size() == 0) {
            args.sendMessage("You need to specify a subcommand. Use /allfivestars help for more information.");
            return;
        }
        switch (args.get(0).toLowerCase()) {
            case "help":
                args.sendMessage("/allfivestars <help|toggle|pickupchance|fivestarschance> ...");
                break;
            case "toggle":
                ConfigManager.setIsEnabled(!ConfigManager.isEnabled());
                args.sendMessage("Toggled AllFiveStars Mod. Status: " + (ConfigManager.isEnabled() ? "Enabled" : "Disabled"));
                break;
            case "pickupchance":
                int pickupChance = -1;
                if (args.size() > 1) {
                    try {
                        pickupChance = Integer.parseInt(args.get(1));
                    } catch (NumberFormatException e) {
                        args.sendMessage("Invalid number. /allfivestars pickupchance <number>");
                        return;
                    }
                }
                ConfigManager.setPickupChance(pickupChance);
                args.sendMessage("Set option \"pickupChance\" to " + ConfigManager.getPickupChance() + ". (default is -1)");
                break;
            case "fivestarschance":
                int fiveStarsChance = -1;
                if (args.size() > 1) {
                    try {
                        fiveStarsChance = Integer.parseInt(args.get(1));
                    } catch (NumberFormatException e) {
                        args.sendMessage("Invalid number. /allfivestars fivestarschance <number>");
                        return;
                    }
                }
                ConfigManager.setFiveStarsChance(fiveStarsChance);
                args.sendMessage("Set option \"fiveStarsChance\" to " + ConfigManager.getFiveStarsChance() + ", or " + (((double) ConfigManager.getFiveStarsChance()) / 100) + "%. (default is -1)");
                break;
            default:
                args.sendMessage("Unknown subcommand. Use /allfivestars help for more information.");
        }
    }
}
