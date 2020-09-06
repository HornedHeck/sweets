package com.hornedheck.yankeesphisics.data

interface ConversionProvider {

    fun getGroups(): List<ConversionGroup>

    fun getConversions(group : ConversionGroup) : List<Conversion>

}