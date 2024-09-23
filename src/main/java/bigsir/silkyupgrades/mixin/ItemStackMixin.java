package bigsir.silkyupgrades.mixin;

import bigsir.silkyupgrades.interfaces.IUpgradable;
import bigsir.silkyupgrades.upgrades.ToolUpgrade;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.lang.I18n;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import bigsir.silkyupgrades.SilkyUpgrades;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin implements IUpgradable {
	@Shadow
	private @NotNull CompoundTag tag;
	@Unique
	private int upgradeDamage;
	@Unique
	private ToolUpgrade toolUpgrade;

	@Override
	public void silky_upgrades$setUpgrade(ToolUpgrade upgrade) {
		this.toolUpgrade = upgrade;
	}

	@Override
	public ToolUpgrade silky_upgrades$getUpgrade() {
		return this.toolUpgrade;
	}

	@Override
	public int silky_upgrades$getUpgradeDamage() {
		return this.upgradeDamage;
	}

	@Override
	public void silky_upgrades$setUpgradeDamage(int upgradeDamage) {
		this.upgradeDamage = upgradeDamage;
	}

	@Inject(method = "damageItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;isItemStackDamageable()Z", shift = At.Shift.AFTER), cancellable = true)
	public void damageUpgrade(int i, Entity entity, CallbackInfo ci){
		if(this.toolUpgrade != null){
			this.upgradeDamage++;
			if(this.upgradeDamage > this.toolUpgrade.getDurability()){
				this.tag.getValue().remove("SilkyUpgrades");
				this.toolUpgrade = null;
				this.upgradeDamage = 0;
			}
			ci.cancel();
		}
	}

	@Inject(method = "isItemDamaged", at = @At("HEAD"), cancellable = true)
	public void damaged(CallbackInfoReturnable<Boolean> cir){
		if(this.toolUpgrade != null) cir.setReturnValue(true);
	}

	@Inject(method = "writeToNBT", at = @At("HEAD"))
	public void write(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir){
		if(this.toolUpgrade != null){
			CompoundTag modData = new CompoundTag();
			modData.putString("Upgrade", this.toolUpgrade.getKey());
			modData.putInt("UpgradeDamage", this.upgradeDamage);
			this.tag.putCompound("SilkyUpgrades", modData);
		}
	}

	@Inject(method = "readFromNBT", at = @At("TAIL"))
	public void read(CompoundTag nbt, CallbackInfo ci){
		CompoundTag modData = this.tag.getCompound("SilkyUpgrades");
		this.toolUpgrade = ToolUpgrade.parseKey(modData.getString("Upgrade"));
		this.upgradeDamage = modData.getInteger("UpgradeDamage");
	}

	@Redirect(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Item;getTranslatedName(Lnet/minecraft/core/item/ItemStack;)Ljava/lang/String;"))
	public String displayName(Item instance, ItemStack itemstack){
		return ToolUpgrade.getTranslatedPrefixOf(this.toolUpgrade) + instance.getTranslatedName(itemstack);
	}


	//Used for moving items
	@Inject(method = "<init>(Lnet/minecraft/core/item/ItemStack;)V", at = @At("TAIL"))
	public void init(ItemStack itemStack, CallbackInfo ci){
		CompoundTag modData = this.tag.getCompound("SilkyUpgrades");
		if(modData.containsKey("Upgrade")){
			this.toolUpgrade = ToolUpgrade.parseKey(modData.getString("Upgrade"));
		}else{
			this.toolUpgrade = ToolUpgrade.getItemUpgrade(itemStack);
		}
        this.upgradeDamage = modData.getInteger("UpgradeDamage") == 0 ? SilkyUpgrades.getItemUpgradeDamage(itemStack) : modData.getInteger("UpgradeDamage");
	}

	//Used for ItemEntity
	@Inject(method = "<init>(IIILcom/mojang/nbt/CompoundTag;)V", at = @At("TAIL"))
	public void init(int itemID, int stackSize, int metadata, CompoundTag tag, CallbackInfo ci){
		CompoundTag modData = tag.getCompound("SilkyUpgrades");
		this.toolUpgrade = ToolUpgrade.parseKey(modData.getString("Upgrade"));
		this.upgradeDamage = modData.getInteger("UpgradeDamage");
	}
}
