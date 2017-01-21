# andorid控件架构与自定义控件详解
## Android控件架构
控件被分为两类：
1. ViewGroup控件
2. View控件
ViewGroup作为父控件可以包含多个View控件，形成控件树，上层控件负责下层子控件的测量与绘制，并传递交互事件。
## View的测量
对View的测量都是在onMeasure()方法进行。
android通过MeasureSpec类来测量View。MeasureSpec是一个32位的int值，其中高2位为测量的模式，低30位为测量的大小，在计算中使用位运算是为了提高效率。
测量的模式有三种：
* EXACTLY
  精确值模式，以下两种用EXACTLY:
  1. 给控件指定layout_width属性或layout_height属性指定为具体数值时。
  2. 给控件设置match_parent属性（占据父View的大小）。
* AT_MOST
  最大值模式，当控件的layout_width属性或layout_height属性指定为wrap_content时，控件的大小一般随着控件的子控件或内容的变化而变化，此时控件的尺寸只要不超过父控件允许的最大尺寸即可。
* UNSPECIFIED
  不指定其大小测量模式，View想多大就多大，通常情况下载绘制自定义View才会使用。

View类默认的onMeasure()方法只支持EXACTLY模式，所以如果在自定义控件的时候不重写onMeasure()方法的话，就只能使用EXACTLY模式。

简单的关于测量的自定义代码，其实测量的那个方法measureDefaultSize就是把官方原有的getDefaultSize做一些修改。
```
public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDefaultSize(widthMeasureSpec), measureDefaultSize(heightMeasureSpec));
    }

    public int measureDefaultSize(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                result=500;
                result=Math.min(result,specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
}

 ```
## View的绘制
由于本人看了很多view的绘制，而且做了大量的笔记，在这不在重复。
## ViewGroup的测量
viewGroup会去管理其子View，其中一个管理项目就是负责子View的显示大小。
当ViewGroup的大小为wrap_content时，ViewGroup就需要对子View进行遍历，以便获得所有子View的大小，从而来决定自己的大小。而在其他模式下通过具体的值来设置自身的大小。
具体步骤：
1. 遍历子view的measure方法来获得对每一个子View的测量结果。
2. 测量完毕就要将子view放到合适的位置，这个过程就是View的Layout过程。viewGroup的Layout过程就是遍历调用子View的Layout方法，并指定其具体显示的位置。

在自定义ViewGroup时会重写onLayout方法来控制子View显示位置的逻辑。同样，如果需要支持wrap_content属性还要重写onMeasure方法。
## ViewGroup的绘制
viewGroup通常不需要绘制，本身没什么东西需要绘制，除了指定背景颜色。一般重写dispatchDraw()方法来绘制子view，其过程同样是通过遍历所有子View，并调用子View的绘制方法来完成绘制工作。
## 自定义View
View中比较重要的回调方法：
* onFinishInflate():从xml加载组件后回调。
* onSizeChanged():组件大小改变时回调。
* onMeasure():回调该方法来进行测量。
* onLayout():回调该方法来确定显示的位置。
* onTouchEvent():监听到触摸事件时回调。
* onDraw():绘制。

有三种方式来实现自定义控件：
* 对现有控件进行扩展。
* 通过组合来实现新的控件。
* 重写View来实现全新的控件。

### 对现有控件进行扩展
```
@Override
    protected void onDraw(Canvas canvas) {
//在回调父类方法之前，实现自己的逻辑，对TextView来说既是在绘制文本内容前
        super.onDraw(canvas);
//在回调父类方法之后，实现自己的逻辑，对TextView来说既是在绘制文本内容后
    }
```
### 创建复合控件
创建复合控件可以很好的创建出具有重用功能的控件集合。这种方式通常需要继承一个合适的ViewGroup，再给它添加指定功能的控件，从而组合成新的复合控件。通过这种方式的控件，我们一般会给它指定一些可配置的属性，有更好的拓展性。
#### 定义属性
在res资源目录的values目录下创建一个attrs.xml的属性定义文件。
```xml
<declare-styleable name="RoundedImageView">
        <attr name="riv_corner_radius" format="dimension" />
        <attr name="riv_corner_radius_top_left" format="dimension" />
        <attr name="riv_corner_radius_top_right" format="dimension" />
        <attr name="riv_corner_radius_bottom_left" format="dimension" />
        <attr name="riv_corner_radius_bottom_right" format="dimension" />
        <attr name="riv_border_width" format="dimension" />
        <attr name="riv_border_color" format="color" />
        <attr name="riv_mutate_background" format="boolean" />
        <attr name="riv_oval" format="boolean" />
        <attr name="android:scaleType" />
        <attr name="riv_tile_mode">
            <enum name="clamp" value="0" />
            <enum name="repeat" value="1" />
            <enum name="mirror" value="2" />
        </attr>
        <attr name="riv_tile_mode_x">
            <enum name="clamp" value="0" />
            <enum name="repeat" value="1" />
            <enum name="mirror" value="2" />
        </attr>
        <attr name="riv_tile_mode_y">
            <enum name="clamp" value="0" />
            <enum name="repeat" value="1" />
            <enum name="mirror" value="2" />
        </attr>
    </declare-styleable>
```
` TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.RoundedImageView);`
这句代码就可以获取自定义的属性集。
`ta.recyle()`
最后要回收。
#### 组合控件
就是在onDraw()方法中用代码添加已有的控件，最后达到你想要的控件。
里面的个体控件如果需要点击事件，就要自己定义接口，最后在暴露给调用者。
#### 引用UI模板
`xmlns:android="http://schemas.android.com/apk/res/android"`
指定引用了"android"，才可以使用"android:"来引用Android的系统属性。
`xmlns:custom="http://schemas.android.com/apk/res/res-auto"`
这里我们将引入的第三方控件的名字空间取名为custom。之后就可以引用attrs中自定义的属性。

