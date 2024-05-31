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
                args.sendMessage("/allfivestars <help|toggle|pickupchance|basechance> ...");
                break;
            case "toggle":
                ConfigManager.setIsEnabled(!ConfigManager.isEnabled());
                args.sendMessage("Toggled AllFiveStars Mod. Status: " + (ConfigManager.isEnabled() ? "Enabled" : "Disabled"));
                break;
            case "pickupchance": {
                if (args.size() < 2) {
                    args.sendMessage("Invalid arguments. /allfivestars pickupchance [stars] (chance)");
                    return;
                }
                int pickupChance = -1;
                int stars;
                try {
                    stars = Integer.parseInt(args.get(1));
                    if (args.size() > 2) {
                        pickupChance = Integer.parseInt(args.get(2));
                    }
                } catch (NumberFormatException e) {
                    args.sendMessage("Invalid arguments. /allfivestars pickupchance [stars] (chance)");
                    return;
                }

                if (stars < 4 || 5 < stars) {
                    args.sendMessage("Invalid stars. Stars must be 4 or 5.");
                    return;
                }

                ConfigManager.getChanceConfig(stars).setPickupChance(pickupChance);
                if (pickupChance == -1) {
                    args.sendMessage("Reset option \"pickupChance\" for " + stars + " stars to the default");
                } else {
                    args.sendMessage("Set option \"pickupChance\" for " + stars + " stars to " + pickupChance + ". (default is -1)");
                }
                break;
            }
            case "basechance": {
                if (args.size() < 2) {
                    args.sendMessage("Invalid arguments. /allfivestars basechance [stars] (baseChance)");
                    return;
                }
                int baseChance = -1;
                int stars;
                try {
                    stars = Integer.parseInt(args.get(1));
                    if (args.size() > 2) {
                        baseChance = Integer.parseInt(args.get(2));
                    }
                } catch (NumberFormatException e) {
                    args.sendMessage("Invalid arguments. /allfivestars baseChance [stars] (baseChance)");
                    return;
                }

                if (stars < 4 || 5 < stars) {
                    args.sendMessage("Invalid stars. Stars must be 4 or 5.");
                    return;
                }

                ConfigManager.getChanceConfig(stars).setBaseChance(baseChance);
                if (baseChance == -1) {
                    args.sendMessage("Reset option \"baseChance\" for " + stars + " stars to the default");
                } else {
                    args.sendMessage("Set option \"baseChance\" for " + stars + " stars to " + baseChance + ", or " + (((double) baseChance) / 100) + "%. (default is -1)");
                }
                break;
            }
            default:
                args.sendMessage("Unknown subcommand. Use /allfivestars help for more information.");
        }
    }
}
