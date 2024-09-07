package com.tabletmc.travel_system_revamp;

import com.tabletmc.travel_system_revamp.config.ModConfig;
import com.tabletmc.travel_system_revamp.item.ModItems;
import com.tabletmc.travel_system_revamp.net.ServerNetworking;


public class MainInitializer implements net.fabricmc.api.ModInitializer {

	@Override
	public void onInitialize()
	{
		ModItems.registerModItems();
		ModConfig.registerConfig();
		ServerNetworking.init();
		TSRConstants.LOGGER.info("Travel System Revamp Initialized.");


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


