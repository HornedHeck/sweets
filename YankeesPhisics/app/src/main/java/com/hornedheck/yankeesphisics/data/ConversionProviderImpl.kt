package com.hornedheck.yankeesphisics.data

import com.hornedheck.yankeesphisics.R

object ConversionProviderImpl : ConversionProvider {

    private val conversions = mapOf(
        ConversionGroup.MASS to listOf(
            Conversion(R.string.type_long_ton, 1016.05f),
            Conversion(R.string.type_short_ton, 907.185f),
            Conversion(R.string.type_tonne, 1000f),
            Conversion(R.string.type_kg, 1f),
            Conversion(R.string.type_gramme, 0.001f),
            Conversion(R.string.type_short_hundredweight, 45.36f),
            Conversion(R.string.type_long_hundredweight, 50.802f),
            Conversion(R.string.type_pound, 0.45359f),
        ),
        ConversionGroup.DISTANCE to listOf(
            Conversion(R.string.type_meter, 1f),
            Conversion(R.string.type_kilometer, 1000f),
            Conversion(R.string.type_centimetre, 0.01f),
            Conversion(R.string.type_nautical_mile, 1853.257f),
            Conversion(R.string.type_mile, 1609.344f),
            Conversion(R.string.type_inch, 0.0254f),
            Conversion(R.string.type_foot, 0.3048f),
            Conversion(R.string.type_yard, 0.9144f),
        ),
        ConversionGroup.VOLUME to listOf(
            Conversion(R.string.type_meter3, 1f),
            Conversion(R.string.type_litre, 0.001f),
            Conversion(R.string.type_barrel, 0.16366f),
            Conversion(R.string.type_fl_oz, 28.56e-6f),
            Conversion(R.string.type_gallon, 0.004405f),
        )
    )

    override fun getGroups() = conversions.keys.toList()

    override fun getConversions(group: ConversionGroup) = conversions.getValue(group)
}