package com.jml.rappichallenge.repository.memory

import com.jml.rappichallenge.models.entities.VideoResponse
import javax.inject.Inject
import javax.inject.Singleton

// Wrapper created for easy injection with Dagger
@Singleton
class VideoResponseMemoryCache @Inject constructor(): MemoryCacheImpl<VideoResponse>()