# android启动页白屏解决方案
## 白屏产生的原因
在我们的splash页（splash的activity）中，setContView并不是第一步，系统会在执行这步之前先绘制窗体，所以布局资源还没加载，于是就使用默认背景色，你的默认Theme是white，你就是产生白屏现象，默认Theme是black，你就是产生黑屏现象。
## 解决的方案
解决的方案很简单，就是自定义一个Theme,给你的SplashActivity设置。
这个自定义Theme的android:windowBackground可以设置为你想要的图片，这样就能解决黑白屏问题了。如果没有其他广告页，可以把setContent省略掉，少消耗资源。