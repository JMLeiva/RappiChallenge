package com.jml.rappichallenge.repository.memory


interface MemoryCache<T> {

    fun save(data : T, key : String, expiry : ExpiryPolicy)
    fun load(key : String) : T?
    fun loadAll() : List<T>
}