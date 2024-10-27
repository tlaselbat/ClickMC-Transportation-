package com.tabletmc.transport_plus.keybinds;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.List;

public class RegisterKeybinds {

    public static final KeyBinding SUMMON_HORSE = new KeyBinding(
            "keybind.transport_plus.keybinding.summonMount",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            "text.transport_plus.keybinding.category"
    );

    public static final List<KeyBinding> ALL = List.of(
             SUMMON_HORSE
    );
    }

