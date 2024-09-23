package bigsir.silkyupgrades.interfaces;

import bigsir.silkyupgrades.upgrades.ToolUpgrade;

public interface IUpgradable {
	default boolean silky_upgrades$getSilky(){
		return false;
	}

	default void silky_upgrades$setSilky(boolean isSilky){
	}

	default int silky_upgrades$getUpgradeDamage(){
		return 0;
	}

	default void silky_upgrades$setUpgradeDamage(int damage){
	}

	default void silky_upgrades$setUpgrade(ToolUpgrade upgrade){
	}

	default ToolUpgrade silky_upgrades$getUpgrade(){
		return null;
	}
}
