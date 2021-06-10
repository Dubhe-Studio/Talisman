package org.dubhe.talisman.talisman;

import java.util.HashMap;
import java.util.Map;

public class Talismans {
    private static final Map<String, AbstractTalisman> TALISMANS = new HashMap<>();

    public static final AbstractTalisman EXPLODE = register(new ExplodeTalisman("explode"));
    public static final AbstractTalisman TRANSFER = register(new TransferTalisman("transfer"));
    public static final AbstractTalisman FIREBALL = register(new FireballTalisman("fireball"));
    public static final AbstractTalisman IMMOBILITY = register(new ImmobilityTalisman("immobility"));
    public static final AbstractTalisman TREATMENT = register(new TreatmentTalisman("treatment"));
    public static final AbstractTalisman PUPPET = register(new PuppetTalisman("puppet"));
    public static final AbstractTalisman SEPARATION = register(new SeparationTalisman("separation"));
    public static final AbstractTalisman THUNDER = register(new ThunderTalisman("thunder"));
    public static final AbstractTalisman MUTE = register(new MuteTalisman("mute"));
    public static final AbstractTalisman CARRY = register(new CarryTalisman("carry"));
    public static final AbstractTalisman WATER_BALL = register(new WaterBallTalisman("water_ball"));
    public static final AbstractTalisman DOOM = register(new DoomTalisman("doom"));
    public static final AbstractTalisman HUGE_EXPLOSION = register(new HugeExplosionTalisman("huge_explosion"));
    public static final AbstractTalisman CHANGE_CLOTHING = register(new ChangeClothingTalisman("change_clothing"));
//    public static final AbstractTalisman /*NAME*/ = register(/*instance*/);


    public static void init() {}

    private static <T extends AbstractTalisman> T register(T talisman) {
        TALISMANS.put(talisman.getName(), talisman);
        return talisman;
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractTalisman> T get(String name) {
        return (T) TALISMANS.get(name);
    }
}
