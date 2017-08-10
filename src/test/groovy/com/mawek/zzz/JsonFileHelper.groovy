package com.mawek.zzz

import org.apache.commons.io.FileUtils
import org.springframework.util.ResourceUtils

/**
 * Helper class for manipulating with JSON.
 */
final class JsonFileHelper {

    private JsonFileHelper() {
    }

    /**
     * Read json file and return its content as String.
     *
     * Example:
     * getJsonAsString("com/mawek/something.json") will try to find and read something.json file in com.mawek.something package
     *
     * @param fullPathOnClassPath - path to jscon file on classpath
     */
    static String getJsonAsString(final String fullPathOnClassPath) {

        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + fullPathOnClassPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

