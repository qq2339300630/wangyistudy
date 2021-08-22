##### canvas动画

这个效果图，要是是三个动画组合

第二个动画用到了OvershootInterpolator，可以设置超出设置值得范围，在方向播放动画，刚好就有这个效果

第三个动画，绘制一个空心圆，但是开始的stroke宽度得完全覆盖住手机屏幕，在将半径慢慢变大，达到水波纹效果

提一下，canvas绘制水波纹圆时，stroke宽度，一半在圆内，一半圆外

所以真实圆的半径等于

```kotlin
val strokeWidth = hypot(
                (width / 2).toDouble(),
                (height / 2).toDouble()
            ).toFloat() - radius
真实半径:strokeWidth/2 + radius
```

这个真实半径就刚好覆盖整个手机界面

效果图

![image](https://github.com/qq2339300630/wangyistudy/blob/master/gif/canvas%E5%8A%A8%E7%94%BB.gif)
