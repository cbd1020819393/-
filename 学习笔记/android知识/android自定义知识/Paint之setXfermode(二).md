作者博文：http://blog.csdn.net/harvic880925/article/details/51284710

本文只是读此博客的笔记

# Paint之setXfermode(二)
## 模式的深入解析
![](http://img.blog.csdn.net/20160429225539210?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

1. 区域一是源图像何目标图像的相交区域。
2. 区域二是源图像与目标图像的不相交区域。


## 颜色叠加相关模式
Mode.ADD（饱和度相加）,Mode.DARKEN（变暗）,Mode.LIGHTEN（变亮）,Mode.MULTIPLY（正片叠底）,Mode.OVERLAY（叠加）,Mode.SCREEN（滤色）。
### Mode.ADD
计算公式：Saturate(S + D)
![](http://img.blog.csdn.net/20160430094555221?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.LIGHTEN
计算公式： [Sa + Da - Sa*Da,Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)] 
![](http://img.blog.csdn.net/20160430094728112?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.DARKEN
计算公式：[Sa + Da - Sa*Da,Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)] 
![](http://img.blog.csdn.net/20160430095129102?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.MULTIPLY
计算公式：[Sa * Da, Sc * Dc] 
![](http://img.blog.csdn.net/20160430095222837?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.OVERLAY
![](http://img.blog.csdn.net/20160430095305510?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.SCREEN
计算公式：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc] 
![](http://img.blog.csdn.net/20160430095407511?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

1. 这几种模式都是PhotoShop中存在的模式，是通过计算改变交合区域的颜色值的。 
2. 除了Mode.MULTIPLY(正片叠底)会在目标图像透明是将结果对应区域为透明，其他图像都不受目标图像透明像素影响，即源图像非交合部分保持原样。

## SRC相关模式
Mode.SRC、Mode.SRC_IN、Mode.SRC_OUT、Mode.SRC_OVER、Mode.SRC_ATOP
### Mode.SRC
计算公式：[Sa, Sc] 
![](http://img.blog.csdn.net/20160430095812548?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.SRC_IN
计算公式为：[Sa * Da, Sc * Da]
![](http://img.blog.csdn.net/20160430095847830?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
### Mode.SRC_OUT
计算公式为：[Sa * (1 - Da), Sc * (1 - Da)] 
![](http://img.blog.csdn.net/20160430100530854?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
这个模式就是刮刮卡效果的实现核心。
### Mode.SRC_OVER
计算公式为：[Sa + (1 - Sa)*Da, Rc = Sc + (1 - Sa)*Dc] 
![](http://img.blog.csdn.net/20160430101056378?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.SRC_ATOP
计算公式为：[Da, Sc * Da + (1 - Sa) * Dc] 
![](http://img.blog.csdn.net/20160430101137669?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

有公式可知：

1、当透明度只有100%和0%时，SRC_ATOP是SRC_IN是通用的 。

2、当透明度不只有100%和0%时，SRC_ATOP相比SRC_IN源图像的饱和度会增加，即会显得更亮！