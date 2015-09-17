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

    protected Baby(Parcel in) {
        name = in.readString();
        age = in.readInt();
        note = in.readString();
        recordTime=new Date(in.readLong());
        isLike=in.readInt()==0;
        mSex=SexType.values()[in.readInt()];
    }

    public static final Creator<Baby> CREATOR = new Creator<Baby>() {
        @Override
        public Baby createFromParcel(Parcel in) {
            return new Baby(in);
        }

        @Override
        public Baby[] newArray(int size) {
            return new Baby[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(note);
        dest.writeLong(recordTime.getTime());
        dest.writeInt(isLike ? 0 : 1);
        dest.writeInt(mSex.ordinal());
    }

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

    public Baby() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public SexType getSex() {
        return mSex;
    }

    public void setSex(SexType sex) {
        mSex = sex;
    }
}
 ```
 和与视图相关的视图模型`BabyViewModel`(简单的可以省略,直接和模型绑定视图就行,这里建立是为了方便的转化原模型中多样的数据类型)
 ```
 public class BabyViewModel extends BaseObservable {
    private static final String TAG = "BabyViewModel";
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

    public int getBabySexIcon() {
        return mBaby.getSex().compareTo(Baby.SexType.BOY) == 0 ? R.mipmap.sex_boy : R.mipmap.sex_girl;
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

    public int getLikeColor(){
        return mBaby.isLike()? Color.RED:Color.TRANSPARENT;
    }
}
 
 ```
2.	绑定`layout`
3.	绑定`RecyclerViewAdapter`
4.	在`Activity`中使用