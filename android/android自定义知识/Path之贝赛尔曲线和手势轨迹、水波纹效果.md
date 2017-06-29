作者博文：http://blog.csdn.net/harvic880925/article/details/50995587

本文只是读此博客的笔记
# Path之贝赛尔曲线和手势轨迹、水波纹效果
<pre><code>//二阶贝赛尔  
public void quadTo(float x1, float y1, float x2, float y2)  
public void rQuadTo(float dx1, float dy1, float dx2, float dy2)  
//三阶贝赛尔  
public void cubicTo(float x1, float y1, float x2, float y2,float x3, float y3)  
public void rCubicTo(float x1, float y1, float x2, float y2,float x3, float y3)  </code></pre>

## quadTo使用原理
public void quadTo(float x1, float y1, float x2, float y2)

（x1,y1）就是控制点坐标，（x2,y2）是终点坐标。

默认起点为（0，0），可以用Path.moveTo(x,y)指定。连续使用quadTo()函数上一个点的终点就是下一个的起点。

## postInvalidate与invalidate的区别
invalidate必须在UI线程执行，postInvalidate可以在任意线程，所以效率肯定invalidate快。在确定是主线程的情况下，最好用invalidate。


## rQuadTo原理
public void rQuadTo(float dx1, float dy1, float dx2, float dy2)

dx1:控制点X坐标，表示相对于上一个终点X坐标的位移坐标，可为负值，正值表示相加，负值表示相减。

dy1:控制点Y坐标，相对上一个终点Y坐标的位移坐标。同样可为负值，正值表示相加，负值表示相减；

dx2:终点X坐标，同样是一个相对坐标，相对上一个终点X坐标的位移值，可为负值，正值表示相加，负值表示相减；

dy2:终点Y坐标，同样是一个相对，相对上一个终点Y坐标的位移值。可为负值，正值表示相加，负值表示相减；


