作者博文：http://blog.csdn.net/harvic880925/article/details/51317746

本文只是读此博客的笔记
# Canvas与图层(一)
## 如何获得一个Canvas对象
方法一：自定义view时，重写onDraw、dispatchDraw方法。

* 定义
<pre><code>protected void onDraw(Canvas canvas) {  
    super.onDraw(canvas);  
}  
  
protected void dispatchDraw(Canvas canvas) {  
    super.dispatchDraw(canvas);  
} </code></pre>
* onDraw、dispatchDraw区别
 onDraw是绘制视图自身。dispatchDraw是绘制子视图。
所以要在ViewGroup中绘图时，往往是重写dispatchDraw()方法。

方法二：使用Bitmap创建
<pre><code>//第一种
Canvas c = new Canvas(bitmap); 
// 第二种
Canvas c = new Canvas();   
c.setBitmap(bitmap);   
 </code></pre>

方法三：SurfaceHolder.lockCanvas()
## 图层与画布
save()和restore()可以保持恢复画布，还有一些函数也可以操作画布。
### saveLayer()
<pre><code>public int saveLayer(RectF bounds, Paint paint, int saveFlags)  
public int saveLayer(float left, float top, float right, float bottom,Paint paint, int saveFlags)
</code></pre>
bounds:要保存的区域的矩形。
saveFlags:ALL_SAVE_FLAG、MATRIX_SAVE_FLAG、CLIP_SAVE_FLAG、HAS_ALPHA_LAYER_SAVE_FLAG、FULL_COLOR_LAYER_SAVE_FLAG、CLIP_TO_LAYER_SAVE_FLAG。

ALL_SAVE_FLAG：表示保持全部内容。

saveLayer的作用：saveLayer会创建一个全新透明的bitmap，大小与指定保存的区域一致，其后的绘图操作都放在这个bitmap上进行。在绘制结束后，会直接盖在上一层的Bitmap上显示。
### 画布与图层
layer(图层):每调用canvas.drawXXX系列函数，就会生成一个透明图层来专门画这个图形。

bitmap(画布)：每一个画布都是一个bitmap，所有图形都是画在bitmap上。没调用一次canvas.drawXXX，都会生成一个专门的透明图层来画这个图形，画完后就盖在画布上。画布有两张，一种是view的原始画布，通过onDraw(Canvas canvas)函数传进来，控件的背景就是画在这个画布上。
另一种是人造画布，通过saveLayer()，new Canvas(bitmap)来新建一个画布。尤其是saveLayer()，一旦调用saveLayer()新建一个画布以后，所有的draw函数所画的图像都是画在这个画布上的，只有电泳restore、resoreToCount函数后，才会返回到原始画布上绘制。

Canvas(画板):画板的旋转，剪裁都会影响bitmap的旋转与剪裁。
## save()、saveLayer()、saveLayerAlpha()中的用法
* public int saveLayer(RectF bounds,Paint paint,int saveFlags)
* public int saveLayer(float left,float top,float right,float bottom ,Paint paint,int saveFlags)


1.saveLayer后的所有动作都只对新建画布有效。
调用了saveLayer函数后，新建一个bitmap，所有的动作只对新建的bitmap起作用。

2. 通过Rect指定矩形大小就是新建的画布大小。也可以通过指定左上，右下的坐标。
### saveLayerAlpha的用法
* public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags)  
* public int saveLayerAlpha(float left, float top, float right, float bottom,int alpha, int saveFlags)
 
alpha：取值0-255，可以用16进制的0xAA表示。



