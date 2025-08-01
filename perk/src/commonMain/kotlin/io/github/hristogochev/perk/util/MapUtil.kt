@file:Suppress("unused")

package io.github.hristogochev.perk.util

import kotlin.collections.iterator

internal fun <K, V, R> Map<K, V>.mapNotNullKeys(transform: (K) -> R?): Map<R, V> {
    return this.mapNotNull { (key, value) ->
        transform(key)?.let { transformedKey ->
            transformedKey to value
        }
    }.toMap()
}

internal fun <K, V, R> Map<K, V>.mapNotNullValues(transform: (V) -> R?): Map<K, R> {
    return this.mapNotNull { (key, value) ->
        transform(value)?.let { transformedValue ->
            key to transformedValue
        }
    }.toMap()
}

internal fun <K : Any, V> Map<K?, V>.filterNotNullKeys(): Map<K, V> {
    val result = mutableMapOf<K, V>()
    for ((key, value) in this) {
        if (key != null) {
            result[key] = value
        }
    }
    return result
}

internal fun <K, V : Any> Map<K, V?>.filterNotNullValues(): Map<K, V> {
    val result = mutableMapOf<K, V>()
    for ((key, value) in this) {
        if (value != null) {
            result[key] = value
        }
    }
    return result
}