作者博文：http://blog.csdn.net/harvic880925/article/details/50423762

本文只是读此博客的笔记
# drawText的详解
# x,y的真正含义
<pre><code>/** 
* text:要绘制的文字 
* x：绘制原点x坐标 
* y：绘制原点y坐标 
* paint:用来做画的画笔 
*/  
public void drawText(String text, float x, float y, Paint paint)  </code></pre>


<pre><code>/** 
* 其中Align的取值为：Panit.Align.LEFT,Paint.Align.CENTER,Paint.Align.RIGHT 
*/  
Paint::setTextAlign(Align align); </code></pre>

x是指相对位置，当我们设置Align时

1. Panit.Align.LEFT,相当于x点的右边
2. Paint.Align.CENTER，相当于x点的中间，文字的中间。
3. Paint.Align.RIGHT，相当于x点的左边

y是指基线位置，而不是左上角的位置。

三张图片清晰得出

Panit.Align.LEFT时
![](http://img.blog.csdn.net/20151229092016017?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
Panit.Align.CENTER时
![](http://img.blog.csdn.net/20151229092103818?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
Panit.Align.RIGHT时
![](http://img.blog.csdn.net/20151229092145628?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## drawText的四线格与FontMetrics

![](http://img.blog.csdn.net/20151229092331153?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

* FontMetrics::ascent;  基点到ascent的距离，为负值。
* FontMetrics::descent;基点到descent的距离，为正值。  
* FontMetrics::top;基点到top的距离，为负值。  
* FontMetrics::bottom;基点到bottom的距离，为正值。  

* ascent = ascent线的y坐标 - baseline线的y坐标；
* descent = descent线的y坐标 - baseline线的y坐标；
* top = top线的y坐标 - baseline线的y坐标；
* bottom = bottom线的y坐标 - baseline线的y坐标；

其实最后知道以下即可

* ascent线Y坐标 = baseline线的y坐标 + fontMetric.ascent；
* descent线Y坐标 = baseline线的y坐标 + fontMetric.descent；
* top线Y坐标 = baseline线的y坐标 + fontMetric.top；
* bottom线Y坐标 = baseline线的y坐标 + fontMetric.bottom；

### 获取FontMetirics对象
<pre><code>Paint paint = new Paint();  
Paint.FontMetrics fm = paint.getFontMetrics();  
Paint.FontMetricsInt fmInt = paint.getFontMetricsInt(); </code></pre>
## 所绘文字宽度、高度和最小矩形获取
### 高度
字符串所占高度就是bottom线减去top线。
### 宽度
width=paint.measureText(String text);
### 最小矩形
getTextBounds(String text,int start,int end,Rect bounds)

其中的bounds是传入的bounds,最后的值也会放入这个矩形中。
