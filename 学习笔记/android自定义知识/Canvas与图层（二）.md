作者博文：http://blog.csdn.net/harvic880925/article/details/51332494

本文只是读此博客的笔记
# Canvas与图层（二）
## FLAG的具体含义
### FLAG概述
* public int save()  
* public int save(int saveFlags)  
* public int saveLayer(RectF bounds, Paint paint, int saveFlags)  
* public int saveLayer(float left, float top, float right, float bottom,Paint paint, int saveFlags)  
* public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags)  
* public int saveLayerAlpha(float left, float top, float right, float bottom,int alpha, int saveFlags) 

save的两个函数与saveLayer的两个函数的区别在于,save不会新建画布，saveLayer会新建画布。

| FLAG        | 意义           | 适用  |
| ------------- |:-------------:| -----:|
|ALL_SAVE_FLAG      | 保存所有的标识 | save()、saveLayer() |
| MATRIX_SAVE_FLAG    | 仅保存canvas的matrix数组   |   save()、saveLayer() |
| CLIP_SAVE_FLAG | 仅保存canvas的当前大小     |   save()、saveLayer() |
|HAS_ALPHA_LAYER_SAVE_FLAG    | 标识新建的bmp具有透明度，在与上层画布结合时，透明位置显示上图图像,与FULL_COLOR_LAYER_SAVE_FLAG冲突，若同时指定，则以HAS_ALPHA_LAYER_SAVE_FLAG为主 | saveLayer() |
| FULL_COLOR_LAYER_SAVE_FLAG   | 标识新建的bmp颜色完全独立，在与上层画布结合时，先清空上层画布再覆盖上去   |   saveLayer() |
| CLIP_TO_LAYER_SAVE_FLAG | 在保存图层前先把当前画布根据bounds裁剪，与CLIP_SAVE_FLAG冲突，若同时指定，则以CLIP_SAVE_FLAG为主     |   saveLayer() |

#### MATRIX_SAVE_FLAG
1、当save\saveLayer调用Canvas.MATRIX_SAVE_FLAG标识时只会保存画布的位置矩阵信息，在canvas.restore()时也只会恢复位置信息，而改变过的画布大小是不会被恢复的。 

2、当使用canvas.saveLayer(Canvas.MATRIX_SAVE_FLAG)时，需要与Canvas.HAS_ALPHA_LAYER_SAVE_FLAG一起使用，不然新建画布所在区域原来的图像将被清空。（后面会讲原因）
#### CLIP_SAVE_FLAG
1、当save/saveLayer调用 Canvas.CLIP_SAVE_FLAG时只会保存画布的裁剪信息，在canvas.restore()时也只会恢复裁剪信息，而改变过的画布位置信息是不会被恢复的。 

2、当使用canvas.saveLayer(Canvas.CLIP_SAVE_FLAG)时，需要与Canvas.HAS_ALPHA_LAYER_SAVE_FLAG一起使用，不然新建画布所在区域原来的图像将被清空。
#### FLAG之HAS_ALPHA_LAYER_SAVE_FLAG和FULL_COLOR_LAYER_SAVE_FLAG
1、HAS_ALPHA_LAYER_SAVE_FLAG表示新建的bitmap画布在与上一个画布合成时，不会将上一层画布内容清空，直接盖在上一个画布内容上面。 

2、FULL_COLOR_LAYER_SAVE_FLAG则表示新建的bimap画布在与上一个画布合成时，先将上一层画布对应区域清空，然后再盖在上面。 

3、当HAS_ALPHA_LAYER_SAVE_FLAG与FULL_COLOR_LAYER_SAVE_FLAG两个标识同时指定时，以HAS_ALPHA_LAYER_SAVE_FLAG为主 

4、当即没有指定HAS_ALPHA_LAYER_SAVE_FLAG也没有指定FULL_COLOR_LAYER_SAVE_FLAG时，系统默认使用FULL_COLOR_LAYER_SAVE_FLAG；
#### FLAG之CLIP_TO_LAYER_SAVE_FLAG
1、CLIP_TO_LAYER_SAVE_FLAG意义是在新建bitmap前，先把canvas给裁剪，一旦画板被裁剪，那么其中的各个画布都会被受到影响。而且由于它是在新建bitmap前做的裁剪，所以是无法恢复的； 

2、当CLIP_TO_LAYER_SAVE_FLAG与CLIP_SAVE_FLAG标识共用时，在调用restore()后，画布将被恢复
#### FLAG之ALL_SAVE_FLAG
1、对于save(int flag),ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG。

2、对于saveLayer(int flag),ALL_SAVE_FLAG = MATRIX_SAVE_FLAG | CLIP_SAVE_FLAG|HAS_ALPHA_LAYER_SAVE_FLAG。
## restore()与restoreTocount()
1、restore的意义是把回退栈中的最上层画布状态出栈，恢复画布状态。restoreToCount(int count)的意义是一直退栈，直到指定层count做为栈顶，将此之前的所有动作都恢复。 

2、所以无论哪种save方法，哪个FLAG标识，保存画布时都使用的是同一个栈 

3、restore()与restoreToCount(int count)针对的都是同一个栈，所以是完全可以通用和混用的。