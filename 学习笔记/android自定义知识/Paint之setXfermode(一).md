作者博文：http://blog.csdn.net/harvic880925/article/details/51264653

本文只是读此博客的笔记
# Paint之setXfermode(一)
## GPU硬件加速
### 概述
GPU英文全称Graphic Processing Unit,中文翻译为“图形处理器”。于CPU不同，GPU是专门为处理图像任务而产生的芯片。
CPU绘制和GPU绘制时，在流程上市有区别的：

CPU主导绘图：
1. 让View层次结构失效。
2. 绘制View层次结构。

GPU主导绘图：
1. 让View层次结构失效。
2. 记录、更新显示列表。
3. 绘制显示列表。

#### 优点
硬件加速提高了Android系统显示何刷新的速度。
#### 缺点
1. 兼容性问题:由于是将绘制函数转换成OpenGL命令来绘制，定然会存在OpenGL并不能完全支持原始绘制函数的问题，所以这就会造成在打开GPU加速时，效果会失效的问题。 
2. 内存消耗问题:由于需要OpenGL的指令，所以需要把系统中的OpenGL相关的包加载到内存中来，所以单纯OpenGL API调用就会占用8MB，而实际上会占用更多内存； 
3. 电量消耗问题:多使用了一个部件，当然会更耗电……

## 禁用GPU硬件加速方法
很多方法硬件加速并不支持，但是你又想用，在API14以后默认是打开的，所以你必须禁掉硬件加速。

* 在AndroidManifest.xml中为application标签添加为整个应用程序开启/关闭硬件加速。
<pre><code>application android:hardwareAccelerated="true" ...
</code></pre>
* 在Activity标签下使用hardwareAccelerated属性开启或关闭硬件加速
<pre><code>android:hardwareAccelerated="false"
</code></pre> 
* 在Window层级使用如下代码开启硬件加速（Window层级不支持关闭硬件加速）
<pre><code>getWindow().setFlags(WindowManger.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManger.LayoutParams.FLAG_HARDWARE_ACCELERATED)
</code></pre>
* View级别如下关闭硬件加速（view层级不支持开启硬件加速）
<pre><code>setLayerType(View.LAYER_TYPE_SOFTWARE,null)
</code></pre>
* 使用android：layerType="software"
<pre><code>LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="fill_parent"  
    android:layout_height="fill_parent"  
    android:orientation="vertical"  
    android:paddingLeft="2dp"  
    android:layerType="software"  
    android:paddingRight="2dp" 
</code></pre>

## setXfermode(Xfermode xfermode)之AvoidXfermode
作用：将目标图像对应选区的图像更新到原图上。

使用Xfermode时，为了保险起见，需要做两件事：
1. 禁用硬件加速
`setLayerType(View.LAYER_TYPE_SOFTWARE, null);  `
2. 使用离屏绘制
<pre><code>//新建图层  
int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);  
  
//TODO 核心绘制代码  
  
//还原图层  
canvas.restoreToCount(layerID);  
</code></pre>

### AvoidXfermode
* public  AvoidXfermode(int opColor,int tolerance,Mode mode)
* 
opColor:16进制的AARRGGBB的颜色值。

tolerance:表示容差

mode：Mode.TARGET和Mode.AVOID

容差的意思就是指在opColor的颜色值上下波动的范围容差越大，覆盖的颜色区域越大，容差为0 时，表示与opColor的颜色一样，容差为255表示所有颜色都被选中。
### canvas脏区域更新原理
如果没有设置Xfermode，那么直接将绘制的图形覆盖Canvas对应位置原有的像素；如果设置了Xfermode，那么会按照Xfermode具体的规则来更新Canvas中对应位置的像素颜色。 
所以对于AvoidXfermode而言，这个规则就是先把把目标区域（选区）中的颜色值先清空，然后再把目标颜色给替换上；
## setXfermode(Xfermode xfermode)之PixelXorXfermode
AvoidXfermode一样也在API 16过时了,它是一个简单的异或运算(op ^ src ^ dst)，返回的alpha值始终等于255，所以对操作颜色混合不是特别的有效； 
## setXfermode(Xfermode xfermode)之PorterDuffXfermode
* public PorterDuffXfermode(PorterDuff.Mode mode)
mode枚举值有18个
Mode.CLEAR  
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

每个模式对应的算法
![](http://img.blog.csdn.net/20160428215743886?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

Google给我们的图，显示两个圆形一园一方通过一定的计算参数不同的组合效果，其中圆形是目标图像，方形是源图像。
![](http://img.blog.csdn.net/20160428215851481?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
Google得出这种效果是把两张bitmap大小一样的bitmap从（0，0）点开始画。

如果完全是以一个圆形和正方形的坐标做对应算法，除了两个位图重合的部分，目标图像的其余部分是不会作任何脏数据的清除。
![](http://img.blog.csdn.net/20160428221401299?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
