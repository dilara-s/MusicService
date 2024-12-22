package ru.kpfu.itis.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {

    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "music");
            configMap.put("api_key", "466373683895828");
            configMap.put("api_secret", "JQrZz-wdj926XhS_4IVMlMQ0Ww0");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }
}