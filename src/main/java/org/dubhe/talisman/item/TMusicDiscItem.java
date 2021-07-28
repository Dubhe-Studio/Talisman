package org.dubhe.talisman.item;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class TMusicDiscItem extends MusicDiscItem {

    public TMusicDiscItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder) {
        super(comparatorValue, soundSupplier, builder);
    }

    @Override
    public String getTranslationKey() {
        return "item.talisman.music_disc";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getDescription() {
        return new TranslationTextComponent("item.talisman.music_disc.desc", new TranslationTextComponent(this.getDefaultTranslationKey()));
    }
}
