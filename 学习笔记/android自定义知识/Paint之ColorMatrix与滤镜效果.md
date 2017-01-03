作者博文：http://blog.csdn.net/harvic880925/article/details/51187277

本文只是读此博客的笔记

# Paint之ColorMatrix与滤镜效果

## 矩阵概述
## 定义
![](http://img.blog.csdn.net/20160419085228486)
## 矩阵乘法
![](http://img.blog.csdn.net/20160419085724492)
1. A矩阵的列数必须与B矩阵的行数相同，才能相乘！因为我们需要把A中的一行中的各个数字与B矩阵中的一列中的各个数字分别相乘，所以A的列数与B的行数必须相同才行！
2. 矩阵A乘以矩阵B和矩阵B乘以矩阵A的结果必然是不一样的。


## 色彩矩阵
Bitmap用32位的数值来保存。红蓝绿及透明度各占8位，每个色彩分量的取值范围是0-255(2^8)。透明度为0时表示完全透明，为255时色彩完全可见。

### 色彩矩阵的表示
#### 四阶表示
![](http://img.blog.csdn.net/20160419090010949)
色彩的顺序按对角线，RGBA。
#### 为什么使用五阶矩阵
考虑到给某个颜色增加以下分量。
![](http://img.blog.csdn.net/20160419090113981)

## Android中的色彩矩阵
Android中的色彩矩阵是用ColorMatrics类来表示。
<pre><code>// 生成色彩矩阵    
ColorMatrix colorMatrix = new ColorMatrix(new float[]{    
        1, 0, 0, 0, 0,    
        0, 1, 0, 0, 0,    
        0, 0, 1, 0, 0,    
        0, 0, 0, 0.5, 0,    
});    
mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix)); 
</code></pre>

## 色彩的集中运算方式
### 色彩的平移运算
平移很简单，就是最后一行加上某个值。
### 色彩反转/反相功能
色彩的反转就是每个色彩的补值来作为目标图像的对应颜色值。
<pre><code>ColorMatrix colorMatrix = new ColorMatrix(new float[]{  
        -1,0,0,0,255,  
        0,-1,0,0,255,  
        0,0,-1,0,255,  
        0,0,0,1,0  
});  
</code></pre>
### 色彩的缩放运算
色彩的缩放就是色彩的乘法运算。就是对亮度的调节。
<pre><code>ColorMatrix colorMatrix = new ColorMatrix(new float[]{  
        1.2f, 0, 0, 0, 0,  
        0, 1.2f, 0, 0, 50,  
        0, 0, 1.2f, 0, 0,  
        0, 0, 0, 1.2f, 0,  
});    
</code></pre>
### 色彩的特殊应用（通道输出）
其实就是把R,G,B中的两个设为0,留下的就是当前的颜色。
![](http://img.blog.csdn.net/20160419093618819?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### 色彩的旋转运算
色彩旋转还没看懂。。下次再做笔记。
### 色彩的投射运算
利用其它色彩分量的倍数来更改自己色彩分量的值，这种运算就叫投射运算。
![](http://img.blog.csdn.net/20160524151547855?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
#### 黑白图片
<pre><code>ColorMatrix colorMatrix = new ColorMatrix(new float[]{  
        0.213f, 0.715f, 0.072f, 0, 0,  
        0.213f, 0.715f, 0.072f, 0, 0,  
        0.213f, 0.715f, 0.072f, 0, 0,  
        0,       0,    0, 1, 0,  
});      
</code></pre>
#### 色彩反色
<pre><code>ColorMatrix colorMatrix = new ColorMatrix(new float[]{  
        0,1,0,0,0,  
        1,0,0,0,0,  
        0,0,1,0,0,  
        0,0,0,1,0  
});       
</code></pre>
#### 变旧照片
<pre><code>ColorMatrix colorMatrix = new ColorMatrix(new float[]{  
        1/2f,1/2f,1/2f,0,0,  
        1/3f,1/3f,1/3f,0,0,  
        1/4f,1/4f,1/4f,0,0,  
        0,0,0,1,0  
});       
</code></pre>
## ColorMatrix
以上是直接设置值，需要一定的色彩基础，android中的ColorMatrix自带了一些函数来帮我们完成饱和度，色彩旋转等操作的函数。
### 构造函数
<pre><code>ColorMatrix()  
ColorMatrix(float[] src)  
ColorMatrix(ColorMatrix src)        
</code></pre>
### 设置、重置函数
<pre><code>public void set(ColorMatrix src)  
public void set(float[] src)  
public void reset()        
</code></pre>
### 设置饱和度
* setSaturation(float sat)
sat:表示当前色彩饱和度放大的倍数。取值为0变身完全无色彩，即灰度图像。取值为1，表示色彩不变动，当色彩大于1时，显示色彩过度饱和。

### 色彩缩放
* setScale(float rScale,float gScale,float bScale,float aScale)
四个参数分别对应R,G,B,A颜色值的缩放倍数。
### 色彩旋转
* setRotate(int axis,float degrees)
axis:表示围绕那个轴旋转，取值为0，1，2，分别对应红绿蓝。
degrees:表示旋转的度数。
### ColorMatrics相乘
* setConcat(ColorMatrix matA,ColorMatrix matB)
### getArray()获取当前矩阵数组
索引顺序为：
![](http://img.blog.csdn.net/20160419100502816?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)