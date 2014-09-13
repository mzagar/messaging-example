package messaging.gateway.message;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Contains info about supported protocol versions.
 *
 * Created by mzagar on 13.9.2014.
 */
public class Versions {
    private static Set<String> supportedVersions = new LinkedHashSet<String>();

    public static String V1_0_0 = "1.0.0";
    public static String V1_0_1 = "1.0.1";
    public static String V2_0_0 = "2.0.0";

    static {
        supportedVersions.add(V1_0_0);
        supportedVersions.add(V1_0_1);
        supportedVersions.add(V2_0_0);
    }

    public static boolean isSupported(String version) {
        return supportedVersions.contains(version);
    }

    public static Set<String> getSupportedVersions() {
        return new LinkedHashSet<String>(supportedVersions);
    }

}
