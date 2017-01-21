作者博文：http://blog.csdn.net/harvic880925/article/details/39080931

本文只是读此博客的笔记
# canvas变换与操作
## 平移（translate）
void translate(float dx, float dy)

平移是平移画布（canvas），其实就是平移坐标系。

屏幕与Canvas不是一个概念

## 旋转
* void rotate(float degrees)
* void rotate (float degrees, float px, float py)

第一个构造degrees是旋转的角度，正数是顺时针旋转，负数是逆时针旋转，旋转中心点是原点

第二个就是旋转点是坐标（px,py）

旋转是旋转画布

## 缩放
* public void scale (float sx, float sy)
* public final void scale (float sx, float sy, float px, float py)

sx:水平方向的伸缩比例，小数为缩放，整数为放大。

sy:垂直方向的伸缩比例，小数为缩放，整数为放大。

整体的显示密度也会改变。

## 扭曲
* void skew (float sx, float sy)

sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值。

sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值。

## 裁剪画布（clip系列函数）
<pre><code>boolean	clipPath(Path path)
boolean	clipPath(Path path, Region.Op op)
boolean	clipRect(Rect rect, Region.Op op)
boolean	clipRect(RectF rect, Region.Op op)
boolean	clipRect(int left, int top, int right, int bottom)
boolean	clipRect(float left, float top, float right, float bottom)
boolean	clipRect(RectF rect)
boolean	clipRect(float left, float top, float right, float bottom, Region.Op op)
boolean	clipRect(Rect rect)
boolean	clipRegion(Region region)
boolean	clipRegion(Region region, Region.Op op)
</code></pre>
## 画布的保存与恢复（save()、restore（））
* int save ()
* void restore()

save:每次调用save()函数，都会把当前画布的状态进行保存，然后放入特定的栈中。

restore:每当调用Restore()函数，就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布，并在这个画布上做画。