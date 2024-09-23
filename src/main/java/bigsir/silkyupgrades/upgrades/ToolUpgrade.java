package bigsir.silkyupgrades.upgrades;

import bigsir.silkyupgrades.interfaces.IItemTool;
import bigsir.silkyupgrades.interfaces.IUpgradable;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.lang.I18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolUpgrade {
	private static final Map<String, ToolUpgrade> keyToUpgradeMap = new HashMap<>();
	public static final ToolUpgrade gilded = new ToolUpgrade().withKey("gilded").excludingMaterial(ToolMaterial.gold).withUpgradeItem(Item.ingotGold).withDurability(ToolMaterial.gold.getDurability());
	public static final ToolUpgrade instance = new ToolUpgrade();
	private Item upgradeItem;
	private String key;
	private int durability;
	private final Map<Item, Integer> craftingMaterialCountMap = new HashMap<>();


	public ToolUpgrade(){
	}

	public Item getUpgradeItem() {
		return this.upgradeItem;
	}

	public String getKey() {
		return this.key;
	}

	public int getDurability() {
		return durability;
	}

	public int getCraftingMaterialCount(Item item){
		return craftingMaterialCountMap.get(item);
	}

	public String getTranslatedPrefix(){
		return I18n.getInstance().translateKey(this.key + ".prefix") + " ";
	}

	public static String getTranslatedPrefixOf(ToolUpgrade toolUpgrade){
		return toolUpgrade == null ? "" : toolUpgrade.getTranslatedPrefix();
	}

	public static ToolUpgrade parseKey(String key){
		return keyToUpgradeMap.get(key);
	}

	public boolean canUpgradeItem(Item item){
		return craftingMaterialCountMap.containsKey(item);
	}

	public static boolean isItemUpgraded(ItemStack itemStack){
		return ((IUpgradable)(Object)itemStack).silky_upgrades$getUpgrade() != null;
	}

	public void setItemUpgrade(ItemStack itemStack){
		((IUpgradable)(Object)itemStack).silky_upgrades$setUpgrade(this);
	}

	public static ToolUpgrade getItemUpgrade(ItemStack itemStack){
		return ((IUpgradable)(Object)itemStack).silky_upgrades$getUpgrade();
	}

	public boolean hasThisUpgrade(ItemStack itemStack){
		return ((IUpgradable)(Object)itemStack).silky_upgrades$getUpgrade() == this;
	}

	public static void removeItemUpgrade(ItemStack itemStack){
		((IUpgradable)(Object)itemStack).silky_upgrades$setUpgrade(null);
		((IUpgradable)(Object)itemStack).silky_upgrades$setUpgradeDamage(0);
		itemStack.getData().getValue().remove("SilkyUpgrades");
	}

	public void applyThisUpgrade(ItemStack itemStack, int damage){
		((IUpgradable)(Object)itemStack).silky_upgrades$setUpgrade(this);
		((IUpgradable)(Object)itemStack).silky_upgrades$setUpgradeDamage(0);
		CompoundTag modData = new CompoundTag();
		modData.putString("Upgrade", this.key);
		modData.putInt("UpgradeDamage", damage);
		itemStack.getData().putCompound("SilkyUpgrades", modData);
	}

	public static boolean isInGenericMap(Item item){
		return ToolUpgrade.instance.craftingMaterialCountMap.containsKey(item);
	}

	//Builders
	private ToolUpgrade excludingMaterial(ToolMaterial material){
		List<Item> toRemove = new ArrayList<>();
		for (Map.Entry<Item, Integer> entry : craftingMaterialCountMap.entrySet()){
			if(((IItemTool)entry.getKey()).silky_upgrades$getMaterial().equals(material)) toRemove.add(entry.getKey());
		}
		for (Item item : toRemove){
			craftingMaterialCountMap.remove(item);
		}
		return this;
	}

	private ToolUpgrade withUpgradeItem(Item item){
		this.upgradeItem = item;
		return this;
	}

	private ToolUpgrade withKey(String key){
		keyToUpgradeMap.put("toolupgrade." + key, this);
		this.key = "toolupgrade." + key;
		return this;
	}

	private ToolUpgrade withDurability(int durability){
		this.durability = durability;
		return this;
	}

	{
		this.craftingMaterialCountMap.put(Item.toolAxeWood, 3);
		this.craftingMaterialCountMap.put(Item.toolAxeStone, 3);
		this.craftingMaterialCountMap.put(Item.toolAxeIron, 3);
		this.craftingMaterialCountMap.put(Item.toolAxeGold, 3);
		this.craftingMaterialCountMap.put(Item.toolAxeDiamond, 3);
		this.craftingMaterialCountMap.put(Item.toolAxeSteel, 3);

		this.craftingMaterialCountMap.put(Item.toolPickaxeWood, 3);
		this.craftingMaterialCountMap.put(Item.toolPickaxeStone, 3);
		this.craftingMaterialCountMap.put(Item.toolPickaxeIron, 3);
		this.craftingMaterialCountMap.put(Item.toolPickaxeGold, 3);
		this.craftingMaterialCountMap.put(Item.toolPickaxeDiamond, 3);
		this.craftingMaterialCountMap.put(Item.toolPickaxeSteel, 3);

		this.craftingMaterialCountMap.put(Item.toolShovelWood, 1);
		this.craftingMaterialCountMap.put(Item.toolShovelStone, 1);
		this.craftingMaterialCountMap.put(Item.toolShovelIron, 1);
		this.craftingMaterialCountMap.put(Item.toolShovelGold, 1);
		this.craftingMaterialCountMap.put(Item.toolShovelDiamond, 1);
		this.craftingMaterialCountMap.put(Item.toolShovelSteel, 1);

		this.craftingMaterialCountMap.put(Item.toolHoeWood, 2);
		this.craftingMaterialCountMap.put(Item.toolHoeStone, 2);
		this.craftingMaterialCountMap.put(Item.toolHoeIron, 2);
		this.craftingMaterialCountMap.put(Item.toolHoeGold, 2);
		this.craftingMaterialCountMap.put(Item.toolHoeDiamond, 2);
		this.craftingMaterialCountMap.put(Item.toolHoeSteel, 2);

		this.craftingMaterialCountMap.put(Item.toolSwordWood, 2);
		this.craftingMaterialCountMap.put(Item.toolSwordStone, 2);
		this.craftingMaterialCountMap.put(Item.toolSwordIron, 2);
		this.craftingMaterialCountMap.put(Item.toolSwordGold, 2);
		this.craftingMaterialCountMap.put(Item.toolSwordDiamond, 2);
		this.craftingMaterialCountMap.put(Item.toolSwordSteel, 2);
	}
}
