package com.tabletmc.travel_system_revamp;
import com.tabletmc.travel_system_revamp.keybinds.RegisterKeybinds;
import com.tabletmc.travel_system_revamp.net.ClientNetworking;
import com.tabletmc.travel_system_revamp.keybinds.KeybindTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientModInitializer implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetworking.init();
        KeybindTickEvents.init();
        RegisterKeybinds.ALL.forEach(KeyBindingHelper::registerKeyBinding);

    }
}
