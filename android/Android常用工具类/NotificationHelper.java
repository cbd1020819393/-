package database.cbd.com.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created by A on 2016/11/1.
 */
public class NotificationHelper {

    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    //创建普通的Notification
    public void createOrdinaryNotification(PendingIntent pendingIntent,String title,String contentText,int smallIcon,int notificationId){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(contentText);
        builder.setSmallIcon(smallIcon);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.flags =Notification.FLAG_AUTO_CANCEL;
        manager.notify(notificationId, notification);
    }

    //创建自定义的Notification
    //自定义的Notification用notification.flags =Notification.FLAG_AUTO_CANCEL 无效
    //只能在调用clearNotification删除
    public void createCustomNotification(PendingIntent pendingIntent,RemoteViews remoteViews,int smallIcon,boolean oneGoing,int notificationId){

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(context);
                builder.setOngoing(oneGoing);
                builder.setSmallIcon(smallIcon);
                if(pendingIntent!=null){
                    builder.setContentIntent(pendingIntent);
                }
                Notification notification = builder.build();
                notification.flags =Notification.FLAG_AUTO_CANCEL;
                if(android.os.Build.VERSION.SDK_INT >= 16) {
                    notification.bigContentView = remoteViews;
               }
                notification.contentView = remoteViews;
                manager.notify(notificationId, notification);
    }


    //删除Notification
    public void clearNotification(int noticeId){
        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(noticeId);

    }
}
