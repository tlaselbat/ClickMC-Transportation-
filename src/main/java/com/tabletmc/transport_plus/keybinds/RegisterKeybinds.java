package com.tabletmc.transport_plus.keybinds;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.List;

public class RegisterKeybinds {

//    public static final KeyBinding HORSE_INVENTORY_KEY = new KeyBinding(
//            "keybind.transport_plus.keybinding.horsePlayerInventory",
//            InputUtil.Type.KEYSYM,
//            GLFW.GLFW_KEY_LEFT_ALT,
//            "text.transport_plus.keybinding.category"
//    );
    public static final KeyBinding SUMMON_HORSE = new KeyBinding(
            "keybind.transport_plus.keybinding.summonMount",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            "text.transport_plus.keybinding.category"
    );

    public static final List<KeyBinding> ALL = List.of(
//            HORSE_INVENTORY_KEY,
            SUMMON_HORSE
    );
    }

