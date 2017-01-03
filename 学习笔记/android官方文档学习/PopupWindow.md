#PopupWindow
##定义
popupWindow是一个在activity上的浮动窗口。可以用来显示任意的界面。

##动画
1. 动画可以通过setanimationstyle指定资源ID。
2. 可以通过popupanimationstyle XML属性指定。
3. API23开始，可以通过setEnterTransition(Transition)或者setExitTransition(Transition)。
4. 也可以指定在pupupwindow的style XML中，通过popupentertransition和popupexittransition属性


##接口
popupWindow.OnDismissListener
点击消失的接口。

##XML属性
1. android：overlapAnchor
2. android:popupAnimationStyle popup window的动画
3. android:popupBackground：popup window的背景。
4. android：popupElevation
5. android:popupEnterTransition：进入的过渡动画
6. android:popupExitTransition：退出的过渡动画



