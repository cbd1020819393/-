读http://blog.csdn.net/guolin_blog/article/details/9316683
  http://blog.csdn.net/guolin_blog/article/details/28863651
的笔记

# Android高效加载大图、多图解决方案

## 获取应用程序最高可用内存
```
int maxMemory=(int)(Runtime.getRuntime().maxMemory/1024);
获得的是KB。
```

## 使用BitmapFactory压缩图片
1. BitmapFactory.decodeByteArray
2. BitmapFactory.decodeFile
解析SD卡中的图片 
3. BitmapFactory.decodeResource
解析资源文件中的图片 
4. BimmapFactory.decodeStream
解析网络上的图片

这些方法可以尝试为已经构建的bitmap分配内存，这样就不会很容易导致OOM出现。
BitmapFactory.Options参数

### 获取图片的宽高
将BitmapFactory.Options中inJustDecodeBounds属性设置为true，可以禁止为bitmap分配内存，返回值不再是一个Bitmap对象，而是null,但是outWidth,outHeight,outMimeType属性都会被赋值。

这个技巧可以让我们获取到图片的长宽度，MIME类型，从而根据情况对图片进行压缩。
```
BitmapFactory.Options options = new BitmapFactory.Options();
options.inJustDecodeBounds = true;
BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
int imageHeight = options.outHeight;
int imageWidth = options.outWidth;
String imageType = options.outMimeType;
```

这个技巧的好处是可以检查每张图片的大小，考虑是否压缩，避免OOM。

### 得出图片的大小
图片的大小=宽*高*像素点占用字节
例：
ARGB_8888类型图片2048*1536，ARGB_8888每个像素点占4位。
大小=2048*1536*4/(1024*1024）=12MB

### 缩放图片
通过设置BitmapFactory.Options中的inSampleSize的值就可以实现
例：
inSampleSize的值设置为4
大小=（2048/4）*(1536/4)/(1024*1024）=0.75MB

由此可见inSampleSize是同时作用于宽高。
```
public static int calculateInSampleSize(BitmapFactory.Options options,
        int reqWidth, int reqHeight) {
    // 源图片的高度和宽度
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
        // 计算出实际宽高和目标宽高的比率
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);
        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
        // 一定都会大于等于目标的宽和高。
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }
    return inSampleSize;
```

### 使用图片缓存技术
内存缓存技术对那些大量占用应用程序宝贵内存的图片提供了快速访问的方法。其中最核心的类是LruCache (此类在android-support-v4的包中提供) 。这个类非常适合用来缓存图片，它的主要算法原理是把最近使用的对象用强引用存储在 LinkedHashMap 中，并且把最近最少使用的对象在缓存值达到预设定值之前从内存中移除。


设置缓存代码
```
private LruCache<String, Bitmap> mMemoryCache;

@Override
protected void onCreate(Bundle savedInstanceState) {
    // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
    // LruCache通过构造函数传入缓存值，以KB为单位。
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    // 使用最大可用内存值的1/8作为缓存的大小。
    int cacheSize = maxMemory / 8;
    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // 重写此方法来衡量每张图片的大小，默认返回图片数量。
            return bitmap.getByteCount() / 1024;
        }
    };
}

public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
    if (getBitmapFromMemCache(key) == null) {
        mMemoryCache.put(key, bitmap);
    }
}

public Bitmap getBitmapFromMemCache(String key) {
    return mMemoryCache.get(key);
}
```

加载图片代码
```
public void loadBitmap(int resId, ImageView imageView) {
    final String imageKey = String.valueOf(resId);
    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
    if (bitmap != null) {
        imageView.setImageBitmap(bitmap);
    } else {
        imageView.setImageResource(R.drawable.image_placeholder);
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(resId);
    }
}
```


## DiskLruCache磁盘缓存
LruCache只是管理了内存中图片的存储与释放
DiskLruCache可以把缓存放在磁盘，避免图片的重复网络获取。

## DiskLruCache实例化
不能new，只能通过open方法
public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize) 
open()方法接收四个参数，第一个参数指定的是数据的缓存地址，第二个参数指定当前应用程序的版本号，第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，第四个参数指定最多可以缓存多少字节的数据。

### 判断是否有sd卡
```
public File getDiskCacheDir(Context context, String uniqueName) {  
    String cachePath;  
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
            || !Environment.isExternalStorageRemovable()) {  
        cachePath = context.getExternalCacheDir().getPath();  
    } else {  
        cachePath = context.getCacheDir().getPath();  
    }  
    return new File(cachePath + File.separator + uniqueName);  
} 
```
有就存在SD卡，没有放手机存储中。

### 版本号变更
版本号变更会让缓存路径下存储的所有数据都会被清除掉，因为DiskLruCache认为当应用程序有版本更新的时候，所有的数据都应该从网上重新获取。


## DiskLruCache写操作
public Editor edit(String key) throws IOException 

## 使用md5编码得到唯一key
```
public String hashKeyForDisk(String key) {  
    String cacheKey;  
    try {  
        final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
        mDigest.update(key.getBytes());  
        cacheKey = bytesToHexString(mDigest.digest());  
    } catch (NoSuchAlgorithmException e) {  
        cacheKey = String.valueOf(key.hashCode());  
    }  
    return cacheKey;  
}  
  
private String bytesToHexString(byte[] bytes) {  
    StringBuilder sb = new StringBuilder();  
    for (int i = 0; i < bytes.length; i++) {  
        String hex = Integer.toHexString(0xFF & bytes[i]);  
        if (hex.length() == 1) {  
            sb.append('0');  
        }  
        sb.append(hex);  
    }  
    return sb.toString();  
```

## 使用Editor写入
```
new Thread(new Runnable() {  
    @Override  
    public void run() {  
        try {  
            String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
            String key = hashKeyForDisk(imageUrl);  
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);  
            if (editor != null) {  
                OutputStream outputStream = editor.newOutputStream(0);  
                if (downloadUrlToStream(imageUrl, outputStream)) {  
                    editor.commit();  
                } else {  
                    editor.abort();  
                }  
            }  
            mDiskLruCache.flush();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}).start();    
```

abort表示放弃，commit表示提交。

## 读取缓存
public synchronized Snapshot get(String key) throws IOException  
```
try {  
    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";  
    String key = hashKeyForDisk(imageUrl);  
    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);  
    if (snapShot != null) {  
        InputStream is = snapShot.getInputStream(0);  
        Bitmap bitmap = BitmapFactory.decodeStream(is);  
        mImage.setImageBitmap(bitmap);  
    }  
} catch (IOException e) {  
    e.printStackTrace();  
}  
```

## 移除缓存
```
try {  
    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";    
    String key = hashKeyForDisk(imageUrl);    
    mDiskLruCache.remove(key);  
} catch (IOException e) {  
    e.printStackTrace();  
}  
```

## diskLrucache的api
### size()
获取缓存数量

### flush()
这个方法用于将内存中的操作记录同步到日志文件（也就是journal文件）当中
一般在activity的onPause（）方法中调用一次flush()方法就可以了 。

### close()
这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，通常只应该在Activity的onDestroy()方法中去调用close()方法。

### delete()
这个方法用于将所有的缓存数据全部删除，其实只需要调用一下DiskLruCache的delete()方法就可以实现了。