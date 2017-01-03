参考：
http://blog.csdn.net/qq_33689414/article/details/52205730
http://www.jianshu.com/p/b1df61a4df77
# Databinding
Databinding是一个support库，可以在所有android平台最低能到Android2.1（API等级7+）上使用。

需求：Android Plugin for Gradle 1.5.0-alpha1 或 更高版本。

## 构建环境
在build.gradle构建文件中加入
<pre><code>android {
    ....
    dataBinding {
        enabled = true    
    }    
}
</code></pre>
需求：Android Studio 1.3.0 或 更高版本。
## DataBinding基本使用
* 单纯的摆脱findviewbyid
* 绑定基本数据类型及String
* 绑定Model数据
* 绑定事件
* 通过静态方法转换数据类型
* 通过运算符操作数据
* 自定义Binding的类名
* 绑定相同Model的操作
* model变量改变自动更新数据
* 绑定List/Map等集合数据
* Obserbable自动更新
* Databinding与include标签的结合
* DataBinding与RecyclerView的结合

### 使用DataBinding时的xml
``` xml
<!--布局以layout作为根布局-->
<layout>
    <!--我们需要展示的布局-->
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="www.zhang.com.databinding.MainActivity">

        <TextView
            android:id="@+id/text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            />
    </LinearLayout>
</layout>
  ```
从上面的xml代码中可知，使用DataBinding的根标签是layout

### 单纯的摆脱findviewbyid
```
 //第一种绑定方式
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        //第二种绑定方式
//        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        binding.text.setText("go go go!");
```
代码中的ActivityMainBinding类是自动生成的，根据activity_main的xml文件的名字生成。因为在xml中给TextView设置了id，在java代码中可知`binding.text.setText("go go go!");`，可以直接使用binding.text。

### 绑定基本数据和String
xml代码
```xml
<?xml version="1.0" encoding="utf-8"?><!--布局以layout作为根布局-->
<layout>

    <data>
        <!--绑定基本数据类型及String-->
        <!--name:   和java代码中的对象是类似的，名字自定义-->
        <!--type:   和java代码中的类型是一致的-->
        <variable
            name="content"
            type="String" />

        <variable
            name="enabled"
            type="boolean" />
    </data>
    <!--我们需要展示的布局-->
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <!--绑定基本数据类型及String的使用是通过   @{数据类型的对象}  通过对应数据类型的控制显示-->
        <TextView
            android:id="@+id/text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:clickable="@{enabled}"
            android:text="@{content}" />
    </LinearLayout>
</layout>
```
java代码
```
 binding.content.setText("go go go!");
```
### 绑定Model数据类型
数据Bean
```  
    private  String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
```
xml
```xml
<layout>

    <data>
        <!--绑定Model数据2中形式，一中是导入该类型的，一种指定类型的全称，和java一样-->
        <!--  方式一 -->
        <variable
            name="user"
            type="cbd.com.mvvm.User" />
        <!--  方式二 -->
        <!--<import type="cbd.com.mvvm.User" />-->
        <!--<variable-->
            <!--name="user"-->
            <!--type="User" />-->

    </data>
    <!--我们需要展示的布局-->
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:id="@+id/text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{user.text}" /><!--这里user.text相当于user.getText()-->
    </LinearLayout>
</layout>
```
java代码
```
 ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        //第二种绑定方式
//        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
        User user=new User("陈","宝迪");
		binding.setUser(user);
     // bean.User myUser=new bean.User("刘","朝晖");
    //  binding.setMyUser(myUser);
```
### 事件的绑定
可以自己写事件的监听回调

