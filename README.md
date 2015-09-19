##Android MVVP data-binding 数据绑定

### `Data Binding Library` 介绍 
`Data Binding Library` 数据绑定库是Google在2015 的 I/O 大会上推出的一项技术,目的就是为开发者提供适用于用MVVM 模式的开发方案.开发者可以在xml中绑定业务数据到视图中. 这一开发模式有以下优点
-	<b>低耦合 </b> <br/>摆脱了直接在代码中根据模型设置视图显示的麻烦.<br/>当视图应操作更新时,可以通知给你的data对象.
-	<b>可复用</b><br/复用data对象的逻辑和数据变得非常方便<br/>xml文件也可以通过<include>标签来实现多个布局的复用
-	<b>分层独立</b> <br/>MVVP 模式让视图和数据模型之间耦合降低并相对独立,这样使得合作开发的相互独立性提高了不少,直接提高了开发效率
-	<b>可测试</b><br/> 由于业务逻辑和视图的有效分离,使得之前较为不易的Android单元测试变得容易.

###使用准备
在使用`Data Binding Library` 数据绑定库之前,需要以下准备工作
1.	确保使用的是`AndroidStudio1.3` 或更高版本.<br/>[AndroidStudio1.3.2下载链接](http://pan.baidu.com/s/1pJ7n7WR)
2.	添加`Data Binding Library`到项目的`gradle`构建文件中.如下:<br/>
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.3.0'
        classpath "com.android.databinding:dataBinder:1.0-rc1"

    }
}

allprojects {
    repositories {
        jcenter()
    }
}

```
注意:
-	引用的`gradle`版本需要`1.3.0`或更高
-	确保`jcenter`在`repositories`列表里
-	在需要使用`Data Binding` 的 `module` 中添加如下插件<br/>`apply plugin: 'com.android.databinding'`
###开始使用
接下来我们就创建一个示例引用来使用`Data Binding Library`
1.	建模
 建立一个记录小孩成长日历的模型:
 ```
 public class Baby implements Parcelable{
    private static final String TAG = "Baby";
    private String name;//宝宝名字
    private int age;//宝宝年龄
    private SexType mSex;//宝宝性别
    private String note;//宝宝日记内容
    private Date recordTime;//宝宝日记记录时间
    private boolean isLike;//宝宝记录是否点赞
    public enum SexType{
        GIRL("girl"),
        BOY("boy");
        private String str;

        SexType(String str) {
            this.str = str;
        }

        public static SexType fromString(String str){
            if (str!=null){
                for (SexType sexType :SexType.values()){
                    if (str.equalsIgnoreCase(sexType.str)){
                        return sexType;
                    }
                }
            }
            return null;
        }
    }
}
 ```
 和与视图相关的视图模型`BabyViewModel`(简单的可以省略,直接和模型绑定视图就行,这里建立是为了方便的转化原模型中多样的数据类型)
 ```
  public class BabyViewModel  {
    private Context mContext;
    private Baby mBaby;

    public BabyViewModel(Context context, Baby baby) {
        mContext = context;
        mBaby = baby;
    }

    public String getBabyName() {
        return mBaby.getName();
    }

    public Drawable getBabySex() {
        if (mBaby.getSex().compareTo(Baby.SexType.BOY) == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return mContext.getResources().getDrawable(R.mipmap.sex_boy, mContext.getTheme());
            }
            return mContext.getResources().getDrawable(R.mipmap.sex_boy);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return mContext.getResources().getDrawable(R.mipmap.sex_girl, mContext.getTheme());
            }
            return mContext.getResources().getDrawable(R.mipmap.sex_girl);
        }
    }

 
    public String getBabyAge() {
        return mBaby.getAge() + "岁";
    }

    public String getBabyRecord() {
        return mBaby.getNote();
    }

    public String getRecordTime() {
        return DateUtils.formatDateTime(mContext, mBaby.getRecordTime().getTime(), DateUtils.FORMAT_NUMERIC_DATE);
    }

    public String getRecordContent() {
        return mBaby.getNote();
    }

    public int getLikeColor() {
        return mBaby.isLike() ? Color.RED : Color.TRANSPARENT;
    }

    public View.OnClickListener onClickBaby() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mBaby.toString(), Toast.LENGTH_SHORT).show();
            }
        };

    }
}
 
 ```
 注意:
			- 可以直接绑定数据模型,但为了保持低耦合性,在这里采用了多创建一个对应的视图模型
			- 返回值其实是和xml文件中设置属性的值类型相对应的,`ImageView` 的`src` 还不能设置为资源引用(int),所以改为上面的`drawable`.
2.	绑定`layout`
 IDE会在编译过程中自动生成与`layout`名称相关的`ViewDataBinding`子类<br/>
 `item_baby` ------> `ItemBabyBinding`
```
<?xml version="1.0" encoding="utf-8"?>
<layout>

    <data>
        <variable
            name="viewModel"
            type="com.mopel.databindingdemo.viewModel.BabyViewModel"/>
    </data>

    <android.support.v7.widget.CardView
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:paddingBottom="5dp"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:paddingTop="5dp"
            android:onClick="@{viewModel.onClickBaby}">

            <RelativeLayout
                android:id="@+id/baby_title"
                android:layout_width="match_parent"
                android:layout_height="60dp"
                android:layout_alignParentTop="true"
                android:gravity="center_vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_alignParentLeft="true"
                    android:gravity="center"
                    android:textSize="16sp"
                    android:textStyle="bold"
                    android:text="@{viewModel.babyName}"/>

                <TextView
                    android:id="@+id/baby_age"
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_alignParentRight="true"
                    android:gravity="center"
                    android:textSize="14sp"
                    android:text="@{viewModel.babyAge}"/>

                <ImageView
                    android:layout_width="30dp"
                    android:layout_height="30dp"
                    android:layout_centerVertical="true"
                    android:layout_marginRight="10dp"
                    android:layout_toLeftOf="@id/baby_age"
                    android:src="@{viewModel.getBabySex}"/>
            </RelativeLayout>

            <RelativeLayout
                android:id="@+id/baby_bottom"
                android:layout_width="match_parent"
                android:layout_height="40dp"
                android:layout_alignParentBottom="true"
                android:gravity="center_vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="match_parent"
                    android:layout_alignParentLeft="true"
                    android:gravity="center"
                    android:text="@{viewModel.recordTime}"/>

                <ImageView
                    android:layout_width="20dp"
                    android:layout_height="20dp"
                    android:layout_alignParentRight="true"
                    android:layout_centerVertical="true"
                    android:background="@{viewModel.getLikeColor}"
                    />
            </RelativeLayout>
            <TextView
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_below="@id/baby_title"
                android:layout_above="@id/baby_bottom"
                android:text="@{viewModel.recordContent}"/>
        </RelativeLayout>
    </android.support.v7.widget.CardView>
