package com.jml.rappichallenge.di

import androidx.lifecycle.ViewModel
import dagger.MapKey

import kotlin.reflect.KClass
import kotlin.annotation.MustBeDocumented

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)