package com.cjj.imagestudy

data class Ball(
    var x: Int, //x坐标
    var y: Int, //y坐标
    var color: Int, //像素点颜色
    var vx: Int = 0,//x速度
    var vy: Int = 0,//y速度
    var ax: Int = 0,//x加速度
    var ay: Int = 10,//y加速度
    var t: Long = 0, //手指抬起时间
)