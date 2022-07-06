package me.fzzyhmstrs.keybind_fix.mixins;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface TimesPressedAccessor {

    @Accessor(value = "timesPressed")
    int getTimesPressed();
    @Accessor(value = "timesPressed")
    void setTimesPressed(int value);
}
