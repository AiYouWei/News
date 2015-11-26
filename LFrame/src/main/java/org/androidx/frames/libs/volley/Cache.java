package org.androidx.frames.libs.volley;

import java.util.Collections;
import java.util.Map;

/**
 * 数据缓存接口，对请求的数据进行缓存
 *
 * @author ShuLQ
 * @time 2015-05-29
 */
public interface Cache {
    /**
     * Retrieves an entry from the cache.
     *
     * @param key Cache key
     * @return An {@link Entry} or null in the event of a cache miss
     */
    Entry get(String key);

    /**
     * Adds or replaces an entry to the cache.
     *
     * @param key   Cache key
     * @param entry Data to store and metadata for cache coherency, TTL, etc.
     */
    void put(String key, Entry entry);

    /**
     * Performs any potentially long-running actions needed to initialize the
     * cache; will be called from a worker thread.
     */
    void initialize();

    /**
     * Invalidates an entry in the cache.
     *
     * @param key        Cache key
     * @param fullExpire True to fully expire the entry, false to soft expire
     */
    void invalidate(String key, boolean fullExpire);

    /**
     * Removes an entry from the cache.
     *
     * @param key Cache key
     */
    void remove(String key);

    /**
     * Empties the cache.
     */
    void clear();

    /**
     * Data and metadata for an entry returned by the cache.
     */
    class Entry {
        /**
         * The data returned from cache.
         */
        public byte[] data;

        /**
         * ETag for cache coherency.
         */
        public String etag;

        /**
         * Date of this response as reported by the server.
         */
        public long serverDate;

        /**
         * The last modified date for the requested object.
         */
        public long lastModified;

        /**
         * TTL for this record.
         */
        public long ttl;

        /**
         * Soft TTL for this record.
         */
        public long softTtl;

        /**
         * Immutable response headers as received from server; must be non-null.
         */
        public Map<String, String> responseHeaders = Collections.emptyMap();

        /**
         * True if the entry is expired.
         */
        public boolean isExpired() {
            return this.ttl < System.currentTimeMillis();
        }

        /**
         * True if a refresh is needed from the original data source.
         */
        public boolean refreshNeeded() {
            return this.softTtl < System.currentTimeMillis();
        }
    }

}
