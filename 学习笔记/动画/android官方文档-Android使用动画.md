#Android动画

##Crossfading Two Views
其实是两个View切换时的动画，淡入淡出的动画，至于为什么要用这个动画，是为了更好的交互，避免直接切换View的生硬感。

核心代码
<pre><code>//要显示的View设置Alpha值为0f,设置VISIBLE
showView.setAlpha(0f);
showView.setVisibility(View.VISIBLE);

//设置渐变动画
showView.animate().alpha(1f)
		  .setDuration(mShortAnimationDuration)
		  .setListener(null);

//要隐藏的View设置渐变动画，动画结束时设置GONE隐藏
hideView.animate().alpha(0f)
 		  .setDuration(mShortAnimationDuration)
		  .setListener(new AnimatorListenerAdapter() {
		  @Override
                  public void onAnimationEnd(Animator animation) {
                  hideView.setVisibility(View.GONE);
          }
 });
</code></pre>
