package com.example.pageviewstudy

class ParallaxViewTag(
    var index: Int,
    var xIn: Float,
    var xOut: Float,
    var yIn: Float,
    var yOut: Float,
    var alphaIn: Float,
    var alphaOut: Float
) {

    override fun toString(): String {
        return ("ParallaxViewTag [index=" + index + ", xIn=" + xIn + ", xOut="
                + xOut + ", yIn=" + yIn + ", yOut=" + yOut + ", alphaIn="
                + alphaIn + ", alphaOut=" + alphaOut + "]")
    }

     class TagBuild() {
         var index: Int = 0
          var xIn: Float = 0f
         var xOut: Float = 0f
         var yIn: Float = 0f
         var yOut: Float = 0f
         var alphaIn: Float = 0f
         var alphaOut: Float = 0f

        fun setIndex(value:Int):TagBuild {
            index = value
            return this
        }

        fun setXIn(value: Float):TagBuild {
            xIn = value
            return this
        }

        fun setXOut(value: Float):TagBuild {
            xOut = value
            return this
        }

        fun setYIn(value: Float):TagBuild {
            yIn = value
            return this
        }

        fun setYOut(value: Float):TagBuild {
            yOut = value
            return this
        }

        fun setAlphaIn(value: Float):TagBuild {
            alphaIn = value
            return this
        }

        fun setAlphaOut(value: Float):TagBuild {
            alphaOut = value
            return this
        }

        fun build():ParallaxViewTag {
            return ParallaxViewTag(index, xIn, xOut, yIn, yOut, alphaIn, alphaOut)
        }

    }

}
