#着色器LinearGradient的使用
LinearGradint：线型渐变，线型渲染。

public LinearGradient(float x0, float y0, float x1, float y1, int colors[], float positions[],  TileMode tile) 

x0：表示渲染起始位置的x坐标。

y0：表示渲染起始位置的y坐标。

x1：表示渲染结束位置的x坐标。

y1：表示渲染结束位置的y坐标。

colors：表示渲染的颜色，它是一个颜色数组，数组长度必须大于等于2。

positions：表示colors数组中几个颜色的相对位置，是一个float类型的数组，该数组的长度必须与colors数组的长度相同。如果这个参数使用null也可以，这时系统会按照梯度线来均匀分配colors数组中的颜色。

tile:平铺方式,有三种模式,。

1. LinearGradient.TileMode.CLAMP
   如果没有铺完，就用最后一个颜色延伸。
2. LinearGradient.TileMode.REPEAT
   如果没有铺完，就重复铺颜色。
3. LinearGradient.TileMode.MIRROR
   如果没有铺完，就用颜色的反转，镜像效果。

http://blog.csdn.net/mwping1324/article/details/46551131
这位同学讲的非常清楚。
