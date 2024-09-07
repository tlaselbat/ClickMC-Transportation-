package com.tabletmc.transport_plus;

import com.tabletmc.transport_plus.config.ModConfig;
import com.tabletmc.transport_plus.item.ModItems;
import com.tabletmc.transport_plus.net.ServerNetworking;


public class ModInitializer implements net.fabricmc.api.ModInitializer {

	@Override
	public void onInitialize()
	{
		ModItems.registerModItems();
		ModConfig.registerConfig();
		ServerNetworking.init();
		ModConstants.LOGGER.info("Travel System Revamp Initialized.");


		}

	//TODO: Iron door key
	//TODO: shulker charm
	//TODO: summon mount item
	//TODO:
	//TODO:
	//TODO:
	//TODO:
	//TODO:

}


