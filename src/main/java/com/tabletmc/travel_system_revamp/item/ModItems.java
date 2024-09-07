package com.tabletmc.travel_system_revamp.item;

import com.tabletmc.travel_system_revamp.TSRConstants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.minecraft.item.ArmorMaterials.NETHERITE;

/**
 * This class contains the definitions for the mod's items.
 */
public class ModItems  {

	/**
	 * The Netherite Horse Armor item.
	 */
	public static final Item NETHERITE_HORSE_ARMOR = registerModItems(
			new AnimalArmorItem(
					NETHERITE, // Material
					AnimalArmorItem.Type.EQUESTRIAN, // Type
					true, // Can be used on horses
					new Item.Settings().maxCount(1).fireproof() // Settings
			)
	);

	/**
	 * Adds items to the item group.
	 *
	 * @param entries The item group entries.
	 */
	private static void addItemsToItemGroup(FabricItemGroupEntries entries) {
		// Add the Netherite Horse Armor item to the item group
		entries.add(NETHERITE_HORSE_ARMOR);
	}

	/**
	 * Registers a mod item.
	 *
	 * @param item The item to register.
	 * @return The registered item.
	 */
	private static Item registerModItems(Item item) {
		// Register the item with the game's registry
		return Registry.register(Registries.ITEM, TSRConstants.Id("netherite_horse_armor"), item);
	}

	/**
	 * Registers the mod's items.
	 */
	public static void registerModItems() {
		// Modify the TOOLS item group to include our mod's items
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
				.register(ModItems::addItemsToItemGroup);
	}
}