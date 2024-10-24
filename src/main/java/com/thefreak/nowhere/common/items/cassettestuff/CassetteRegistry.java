package com.thefreak.nowhere.common.items.cassettestuff;

import java.util.HashMap;

public class CassetteRegistry {
    public static HashMap<Integer,String> cassetteNames = new HashMap<>();


    public static void initCassettes() {
        cassetteNames.put(0,"Empty");
        cassetteNames.put(1,"T.O.E : starving.help");
    }
}
