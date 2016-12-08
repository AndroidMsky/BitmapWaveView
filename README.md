# BitmapWaveView
在bitmap中显示进度波浪
转载请注明作者AndroidMsky和出处 
http://blog.csdn.net/AndroidMsky/article/details/53520406

觉得不错给个star，谢谢父老乡亲了

![这里写图片描述](http://img.blog.csdn.net/20161208155819269?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvQW5kcm9pZE1za3k=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
可切换波澜4种状态：

![这里写图片描述](http://img.blog.csdn.net/20161208160012741?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvQW5kcm9pZE1za3k=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


##使用方法：
考入BitmapWave还是一个200行的小鬼：
[BitmapWave.java](https://github.com/AndroidMsky/BitmapWaveView/blob/master/app/src/main/java/com/example/liangmutian/bitmapwaveview/BitmapWave.java)

两个自定义属性：

```
<declare-styleable name="bitmapWave">
        <attr name="backbitmap" format="reference"/>
        <attr name="overColor" format="color"/>

    </declare-styleable>
```

并在布局中使用：

```
<com.example.liangmutian.bitmapwaveview.BitmapWave
        android:id="@+id/bitmapwave2"
        bitmapwave:backbitmap="@mipmap/q1"
        bitmapwave:overColor="#eddf0e"
        android:layout_width="100dp"
        android:layout_height="250dp"
        />
```

支持修改的属性：

| 属性名称        | 描述          | 备注  |
| ------------- |:-------------:| -----:|
|Bitmap bitmap    | 背景图 |  |
|private int mWaveLength = 700;    | 浪宽     |   |
|private int progerss = 50; | 浪总高     |   |
|private int mWaveHeight = 80; | 浪振幅高     |    |
|private float waveBit = 1 / 4f; | 左右浪比例     |  默认1:1 |
|private int mWavePaintColor;| 浪颜色   | |

方法支持：

```
mBitmapWave.setMode(1);
```

| 值        | 描述          | 备注  |
| ------------- |:-------------:| -----:|
|0    | 基本波浪 |  |
|1   | 重叠部分消失     |   可以表示反向加载 |
|2 | 浸泡感觉     |   |
|3 | 红色覆盖    | 波浪透明切颜色加深   |

```
改变颜色
   public void setColor(int c) {
        mWavePaint.setColor(c);
    }
改变进度
    public void setProgerss(int c) {
        this.progerss = c;
    }

```
