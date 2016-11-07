#自定义View
##步骤
1、自定义View的属性

2、在View的构造方法中获得我们自定义的属性

 3、重写onMesure（不一定重写）

4、重写onDraw

###自定义View的属性

在values中建立attrs.xml在里面可以定义我们的属性和声明我们整个样式。

    <attr name="titleText" format="string" />  
    <attr name="titleTextColor" format="color" />  
    <attr name="titleTextSize" format="dimension" />  
  
    <declare-styleable name="CustomTitleView">  
        <attr name="titleText" />  
        <attr name="titleTextColor" />  
        <attr name="titleTextSize" />  
     

format取值类型
string,color,dimension,integer,enum,reference,flag,boolean,fraction。
<pre><code>
 reference：参考某一资源ID。
 color：颜色值。
 boolean：布尔值。
 dimension：尺寸值。
 float：浮点值。
 integer：整型值。
 string：字符串。
 fraction：百分数。
 enum：枚举值。
 flag：位或运算。
</code></pre>

各个值的用法和含义
[链接](http://www.cnblogs.com/rayray/p/3442026.html)



##在View的构造方法中获得我们自定义的属性
<pre><code>  // 解析自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.Lrc);
        mTextSize = ta.getDimension(R.styleable.Lrc_textSize, 50.0f);
        mRows = ta.getInteger(R.styleable.Lrc_rows, 5);
        mDividerHeight = ta.getDimension(R.styleable.Lrc_dividerHeight, 0.0f);

        normalTextColor = ta.getColor(R.styleable.Lrc_normalTextColor,
        		Color.BLACK);
        currentTextColor = ta.getColor(R.styleable.Lrc_currentTextColor,
        		Color.YELLOW);
        ta.recycle();
        // </end>
        // 计算krc面板的高度
        mLrcHeight = (int) (mTextSize + mDividerHeight) * mRows + 5;
        mNormalPaint = new Paint();
        mCurrentPaint = new Paint();
        // 初始化paint
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setColor(normalTextColor);
        mCurrentPaint.setTextSize(mTextSize);
</code></pre>

##重写onMesure（不一定重写）
<pre><code>
	int widthMode = MeasureSpec.getMode(widthMeasureSpec);  
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
   	int heightMode = MeasureSpec.getMode(heightMeasureSpec);  
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);  
    int width;  
    int height ;  
    if (widthMode == MeasureSpec.EXACTLY)  
    {  
        width = widthSize;  
    } else  
    {  
        mPaint.setTextSize(mTitleTextSize);  
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);  
        float textWidth = mBounds.width();  
        int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());  
        width = desired;  
    }  
  
    if (heightMode == MeasureSpec.EXACTLY)  
    {  
        height = heightSize;  
    } else  
    {  
        mPaint.setTextSize(mTitleTextSize);  
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);  
        float textHeight = mBounds.height();  
        int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());  
        height = desired;  
    }  
      
      
  
    setMeasuredDimension(width, height);  
</code></pre>

##重写onDraw
这个是核心，没有固定的代码，就是在这里实现希望的效果。