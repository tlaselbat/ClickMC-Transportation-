package com.tabletmc.transport_plus;
import com.tabletmc.transport_plus.keybinds.RegisterKeybinds;
import com.tabletmc.transport_plus.net.ClientNetworking;
import com.tabletmc.transport_plus.keybinds.KeybindTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ClientInitializer implements net.fabricmc.api.ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetworking.init();
        KeybindTickEvents.init();
        RegisterKeybinds.ALL.forEach(KeyBindingHelper::registerKeyBinding);

    }
}
