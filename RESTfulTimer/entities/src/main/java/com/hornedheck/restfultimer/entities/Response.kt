package com.hornedheck.restfultimer.entities

data class Response<T>(
    val data: T?,
    val isSuccessful: Boolean = data != null
){

    companion object{
        val Success = Response(Unit , true)
        val Fail = Response<Any>(null , false)
    }

}