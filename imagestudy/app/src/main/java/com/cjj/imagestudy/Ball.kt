package com.cjj.imagestudy

data class Ball(
    var x: Int,
    var y: Int,
    var color: Int,
    var vx: Int = 0,
    var vy: Int = 0,
    var ax: Int = 0,
    var ay: Int = 10,
    var t: Long = 0,
)