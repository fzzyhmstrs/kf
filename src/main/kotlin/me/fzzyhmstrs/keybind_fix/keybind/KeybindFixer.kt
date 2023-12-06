package me.fzzyhmstrs.keybind_fix.keybind

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import me.fzzyhmstrs.keybind_fix.mixins.TimesPressedAccessor
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil

object KeybindFixer {

    private val keyFixMap: Multimap<InputUtil.Key,KeyBinding> = ArrayListMultimap.create()

    fun putKey(key: InputUtil.Key, keyBinding: KeyBinding){
        keyFixMap.put(key, keyBinding)
    }

    fun clearMap(){
        keyFixMap.clear()
    }

    fun onKeyPressed(key: InputUtil.Key, finalBinding: KeyBinding, baseBinding: KeyBinding){
        if (finalBinding !== baseBinding) return
        for (key in keyFixMap[key]){
            if (key == null || key === baseBinding) continue
            (it as TimesPressedAccessor).timesPressed++
        }
    }

    fun setKeyPressed(key: InputUtil.Key, pressed: Boolean, finalBinding: KeyBinding, baseBinding: KeyBinding){
        if (finalBinding !== baseBinding) return
        for (key in keyFixMap[key]){
            if (key == null || key === baseBinding) continue
            it.isPressed = pressed
        }
    }
}
