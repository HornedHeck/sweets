package com.hornedheck.restfultimer.entities

data class Response<out T>(
    val data: T?,
    val isSuccessful: Boolean = data != null
) {

    fun <V> map(block: (T) -> V): Response<V> {
        return Response(data?.let { block(it) }, isSuccessful)
    }

    companion object {
        val Success = Response(Unit, true)
        val Fail = Response(null, false)
    }
}

fun <T> handle(block: () -> T) = try {
    Response(block(), true)
} catch (e: Exception) {
    e.printStackTrace()
    Response(null, false)
}

fun handleUnit(block: () -> Unit) = try {
    block()
    Response.Success
} catch (e: Exception) {
    e.printStackTrace()
    Response.Fail
}