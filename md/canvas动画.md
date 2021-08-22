##### canvas动画

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

![canvas动画](..\gif\canvas动画.gif)