### 重写View来实现全新的控件
由于本人看了很多view的绘制，而且做了大量的笔记，在这不在重复。

## 自定义ViewGroup
ViewGroup存在的目的就是为了对其子View进行管理，为其子View添加显示、响应的规则。自定义ViewGroup通常需要重写onMeasure()方法来对子View进行测量，重写onLayout()方法来确定子View的位置，重写onTouchEvent方法来增加响应事件。

## 事件拦截机制分析
假设是个三层嵌套，ViewGroup包含ViewGroup1，ViewGroup1包含View。
```xml
 <cbd.com.customknowledge.MyViewGroup
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:background="@android:color/holo_red_dark">
        <cbd.com.customknowledge.MyViewGroup2
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:background="@android:color/darker_gray">
            <cbd.com.customknowledge.MyView
                android:layout_width="100dp"
                android:layout_height="100dp"
                android:background="@android:color/white"/>
        </cbd.com.customknowledge.MyViewGroup2>
    </cbd.com.customknowledge.MyViewGroup>
```

viewGroup中的代码
```
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "Group:dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "Group:onInterceptTouchEvent: ");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "Group:onTouchEvent: ");
        return super.onTouchEvent(event);
    }
```

view中的代码
```
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "view:onTouchEvent: ");
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "view:dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

```

现在分析各种情况
1. 不做任何改动，log的打印，得出事件的分发
```
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: Group:dispatchTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: Group:onInterceptTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: Group2:dispatchTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: Group2:onInterceptTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: view:dispatchTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: view:onTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: Group2:onTouchEvent: 
01-05 13:43:45.848 5989-5989/cbd.com.customknowledge I/GOGOGOGO: Group:onTouchEvent: 
```
由log可知，点击View区域，事件的分发：
ViewGroup->ViewGroup2->View
onTouchEvent的处理顺序：
view->ViewGroup2->ViewGroup

2. ViewGroup拦截事件
把ViewGroup中的onInterceptTouchEvent的返回值设为true。
```
01-05 13:51:37.628 13316-13316/cbd.com.customknowledge I/GOGOGOGO: Group:dispatchTouchEvent: 
01-05 13:51:37.638 13316-13316/cbd.com.customknowledge I/GOGOGOGO: Group:onInterceptTouchEvent: 
01-05 13:51:37.638 13316-13316/cbd.com.customknowledge I/GOGOGOGO: Group:onTouchEvent: 
```

3.ViewGroup2拦截事件
把ViewGroup2中的onInterceptTouchEvent的返回值设为true。
```
01-05 13:52:53.008 14718-14718/cbd.com.customknowledge I/GOGOGOGO: Group:dispatchTouchEvent: 
01-05 13:52:53.008 14718-14718/cbd.com.customknowledge I/GOGOGOGO: Group:onInterceptTouchEvent: 
01-05 13:52:53.008 14718-14718/cbd.com.customknowledge I/GOGOGOGO: Group2:dispatchTouchEvent: 
01-05 13:52:53.008 14718-14718/cbd.com.customknowledge I/GOGOGOGO: Group2:onInterceptTouchEvent: 
01-05 13:52:53.008 14718-14718/cbd.com.customknowledge I/GOGOGOGO: Group2:onTouchEvent: 
01-05 13:52:53.008 14718-14718/cbd.com.customknowledge I/GOGOGOGO: Group:onTouchEvent: 
```

4.View不往上级传onTouchEvent
把View中的onTouchEvent的返回值设为true。
```
01-05 13:54:47.708 16550-16550/cbd.com.customknowledge I/GOGOGOGO: Group:dispatchTouchEvent: 
01-05 13:54:47.708 16550-16550/cbd.com.customknowledge I/GOGOGOGO: Group:onInterceptTouchEvent: 
01-05 13:54:47.708 16550-16550/cbd.com.customknowledge I/GOGOGOGO: Group2:dispatchTouchEvent: 
01-05 13:54:47.708 16550-16550/cbd.com.customknowledge I/GOGOGOGO: Group2:onInterceptTouchEvent: 
01-05 13:54:47.708 16550-16550/cbd.com.customknowledge I/GOGOGOGO: view:dispatchTouchEvent: 
01-05 13:54:47.708 16550-16550/cbd.com.customknowledge I/GOGOGOGO: view:onTouchEvent: 
```

5. ViewGroup2不往上级传onTouchEvent
把ViewGroup2中的onTouchEvent的返回值设为true。
```
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: Group:dispatchTouchEvent: 
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: Group:onInterceptTouchEvent: 
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: Group2:dispatchTouchEvent: 
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: Group2:onInterceptTouchEvent: 
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: view:dispatchTouchEvent: 
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: view:onTouchEvent: 
01-05 13:59:03.368 20657-20657/cbd.com.customknowledge I/GOGOGOGO: Group2:onTouchEvent: 
```