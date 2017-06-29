#Property Animation(属性动画)
##属性动画的特征
Duration(持续时间)：动画的持续时间。

Time interpolation(时间差值):定义动画的变化率。

Repeat count and behavior(重复次数和行为):定义重复的次数，重复是从头开始还是反向。

Animatorsets(动画设置)：动画集合，可以一起播放，顺序播放，指定延迟等等。

Frame refresh delay:帧刷新延迟，对于你的动画，多久刷新一次帧。

##属性动画怎么工作
![](https://developer.android.com/images/animation/valueanimator.png)

属性动画的运行机制是通过不断地对值进行操作来实现的，而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。除此之外，ValueAnimator还负责管理动画的播放次数、播放模式、以及对动画设置监听器等。

TypeEvaluator 决定了动画如何从初始值过渡到结束值。
TimeInterpolator 决定了动画从初始值过渡到结束值的节奏。
以去从家到公司的路程为例，家就是初始值，公司就是结束值，TypeEvaluator就是从家到公司用什么路线去的，有直线，曲线，绕城一圈等等。TimeInterpolator就是速率，如果我是直线走，我是飞奔而去的速度，还是慢慢悠悠的过去。


##Property Animation与View Animation的不同
###View Animation

1.不能动画非View对象，只能做一些缩放，旋转的功能。

2.修改的只是绘制的view，并不是view的本身。

###Property Animation
没有这些约束，可以动画任何东西（View和非View）和对象本身的位置大小。


##用ValueAnimator创建动画
<pre><code>ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
animation.setDuration(1000);
animation.start();</code></pre>

<pre><code>ValueAnimator animation = ValueAnimator.ofObject(new MyTypeEvaluator(), startPropertyValue, endPropertyValue);
animation.setDuration(1000);
animation.start();</code></pre>

##使用ObjectAnimator创建动画
<pre><code>ObjectAnimator anim = ObjectAnimator.ofFloat(foo, "alpha", 0f, 1f);
anim.setDuration(1000);
anim.start();</code></pre>

<pre><code>ObjectAnimator.ofFloat(targetObject, "propName", 1f)</code></pre>

##用AnimatorSet让多个动画一起使用
<pre><code>AnimatorSet bouncer = new AnimatorSet();
bouncer.play(bounceAnim).before(squashAnim1);
bouncer.play(squashAnim1).with(squashAnim2);
bouncer.play(squashAnim1).with(stretchAnim1);
bouncer.play(squashAnim1).with(stretchAnim2);
bouncer.play(bounceBackAnim).after(stretchAnim2);
ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
fadeAnim.setDuration(250);
AnimatorSet animatorSet = new AnimatorSet();
animatorSet.play(bouncer).before(fadeAnim);
animatorSet.start();</code></pre>


##Animation Listeners
###Animator.AnimatorListener

1.onAnimationStart()——动画开始时

2.onAnimationEnd() ——动画结束时

3.onAnimationRepeat() ——动画重复时

4.onAnimationCancel() ——动画取消时，无论怎么结束的。

###ValueAnimator.AnimatorUpdateListener

onAnimationUpdate() ——对每帧动画的监听

<pre><code>ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
fadeAnim.setDuration(250);
fadeAnim.addListener(new AnimatorListenerAdapter() {
public void onAnimationEnd(Animator animation) {
    balls.remove(((ObjectAnimator)animation).getTarget());
}</code></pre>


##Animating Layout Changes to ViewGroups(ViewGroups变化动画）
- APPEARING 当一个View在ViewGroup中出现时，对此View设置的动画
- CHANGE_APPEARING 当一个View在ViewGroup中出现时，对此View对其他View位置造成影响，对其他View设置的动画
- DISAPPEARING 当一个View在ViewGroup中消失时，对此View设置的动画
- CHANGE_DISAPPEARING  不是由于View出现或消失造成对其他View位置造成影响，然后对其他View设置的动画。
<pre><code>LinearLayout
    android:orientation="vertical"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:id="@+id/verticalContainer"
    android:animateLayoutChanges="true" 
</code></pre>

##Using a TypeEvaluator
就是开始到结束的变化规律。可以是轨迹，可以是颜色的变化等等。
<pre><code>public class FloatEvaluator implements TypeEvaluator {

    public Object evaluate(float fraction, Object startValue, Object endValue) {
        float startFloat = ((Number) startValue).floatValue();
        return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);
    }
}</code></pre>


##Using Interpolators
就是开始到结束的变化率，速率的变化。
android API中给的两个变化率的定义。
AccelerateDecelerateInterpolator
<pre><code>public float getInterpolation(float input) {
    return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
}</code></pre>
LinearInterpolator
<pre><code>public float getInterpolation(float input) {
    return input;
}</code></pre>


##Specifying Keyframes
<pre><code>Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(target, pvhRotation)
rotationAnim.setDuration(5000ms);</code></pre>

##常用的作用于视图的动画
- translationX ，translationY
- rotation，rotationX，rotationY
- scaleX，scaleY
- x y
- alpha

`ObjectAnimator.ofFloat(myView, "rotation", 0f, 360f);`

##Animating with ViewPropertyAnimator
是并行动画更简单。看代码可以知道，有三种并行动画的方式。
Multiple ObjectAnimator objects
<pre><code>ObjectAnimator animX = ObjectAnimator.ofFloat(myView, "x", 50f);
ObjectAnimator animY = ObjectAnimator.ofFloat(myView, "y", 100f);
AnimatorSet animSetXY = new AnimatorSet();
animSetXY.playTogether(animX, animY);
animSetXY.start();</code></pre>
One ObjectAnimator
<pre><code>PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 50f);
PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);
ObjectAnimator.ofPropertyValuesHolder(myView, pvhX, pvyY).start();</code></pre>
ViewPropertyAnimator
<pre><code>myView.animate().x(50f).y(100f);</code></pre>

##Declaring Animations in XML
在xml中定义动画
<pre><code>set android:ordering="sequentially">
    set
        objectAnimator
            android:propertyName="x"
            android:duration="500"
            android:valueTo="400"
            android:valueType="intType"/>
        objectAnimator
            android:propertyName="y"
            android:duration="500"
            android:valueTo="300"
            android:valueType="intType"/>
    /set>
    objectAnimator
        android:propertyName="alpha"
        android:duration="500"
        android:valueTo="1f"/
set></code></pre>

<pre><code>AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(myContext,
    R.anim.property_animator);
set.setTarget(myObject);
set.start();</code></pre>


