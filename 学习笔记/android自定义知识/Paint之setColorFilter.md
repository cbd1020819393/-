作者博文：http://blog.csdn.net/harvic880925/article/details/51253944

本文只是读此博客的笔记

#Paint之setColorFilter
##setColorFilter
* public ColorFilter setColorFilter(ColorFilter filter)

###ColorMatrixColorFilter
* ColorMatrixColorFilter(ColorMatrix matrix)
* ColorMatrixColorFilter(float[] array)
###LightingColorFilter
光照颜色过滤器，可以简单的完成色彩过滤何色彩增强功能。

* public LightingColorFilter(int mul,int add)
mul:是乘法multiply的缩写。
add:是加法的意思。
mul和add取值都是0xRRGGBB,分别对应RGB，没有透明度。
###PorterDuffColorFilter
图形混合滤镜。

* public PorterDuffColorFilter(int srcColor,PorterDuff.Mode mode)
srcColor:0xAARRGGBB类型的颜色值。
PorterDuff.Mode mode:表示混合模式，枚举值有18个，表示各种图形混合模式
<pre><code>Mode.CLEAR  
Mode.SRC  
Mode.DST  
Mode.SRC_OVER  
Mode.DST_OVER  
Mode.SRC_IN  
Mode.DST_IN  
Mode.SRC_OUT  
Mode.DST_OUT  
Mode.SRC_ATOP  
Mode.DST_ATOP  
Mode.XOR  
Mode.DARKEN  
Mode.LIGHTEN  
Mode.MULTIPLY  
Mode.SCREEN  
Mode.OVERLAY  
Mode.ADD 
</code></pre>

####六种变色相关
Mode.ADD(饱和度相加)，Mode.DARKEN（变暗），Mode.LIGHTEN（变亮），Mode.MULTIPLY（正片叠底），Mode.OVERLAY（叠加），Mode.SCREEN（滤色） 
![](http://img.blog.csdn.net/20160426230856991?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
####清空模式
Mode.ClEAR和Mode.XOR效果是一致的，就是把图像清空，所以他们得到的结果图像就是一个空图。
####目标图像模式
Mode.DST、Mode.DST_IN、Mode.DST_OUT、Mode.DST_OVER、Mode.DST_ATOP
除了Mode.DST_OUT是完全透明图片。

![](http://img.blog.csdn.net/20160426231152872?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
####源图模式
Mode.SRC,Mode.SRC_IN,Mode.SRC_OUT,Mode.SRC_OVER,Mode.SRC_ATOP
![](http://img.blog.csdn.net/20160426231242857?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
####v4包中SetTint(给图像指定颜色)
* setTint(Drawable drawable,int tint)
可以利用于不同的主题，只需要一个图片，就不必引入多个颜色的切图。就可以在一定程度上缩小包的大小。

![](http://img.blog.csdn.net/20160426231350852?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


###PorterDuffColorFilter总结
1.PorterDuffColorFilter只能实现与一个特定颜色值的合成。
2、通过Mode.ADD(饱和度相加)，Mode.DARKEN（变暗），Mode.LIGHTEN（变亮），Mode.MULTIPLY（正片叠底），Mode.OVERLAY（叠加），Mode.SCREEN（滤色）可以实现与指定颜色的复合。
3、通过Mode.SRC、Mode.SRC_IN、Mode.SRC_ATOP能够实现setTint()的功能，可以改变纯色图标的颜色。