作者博文：http://blog.csdn.net/harvic880925/article/details/51288006

本文只是读此博客的笔记
# Paint之setXfermode(三)
## DST相关模式
Mode.DST、Mode.DST_IN、Mode.DST_OUT、Mode.DST_OVER、Mode.DST_ATOP
### Mode.DST
计算公式为：[Da, Dc] 
![](http://img.blog.csdn.net/20160430214514510?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
### Mode.DST_IN
计算公式为：[Da * Sa,Dc * Sa]
![](http://img.blog.csdn.net/20160430214703175?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
### Mode.DST_OUT
计算公式为：[Da * (1 - Sa), Dc * (1 - Sa)] 
![](http://img.blog.csdn.net/20160430222320818?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
### Mode.DST_OVER
计算公式为：[Sa + (1 - Sa)*Da, Rc = Dc + (1 - Da)*Sc] 
![](http://img.blog.csdn.net/20160430222451101?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
### Mode.DST_ATOP
计算公式为：[Sa, Sa * Dc + Sc * (1 - Da)] 
![](http://img.blog.csdn.net/20160430222532855?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 

总结：

1、DST相关模式是完全可以使用SRC对应的模式来实现的，只不过需要将目标图像和源图像对调一下即可。 

2、在SRC模式中，是以显示源图像为主，通过目标图像的透明度来调节计算结果的透明度和饱和度，而在DST模式中，是以显示目标图像为主，通过源图像的透明度来调节计算结果的透明度和饱和度。

##其他模式
Mode.CLEAR,Mode.XOR
### Mode.CLEAR
计算公式为：[0, 0] 
![](http://img.blog.csdn.net/20160430222816856?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 
### Mode.XOR
计算公式为：[Sa + Da - Sa*Da,Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)] 
![](http://img.blog.csdn.net/20160430222820481?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 


