qq气泡仿写

1.四个阶段

1.单个气泡不动

2.拖动时双气泡，中间使用贝塞尔曲线连接，同时不动小球半径随着贝塞尔曲线长度反比

<img src="..\img\math.png" alt="math" style="zoom:15%;" />

利用sin与cos把四个点计算出来，其中注意sin是y1-y2因为这些都是有正负号的

```Kotlin
val cosL =
    (height / 2 - mConnectY) / hypot(mConnectY - height / 2, mConnectX - width / 2)
val sinL =
    (mConnectX - width / 2) / hypot(mConnectY - height / 2, mConnectX - width / 2)
pointA.x = width / 2f - cosL * smallR
pointA.y = height / 2f - sinL * smallR

pointD.x = width / 2f + cosL * smallR
pointD.y = height / 2f + sinL * smallR
```

由于我在计算sin，cos已经考虑了正负号，所以按照图直接加减半径就行

A点在左上,所以是（width - cosL * smallR， height / 2f - sinL * smallR）其他点亦然.

3.拖动是超过最长距离不动气泡消失

4.松手时判断是否超过最长距离，没超过回弹，超过了播放爆炸效果

（1）爆炸效果很简单，利用一组图片+动画就可以完成

演示视频
![image](gif/qq%E6%B0%94%E6%B3%A1.gif)
