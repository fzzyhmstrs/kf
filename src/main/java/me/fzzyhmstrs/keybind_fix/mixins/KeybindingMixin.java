package me.fzzyhmstrs.keybind_fix.mixins;

import me.fzzyhmstrs.keybind_fix.keybind.KeybindFixer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KeyBinding.class)
public abstract class KeybindingMixin {

    @Final
    @Shadow private static Map<String, KeyBinding> KEYS_BY_ID;
    @Shadow private static Map<InputUtil.Key, KeyBinding> KEY_TO_BINDINGS;
    @Shadow private InputUtil.Key boundKey;

    @Inject(method = "onKeyPressed", at = @At(value = "TAIL"))
    private static void onKeyPressedFixed(InputUtil.Key key, CallbackInfo ci, @Local Keybinding original){
        KeybindFixer.INSTANCE.onKeyPressed(key, original, KEY_TO_BINDINGS.get(key));
    }

    @Inject(method = "setKeyPressed", at = @At(value = "TAIL"))
    private static void setKeyPressedFixed(InputUtil.Key key, boolean pressed, CallbackInfo ci, @Local Keybinding original){
        KeybindFixer.INSTANCE.setKeyPressed(key, pressed, original, KEY_TO_BINDINGS.get(key));
    }

    @Inject(method = "updateKeysByCode", at = @At(value = "TAIL"))
    private static void updateByCodeToMultiMap(CallbackInfo ci) {
        KeybindFixer.INSTANCE.clearMap();
        for (KeyBinding keyBinding : KEYS_BY_ID.values()) {
            KeybindFixer.INSTANCE.putKey(((BoundKeyAccessor) keyBinding).getBoundKey(),keyBinding);
        }
    }

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/util/InputUtil$Type;ILjava/lang/String;)V", at = @At(value = "TAIL"))
    private void putToMultiMap(String translationKey, InputUtil.Type type, int code, String category, CallbackInfo ci){
        KeybindFixer.INSTANCE.putKey(boundKey, (KeyBinding) (Object) this);
    }

}
