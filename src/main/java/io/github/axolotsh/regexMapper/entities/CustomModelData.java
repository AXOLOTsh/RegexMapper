package io.github.axolotsh.regexMapper.entities;

import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

public class CustomModelData {
    private List<Float> floats = new ArrayList<>();
    public List<Float> getFloats() { return floats; }
    public void setFloats(List<Float> value) { floats = value; }
    public CustomModelData floats(List<Float> value) { setFloats(value); return this; }
    public CustomModelData addFloat(Float value) { getFloats().add(value); return this; }

    private List<Boolean> flags = new ArrayList<>();
    public List<Boolean> getFlags() { return flags; }
    public void setFlags(List<Boolean> value) { flags = value; }
    public CustomModelData flags(List<Boolean> value) { setFlags(value); return this; }
    public CustomModelData addFlag(Boolean value) { getFlags().add(value); return this; }

    private List<String> strings = new ArrayList<>();
    public List<String> getStrings() { return strings; }
    public void setStrings(List<String> value) { strings = value; }
    public CustomModelData strings(List<String> value) { setStrings(value); return this; }
    public CustomModelData addString(String value) { getStrings().add(value); return this; }

    private List<Color> colors = new ArrayList<>();
    public List<Color> getColors() { return colors; }
    public void setColors(List<Color> value) { colors = value; }
    public CustomModelData colors(List<Color> value) { setColors(value); return this; }
    public CustomModelData addColor(Color value) { getColors().add(value); return this; }
}
