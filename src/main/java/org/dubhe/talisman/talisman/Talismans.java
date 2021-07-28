package org.dubhe.talisman.talisman;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Talismans {
    private static final Map<String, AbstractTalisman> TALISMANS = new HashMap<>();

    public static final AbstractTalisman EXPLODE = register(new ExplodeTalisman());
    public static final AbstractTalisman TRANSFER = register(new TransferTalisman());
    public static final AbstractTalisman FIREBALL = register(new FireballTalisman());
//    public static final AbstractTalisman IMMOBILITY = register(new ImmobilityTalisman());
    public static final AbstractTalisman TREATMENT = register(new TreatmentTalisman());
//    public static final AbstractTalisman PUPPET = register(new PuppetTalisman());
//    public static final AbstractTalisman SEPARATION = register(new SeparationTalisman());
    public static final AbstractTalisman THUNDER = register(new ThunderTalisman());
//    public static final AbstractTalisman MUTE = register(new MuteTalisman());
    public static final AbstractTalisman CARRY = register(new CarryTalisman());
//    public static final AbstractTalisman WATER_BALL = register(new WaterBallTalisman());
    public static final AbstractTalisman DOOM = register(new DoomTalisman());
    public static final AbstractTalisman HUGE_EXPLOSION = register(new HugeExplosionTalisman());
    public static final AbstractTalisman CHANGE_CLOTHING = register(new ChangeClothingTalisman());
//    public static final AbstractTalisman /*NAME*/ = register(/*instance*/);


    public static void init() {}

    public static <T extends AbstractTalisman> T register(T talisman) {
        TALISMANS.put(talisman.getName(), talisman);
        return talisman;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends AbstractTalisman> T get(String name) {
        return (T) TALISMANS.get(name);
    }
}
