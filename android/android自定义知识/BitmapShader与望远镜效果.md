作者博文：http://blog.csdn.net/harvic880925/article/details/52039081

本文只是读此博客的笔记
# BitmapShader与望远镜效果
* public Shader setShader(Shader shader)

通过Shader指定对应的图像、渐变色等来填充图形的。
Shader是一个基类，只有两个方法，一个是setLocalMatrix(Matrix localM)、getLocalMatrix(Matrix localM)用来设置坐标变换矩阵的。主要功能实现靠它的派生类。

BitmapShader,ComposeShader,LinearGradient,RadialGradient,SweepGradient。
## BitmapShader基本用法
* public BitmapShader(Bitmap bitmap,TileMode tileX,TileMode tileY)

bitmap:当前图像。
tileX：当X轴超出单个图片大小时所用的重复策略。
tileY:同上。

1. TileMode.CLAMP:用边缘色彩填充多余空间
2. TileMode.REPEAT:重复原图像来填充多余空间
3. TileMode.MIRROR:重复使用镜像模式的图像来填充多余空间

注意：填充是先填充竖向，后填充横向。