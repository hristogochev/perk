@file:Suppress("unused")

package io.github.hristogochev.perk.util

internal inline fun <A, B, C, D> Pair<A, B>.map(transform: (value1: A, value2: B) -> Pair<C, D>): Pair<C, D> {
    val (value1, value2) = this
    return transform(value1, value2)
}

internal inline fun <A, B, C> Pair<A, B>.mapFirst(transform: (value1: A) -> C): Pair<C, B> {
    val (value1, value2) = this
    val first = transform(value1)
    return Pair(first, value2)
}

internal inline fun <A, B, D> Pair<A, B>.mapSecond(transform: (value1: B) -> D): Pair<A, D> {
    val (value1, value2) = this
    val second = transform(value2)
    return Pair(value1, second)
}

internal inline fun <A, B, C, D> Pair<A, B>.mapNullable(transform: (value1: A, value2: B) -> Pair<C, D>?): Pair<C, D>? {
    val (value1, value2) = this
    return transform(value1, value2)
}

internal inline fun <A, B> Pair<A, B>.quick(onSecond: (second: B) -> Unit): A {
    val (value1, value2) = this
    onSecond(value2)
    return value1
}

internal inline fun <A> Pair<A, Boolean>.quickIfTrue(onSecond: () -> Unit): A {
    val (value1, value2) = this
    if (value2) {
        onSecond()
    }
    return value1
}


internal inline fun <A, B> List<Pair<A, B>>.quick(onSecond: (second: B) -> Unit) =
    map { (value1, value2) ->
        onSecond(value2)
        value1
    }

internal fun <A> List<Pair<A, Boolean>>.quickAny(): Pair<List<A>, Boolean> {
    var any = false
    val res = map { (value, boolean) ->
        if (boolean) any = boolean
        value
    }
    return Pair(res, any)
}