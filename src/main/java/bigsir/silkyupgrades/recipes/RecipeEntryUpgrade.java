package bigsir.silkyupgrades.recipes;

import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingDynamic;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;

public class RecipeEntryUpgrade extends RecipeEntryCraftingDynamic {

	public ToolUpgrade toolUpgrade;

	public RecipeEntryUpgrade(ToolUpgrade upgrade){
		this.toolUpgrade = upgrade;
	}

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting) {
		int toUpgradeCount = 0;
		int upgradeMaterialCount = 0;
		int neededMaterial = 0;
		int size = inventoryCrafting.getSizeInventory();

		for (int i = 0; i < size; i++) {
			ItemStack stack = inventoryCrafting.getStackInSlot(i);
			if(stack != null){
				int id = stack.itemID;
				if (this.toolUpgrade.canUpgradeItem(stack.getItem())) {
					neededMaterial = this.toolUpgrade.getCraftingMaterialCount(stack.getItem());
					toUpgradeCount++;
				}else if (id == this.toolUpgrade.getUpgradeItem().id) {
					upgradeMaterialCount++;
				}else{
					return false;
				}
			}
		}
		return upgradeMaterialCount == neededMaterial && toUpgradeCount == 1;
	}

	@Override
	public boolean matchesQuery(SearchQuery searchQuery) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		int size = inventoryCrafting.getSizeInventory();

		for (int i = 0; i < size; i++) {
			ItemStack gridStack = inventoryCrafting.getStackInSlot(i);
			if(gridStack != null && this.toolUpgrade.canUpgradeItem(gridStack.getItem())) {
				ItemStack craftingResult = new ItemStack(gridStack);
				this.toolUpgrade.applyThisUpgrade(craftingResult, 0);
				return craftingResult;
			}
		}

		return null;
	}

	@Override
	public int getRecipeSize() {
		//todo: not sure what this does
		return 2;
	}

	@Override
	public ItemStack[] onCraftResult(InventoryCrafting inventoryCrafting) {
		ItemStack[] returnStack = new ItemStack[9];
		int size = inventoryCrafting.getSizeInventory();

		for (int i = 0; i < size; i++) {
			ItemStack gridStack = inventoryCrafting.getStackInSlot(i);
			if(gridStack != null){
				gridStack.stackSize--;
				if(gridStack.stackSize <= 0) gridStack = null;
			}
			inventoryCrafting.setInventorySlotContents(i, gridStack);
		}

		return returnStack;
	}
}
