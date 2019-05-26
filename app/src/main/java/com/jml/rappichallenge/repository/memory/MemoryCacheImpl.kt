package com.jml.rappichallenge.repository.memory

open class MemoryCacheImpl<T> : MemoryCache<T> {

    private var map : MutableMap<String, MemoryCacheWrapper<T>> = HashMap()

    @Synchronized override fun save(data: T, key: String, expiry: ExpiryPolicy) {
        map[key] = MemoryCacheWrapper(data, expiry)
    }

    @Synchronized override fun load(key: String): T? {
        val wrapper = map[key] ?: return null

        if ( wrapper.expirationPolicy.isExpired() ) {
            map.remove(key)
            return null
        }

        return wrapper.data
    }

    @Synchronized override fun loadAll(): List<T> {
        clearExpired()
        return map.values.map { wrapper -> wrapper.data}
    }

    private fun clearExpired() {
        map.entries.removeAll { entry -> entry.value.expirationPolicy.isExpired() }
    }

}