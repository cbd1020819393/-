# 第五章 Android Scroll 分析
## 滑动效果是如何产生的
### Android坐标系
* getLocationOnScreen获取左上角坐标
* getRawX(),getRawY()获取相对于坐标原点的坐标系
* getX(),getY()获取相对于父控件的坐标系

### 触摸事件——MotionEvent
```
//单点触摸按下动作
public static final int ACTION_DOWN=0;
//单点触摸离开动作
public static final int ACTION_UP=1;
//触摸点移动动作
public static final int ACTION_MOVE=2;
//触摸动作取消
public static final int ACTION_CANCEL=3;
//触摸动作超出边界
public static final int ACTION_OUTSIDE=4;
//多点触摸按下动作
public static final int ACTION_POINTER_DOWN=5;
//多点离开动作
public static final int ACTION_POINTER_UP=6;
```
View提供的获取坐标方法
getTop():获取到的是View自身的顶边到其父布局顶边的距离
getLeft():获取到的是View自身的左边到其父布局顶边的距离
getBottom():获取到的是View自身的底边到其父布局顶边的距离
getRight():获取到的是View自身的右边到其父布局顶边的距离

MotionEvent提供的方法
getX():获取点击事件距离控件左边的距离，即视图坐标。
getY():获取点击事件距离控件顶边的距离，即视图坐标。
getRawX():获取点击事件距离屏幕左边的距离，即绝对坐标。
getRawY():获取点击事件距离屏幕顶边的距离，即绝对坐标。
## 实现滑动的七种方法
### layout方法
1. 重写自定义View的onTouchEvent方法
2. 在MotionEvent.ACTION_DOWN的情况下记录按下位置坐标
3. 在MotionEvent.ACTION_MOVE的情况下记录移动的位置坐标
4. 计算出两个点的偏移量，最后用layout(int l, int t, int r, int b)设置View的新位置。
### offsetLeftAndRight()与offsetTopAndBottom()
步骤与第一步相同，只是最后一步用offsetLeftAndRight()与offsetTopAndBottom()移动控件。
### LayoutParams
* 知道父控件是什么layout,用父控件相关的LayoutParams
* 使用MarginLayoutParams适配任何一种布局
### scrollTo与scrollBy
scrollTo是移动到（x,y）点，scrollBy是移动偏移量。
scrollTo与scrollBy作用的是内容，例如在textView中指的就是文字，在ViewGroup中指的就是里面的View。
scrollTo与scrollBy移动的是屏幕的可视区域，而不是控件本身。
### Scroller
没成功，暂且不提。
### 属性动画
别的篇幅会讲到。
### ViewDragHelper
