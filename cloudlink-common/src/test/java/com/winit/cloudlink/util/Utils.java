
package com.winit.cloudlink.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jerry Lee(oldratlee<at>gmail<dot>com)
 */
public class Utils {
    public static Map<String, String> kv2Map(String... kv) {
        Map<String, String> cs = new HashMap<String, String>();

        for (int i = 0; i < kv.length; i += 2) {
            String key = kv[i];
            if (key == null) {
                throw new IllegalArgumentException("Key must not null!");
            }
            if (i + 1 < kv.length) {
                cs.put(key, kv[i + 1]);
            } else {
                cs.put(key, null);
            }
        }

        return cs;
    }

}
