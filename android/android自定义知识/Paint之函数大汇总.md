作者博文：http://blog.csdn.net/harvic880925/article/details/51010839

本文只是读此博客的笔记
# Paint之函数大汇总
## 基本用法
1. reset() 重置画笔
2. setColor() 给画笔设置颜色值
3. setARGB(int a,int r,int g,int b) 同样是设置颜色，但是利用ARGB分开设置。
4. setAlpha(int a) 设置画笔透明度
5. setStyle(Paint.Style style) 设置画笔样式
6. setStrokeWidth(float width) 设置画笔宽度
7. setAntiAlias(boolean aa) 设置画笔是否抗锯齿
8. setStrokeCap(Paint.Cap cap) 设置线帽样式
9. setStrokeJoin(Paint.Join join) 设置连接处样式
10. setStrokeMiter(float miter) 设置画笔倾斜度
11. setPathEffect(PathEffect effect) 设置路径样式
### setStrokeCap(Paint.Cap cap)
* Cap.Round(圆形线帽)
* Cap.SQUARE(方形线帽)
* Cap.BUTT(无线帽)

图线很直观的表达了线冒的区别

![](http://img.blog.csdn.net/20160330083020037?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### setStrokeJoin(Paint.Join join)
*Join.MITER（结合处为锐角）
*Join.Round(结合处为圆弧)
*Join.BEVEL(结合处为直线)

![](http://img.blog.csdn.net/20160330083742743?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### setPathEffect(PathEffect effect)
* ComposePathEffect
* CornerPathEffect
* DashPathEffect
* DiscretePathEffect
* PathDashPathEffect
* sumPathEffect
#### cornerPathEffect——圆形拐角效果
* public CornerPathEffect(float radius)

 radius表示园的半径。

![](http://img.blog.csdn.net/20160402201638339?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

有图可知,这种方式点并不是设置的那个顶点，会改变原有坐标，如果需要用原有坐标计算，不可用这种方式。
#### DashPathEffect——虚线效果
* public DashPathEffect(float intervals,float phase)

intervals[]:表示虚线的各个线段的长度，如{20，10}
![](http://img.blog.csdn.net/20160402201828153?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

intervals数组的两个限定：

1. 长度必须大于等于2；因为必须有一个实线段和一个空线段来组成虚线。
2. 个数必须为偶数，如果是基数，最后一个数字将被忽略；这个很好理解，因为一组虚线的组成必然是一个实线和一个空线成对组成的。

phase:开始绘制的偏移量。
#### DiscretePathEffect——离散路径效果
* public DiscretePathEffect(float segmentLength, float deviation)

segmentLength:表示将原来的路径切成多长的线段。值越小，线段越多，值越大，线段越少。

deviation：表示被切成的每个小线段的可偏移距离。值越大，表示可偏移距离越大，显得越凌乱。
#### PathDashPathEffect——印章路径效果
* public PathDashPathEffect(Path shape, float advance, float phase,Style style)

shape:表示印章路径。

advance:表示两个印章间的距离，值越大，间距越大。

phase:偏移。

style:表示遇到转交时，印章的过渡效果。

1. Style.ROTATE表示通过旋转印章来过渡转角；
2. Style.MORPH表示通过变形印章来过渡转角；
3. Style.TRANSLATE表示通过位移来过渡转角；

#### ComposePathEffect与SumPathEffect
* public ComposePathEffect(PathEffect outerpe, PathEffect innerpe)
* public SumPathEffect(PathEffect first, PathEffect second)

两个都是合成特效的功能。

ComposePathEffect合并两个特效是有先后顺序的，结合两个pathEffect的效果。

SumPathEffect是分别对原始路径进行特效，然后把两条线合并。

![](http://img.blog.csdn.net/20160403102134342?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
 
## 字体相关 
1. setTextSize(float textSize) 设置文字大小
2. setFakeBoldText(boolean fakeBoldText) 设置是否为粗体文字
3. setStrikeThruText(boolean strikeThruText) 设置带有删除线效果
4. setUnderlineText(boolean underlineText) 设置下划线
5. setTextAlign(Paint.Align align) 设置开始绘图点位置
6. setTextScaleX(float scaleX) 水平拉伸设置
7. setTextSkewX(float skewX) 设置字体水平倾斜度，普通斜体字是-0.25，可见往右斜 
8. setTypeface(Typeface typeface) 字体样式 
9. setLinearText(boolean linearText) 设置是否打开线性文本标识。基本没用。
10. setSubpixelText(boolean subpixelText)是否打开亚像素设置来绘制文本。