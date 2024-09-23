package bigsir.silkyupgrades.commands;

import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class UpgradeCommand extends Command {
	public UpgradeCommand() {
		super("upgrade", "");
	}

	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] args) {
		if(args.length == 0) return false;
		ItemStack stack = commandSender.getPlayer().getHeldItem();
		if(args[0].equalsIgnoreCase("set")){
			if(args.length == 1) return false;
			ToolUpgrade toolUpgrade = ToolUpgrade.parseKey("toolupgrade." + args[1]);
			if(toolUpgrade != null){
				toolUpgrade.applyThisUpgrade(stack, 0);
			}
			return true;
		}else if(args[0].equalsIgnoreCase("remove")){
			ToolUpgrade.removeItemUpgrade(stack);
			return true;
		}
		return false;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return true;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {

	}
}