java代码
可以先定义接口类
```
public interface EventListener {
    public void click1(View v);
    public void click2(View v);
    public void click3(View v,String s);
    public void click4(String s);
}
```
xml中使用
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="cbd.com.mvvm.MainActivity">
    <data>
        <import type="cbd.com.mvvm.User"/>
        <import type="bean.User" alias="MyUser"/>
        <import type="android.graphics.Bitmap"/>
        <import type="java.util.ArrayList"/>
        <import type="java.util.HashMap"/>
        <import type="listener.EventListener"/>
        <variable
            name="user"
            type="User"/>
        <variable
            name="myUser"
            type="MyUser"/>
        <variable
            name="str"
            type="String"/>
        <variable
            name="click4"
            type="String"/>
        <variable
            name="listKey"
            type="int"/>
        <variable
            name="list"
            type="ArrayList&lt;String&gt;"/>
        <variable
            name="event"
            type="EventListener"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="@{event.click1}"
            android:text="@{user.firstName}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="@{event::click2}"
            android:text="@{user.lastName}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="@{(view)->event.click3(view,str)}"
            android:text="@{myUser.firstName}"/>
        <TextView
            android:id="@+id/text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="@{()->event.click4(click4)}"
             />
    </LinearLayout>
</layout>
```
xml代码中试了事件的多种写法
1. android:onClick="@{event.click1}"
2. android:onClick="@{event::click2}"
3. android:onClick="@{(view)->event.click3(view,str)}"
4. android:onClick="@{()->event.click4(click4)}"

都可以使用。暴红也没有关系。

java代码
```
 binding.setEvent(new EventListener() {
            @Override
            public void click1(View v) {
                Toast.makeText(MainActivity.this,"点击1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void click2(View v) {
                Toast.makeText(MainActivity.this,"点击2",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void click3(View view,String s) {
                Toast.makeText(MainActivity.this,"点击3"+":"+s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void click4(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });
```
其实就是把监听实现。

### 通过静态方法转换数据类型
java
```
public class GetUserUtil {
    public static String getUserFirstName(User user){
        return user.getFirstName();
    }
}
```
就是一个工具类，里面有个静态方法。
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="cbd.com.mvvm.MainActivity">
    <data>
        <import type="cbd.com.mvvm.User"/>
        <import type="bean.User" alias="MyUser"/>
        <import type="util.GetUserUtil"/>
        <variable
            name="user"
            type="User"/>
        <variable
            name="myUser"
            type="MyUser"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{GetUserUtil.getUserFirstName(myUser)}"/>
    </LinearLayout>
</layout>
```
从xml中可以得出直接使用GetUserUtil.getUserFirstName(myUser),并且不用使用Variable标签。
###通过运算符操作数据
简单使用其中一个作为例子

xml代码
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="cbd.com.mvvm.MainActivity">
    <data>
        <import type="cbd.com.mvvm.User"/>
        <import type="bean.User" alias="MyUser"/>
        <variable
            name="user"
            type="User"/>
        <variable
            name="myUser"
            type="MyUser"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{@string/app_name??user.firstName}"/>
    </LinearLayout>
</layout>
```
其中@{@string/app_name??user.firstName}相当于@{@string/app_name！=null?@string/app_name：user.firstName}。

DataBinding支持的表达式：
```
数学表达式： + - / * %

字符串拼接 +

逻辑表达式 && ||

位操作符 & | ^

一元操作符 + - ! ~

位移操作符 >> >>> <<

比较操作符 == > < >= <=

instanceof

分组操作符 ()

字面量 - character, String, numeric, null

强转、方法调用

字段访问

数组访问 []

三元操作符 ?:

聚合判断（Null Coalescing Operator）语法 ‘??’
```

缺少的操作
```
this

super

new

显式泛型调用
```
###自定义Binding的类名
三种方式
1. <data class=".binding.MainBinding">
2. <data class="MainBinding">
3. <data class=".MainBinding">

* 使用第一种方式生成的Binding类是在cbd.com.mvvm.binding.MainBinding。就是你指定的位置。
* 使用第二种方式生成的Binding类是在cbd.com.mvvm.databinding.MainBinding。
* 使用第三种方式生成的Binding类是在 cbd.com.mvvm.MainBinding。

值得注意的是任何一种生成的，用户都是看不到实际的类的。
###绑定相同model的操作
有两种情况
一种是同类的两个对象，一种是同名的两个类。
直接上xml，这块比较好理解的
```xml
<?xml version="1.0" encoding="utf-8"?><!--布局以layout作为根布局-->
<layout>

    <data>

        <import type="cbd.com.mvvm.User" />

        <variable
        name="user3"
        type="User" />

        <variable
        name="user4"
        type="User" />

    </data>
 <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user3.firstName}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user3.lastName}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user4.firstName}"/>
       
    </LinearLayout>
</layout>
```
这种就是同类的，在variable标签中只要name不同就行。
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="cbd.com.mvvm.MainActivity">
    <data class=".binding.MainBinding">
        <import type="cbd.com.mvvm.User"/>
        <import type="bean.User" alias="MyUser"/>
  <variable
            name="user"
            type="User"/>
        <variable
            name="myUser"
            type="MyUser"/>
  </data>
<LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user.firstName}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{user.lastName}" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{myUser.firstName}"/>
           </LinearLayout>
</layout>
```
因为两个类的名字都是User,需要通过alias设置别名用来区分。
###model变量改变自动更新数据
双向绑定的核心，数据改变UI也改变。
java代码
```
public class User extends BaseObservable{
    @Bindable
    private  String firstName;
    @Bindable
    private String lastName;


    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(cbd.com.mvvm.BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(cbd.com.mvvm.BR.lastName);
    }
	@Bindable
    public String getFirstName() {
        return this.firstName;
    }
	@Bindable
    public String getLastName() {
        return this.lastName;
    }
}
```
1. 首先让你的Bean实现Android.databinding.Observable接口，Observable接口可以允许附加一个监听器到model对象以便监听对象上的所有属性的变化。
2. 通过@Bindable注解来确认哪个参数可以动态更新。
3. notifyPropertyChanged(BR.参数名)通知更新这一个参数，需要与@Bindable注解配合使用。notifyChange()通知更新所有参数，可以不用和@Bindable注解配合使用。
4. 我测试发现可以放在变量上，也可放在get方法上，两种都可以动态改变。
###绑定List/Map等集合数据
list，map需要注意的就是在xml中的使用

xml代码
```xml
<layout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="cbd.com.mvvm.MainActivity">
    <data class=".binding.MainBinding">
        <import type="java.util.ArrayList"/>
        <import type="java.util.HashMap"/>
        <variable
            name="list"
            type="ArrayList&lt;String>"/>
        <variable
            name="map"
            type="HashMap&lt;String,String&gt;"/>
    </data>
    
</layout>
```
泛型的支持在编译时期回暴红，但是可以直接运行的，但是需要通过转义字符才行，测试两张方式洞可以。直接用ArrayList<String>编译不过去的。

1. `&lt;数据类型>`
2. `&lt;数据类型&gt`
### Observable数据改变自动更新
Observable是一个接口，它的子类有BaseObservable,ObservableField,ObservableBoolean, ObservableByte, ObservableChar, ObservableShort, ObservableInt, ObservableLong, ObservableFloat, ObservableDouble, and ObservableParcelable，ObservableArrayList,ObservableArrayMap。

其实最终都是同样的过程，当数据改变时，UI也同时会改变。

java Bean代码
```
public class Animal {
    public final ObservableField<String> field=new ObservableField<>();
    public final ObservableInt age=new ObservableInt();
}

```

xml代码
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context="cbd.com.mvvm.MainActivity">
    <data class=".binding.MainBinding">
        
        <import type="bean.Animal"/>
     
        <variable
            name="animal"
            type="Animal"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        >
             <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{animal.field}"/>
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{String.valueOf(animal.age)}"/>
    </LinearLayout>
</layout>
```
java代码
```
  //第一种绑定方式
        MainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        //第二种绑定方式
//        ActivityMainBinding binding=ActivityMainBinding.inflate(getLayoutInflater());
   
        final Animal animal=new Animal();
        animal.field.set("小狗");
        animal.age.set(5);
        binding.setAnimal(animal);
        binding.setEvent(new EventListener() {
           
            @Override
            public void click5(View v) {
                user.setFirstName("你好");
                user.setLastName("帅哥");
                myUser.setFirstName("你好");
                myUser.setLastName("美女");
                animal.field.set("小猫");
                animal.age.set(7);
            }
        });
    }
```