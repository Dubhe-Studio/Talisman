package org.dubhe.talisman;

import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TalismanUtils {

    public static ListNBT toListNBT(Collection<String> collection) {
        ListNBT list = new ListNBT();
        for (String s : collection) {
            list.add(StringNBT.valueOf(s));
        }
        return list;
    }

    public static Collection<String> toCollection(ListNBT list) {
        Collection<String> collection = new ArrayList<>(Collections.emptyList());
        for (int i = 0; i < list.size(); i++) {
            collection.add(list.getString(i));
        }
        return collection;
    }

}
