package com.jml.rappichallenge.repository.memory

data class MemoryCacheWrapper<T>(val data : T, val expirationPolicy: ExpiryPolicy)