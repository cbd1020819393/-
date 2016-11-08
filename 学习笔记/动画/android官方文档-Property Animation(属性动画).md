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

文／IAM四十二（简书作者）
原文链接：http://www.jianshu.com/p/420629118c10
著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。


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
