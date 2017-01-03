作者博文：http://blog.csdn.net/harvic880925/article/details/39056701

本文只是读此博客的笔记
# 区域（Range）
## 构造Region
### 基本构造函数
* public Region()//创建一个空的区域
* public Region(Region region)//拷贝一个region的范围
* public Region(Rect r)//创建一个矩形的区域
* public Region(int left,int top,int right,int bottom)//创建一个矩形的区域

一般用第三第四个。
### 间接构造
* public void setEmpty（）
* public boolean set(Region region)
* public boolean set(Rect r)
* public boolean set(int left,int top,int right,int bottom)
* public boolean setPath(Path path,Region clip)

看方法的参数，意思很明了，不在赘述。
其中setPath的方法可以画出一些不规则的图形，如椭圆区域什么的。

## 矩形集枚举区域————RegionIterator类
* RegionIterator(region region)//根据区域构建对应的矩形集
* boolean next(Rect r)//获取下一个矩形，结果保存在参数Rect r中。
## 区域的合并、交叉等操作
* public final boolean union(Rect r)
* public boolean op(Rect r,Op op)
* public boolean op(int left,int top,int right,int bottom,Op op)
* public boolean op(Region,Op op)
* public boolean op(Rect rect,Region region,Op op)

Union(Rect r)指定合并操作。
其他构造都是指定与另一个区域的操作。
Op参数
假设用region1去组合region2
<pre><code>public enum Op{
	DIFFERENCE(0),//最终区域为region1与region2不同的区域
	INTERSECT(1),//最终区域为region1与region2相交的区域
	UNION(2),//最终区域为region1与region2组合一起的区域
	XOR(3),//最终区域为region1与region2相交之外的区域
	REVERSE_DIFFERENCE(4),//最终区域为region2与region1不同的区域
	REPLACE(5),//最终区域为region2的区域。
}
</code></pre>
![](http://img.blog.csdn.net/20140905095358720?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaGFydmljODgwOTI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 其他一些方法
<pre><code>/**几个判断方法*/    
public native boolean isEmpty();//判断该区域是否为空    
public native boolean isRect(); //是否是一个矩阵    
public native boolean isComplex();//是否是多个矩阵组合    
    
    
/**一系列的getBound方法，返回一个Region的边界*/    
public Rect getBounds()     
public boolean getBounds(Rect r)     
public Path getBoundaryPath()     
public boolean getBoundaryPath(Path path)     
    
    
/**一系列的判断是否包含某点 和是否相交*/    
public native boolean contains(int x, int y);//是否包含某点    
public boolean quickContains(Rect r)   //是否包含某矩形  
public native boolean quickContains(int left, int top, int right,    
                                        int bottom) //是否没有包含某矩阵形   
 public boolean quickReject(Rect r) //是否没和该矩形相交    
 public native boolean quickReject(int left, int top, int right, int bottom); //是否没和该矩形相交    
 public native boolean quickReject(Region rgn);  //是否没和该矩形相交    
    
/**几个平移变换的方法*/    
public void translate(int dx, int dy)     
public native void translate(int dx, int dy, Region dst);    
public void scale(float scale) //hide    
public native void scale(float scale, Region dst);//hide    
</code></pre>