</layout>
```
注意:
		- IDE的智能提示在把根元素改为layout后有部分失效,如果不顺手可以另开一个再复制过来
		- 根元素必须是layout ,绑定数据 的 name 关系到后面在控件中绑定的值,type 则是绑定类的完整类名
		- `variable` 中使用`class` 可以自定义生成的`ViewDataBinding`类名
		- 可以在`data`中使用`import`标签来导入要使用的包,如要写表达式` android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"` 可以在data 中加入`  <import type="android.view.View"/>`
3.	绑定`RecyclerViewAdapter`
使用数据绑定后,写`RecyclerView` 的`adapter`
 就变成了一件很轻松的事情了,只需要使用`DataBindingUtil`来绑定视图就行了,注意要绑定的`layout` 必须是包含`data` 元素的.
 其中的`executePendingBindings` 的作用是当变量的值更新的时候，binding 对象将在下个更新周期中更新。这样就会有一点时间间隔.如果来立刻更新,请看下一个部分
```
public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.BabyHolder> {
    private static final String TAG = "BabyAdapter";
    private List<Baby> mBabies;
    private Context mContext;

    public BabyAdapter(Context context) {
        mContext = context;
    }

    public void setItems(List<Baby> babies){
        mBabies=babies;
        notifyDataSetChanged();
    }


    @Override
    public BabyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BabyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_baby,parent,false));
    }

    @Override
    public void onBindViewHolder(BabyHolder holder, int position) {
        ItemBabyBinding babyBinding=DataBindingUtil.bind(holder.itemView);
        babyBinding.setViewModel(new BabyViewModel(mContext,mBabies.get(position)));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBabies.size();
    }

    public static class BabyHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mBinding;

        public BabyHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return mBinding;
        }
    }
}
```


4.	数据和组件的更新机制
在绑定数据后,数据对象的修改并不会更新UI,但是通过修改通知机制可以实现组件UI的更新.要实现这一功能,就需要数据对象实现以下接口中的一个[ `Observable `,`ObservableFields`,`ObservableList` ].
我们可以通过添加`@Bindable`注释来标定监听数据,但通知数据变化则需要我们来手动调用`notifyPropertyChanged(BR.fieldName)`.
```
public class BabyViewModel extends BaseObservable {
    private static final String TAG = "BabyViewModel";
    private Context mContext;
    private Baby mBaby;

    public BabyViewModel(Context context, Baby baby) {
        mContext = context;
        mBaby = baby;
    }
    @Bindable
    public String getBabyName() {
        return mBaby.getName();
    }
    public View.OnClickListener onClickBaby() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaby.setName("abc");
                notifyPropertyChanged(BR.babyName);
            }
        };
    }
}
```

如果你只有少量属性需要更新,可以使用`ObservableFields` 
```
private static class User extends BaseObservable {
   public final ObservableField<String> firstName =
       new ObservableField<>();
   public final ObservableField<String> lastName =
       new ObservableField<>();
   public final ObservableInt age = new ObservableInt();
}
```