package com.example.redisapp.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Bloom Filter Utility Class
 */
@Component
public class BloomFilterUtil {

    // Instance of the Bloom filter
    private final BloomFilter<String> bloomFilter;

    /**
     * Initializes the Bloom filter.
     * Configured to support up to 1 million keys with a 1% false-positive rate.
     */
    public BloomFilterUtil() {
        this.bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), 1000000, 0.01);
    }

    /**
     * Adds a key to the Bloom filter.
     *
     * @param key the key to be added
     */
    public void add(String key) {
        bloomFilter.put(key);
    }

    /**
     * Checks if a key might exist in the Bloom filter.
     *
     * @param key the key to be checked
     * @return true if the key might exist; false if the key is definitely not present
     */
    public boolean mightContain(String key) {
        return bloomFilter.mightContain(key);
    }
}
