package com.example.pageviewstudy

class ParallaxViewTag(
    @JvmField var index: Int = 0,
    @JvmField  var xIn: Float = 0f,
    @JvmField  var xOut: Float = 0f,
    @JvmField  var yIn: Float = 0f,
    @JvmField  var yOut: Float = 0f,
    @JvmField var alphaIn: Float = 0f,
    @JvmField var alphaOut: Float = 0f
) {

    override fun toString(): String {
        return ("ParallaxViewTag [index=" + index + ", xIn=" + xIn + ", xOut="
                + xOut + ", yIn=" + yIn + ", yOut=" + yOut + ", alphaIn="
                + alphaIn + ", alphaOut=" + alphaOut + "]")
    }

}
