# Base库

`bingo`
个人基础组件库

---

###  一、dialog

![dialog](D:\as_ws\Base\img\dialog.gif)

```
 //ActionSheet
 new ActionSheetDialog(mContext)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
                .addSheetItem("清空消息列表", Color.parseColor(DEF_RED),
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("clear msg list");
                            }
                        })
                .setCancelText("取 消")
                .show();
  //Dialog              
     new AlertDialog(mContext)
                .setCancelable(false)
                .setTitle("退出当前账号")
                .setMessage("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                .setLeftButton("取消", null)
                .setRightButton("确认退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("exit");
                    }
                })
                .show();   
        //修改宽度        
       new AlertDialog(mContext)
                .setCancelable(true)
                .setScaleWidth(0.7)// 设置宽度，占屏幕宽度百分比
                .setMessage("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
                .setRightButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("OK");
                    }
                })
                .show();              
      //EditText
       new AlertEditDialog(mContext)
                .setTitle("姓名")
                .setMessage("请输入您的真实姓名。")
                .setLeftButton("取消", null)
                .setRightButton("确定", new AlertEditDialog.EditTextCallListener() {
                    @Override
                    public void callBack(String str) {
                        showToast(str);
                    }
                })
                .show();
       //wheel
      String[] numbers = new String[]{"北京", "上海", "天津", "杭州", "苏州", "深圳"};
      String defValue = numbers[index];
        new AlertNumberPickerDialog(mContext)
                .setScaleWidth(0.8)
                .setCancelable(false)
                .setTitle("选择城市")
                .setNumberValues(numbers, index, new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        defValue = numbers[newVal];
                    }
                })
                .setNumberValueSuffix("市")
                .setLeftButton("取消", null)
                .setRightButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(defValue);
                    }
                })
                .show();
```

#### 二、SmartTabLayout



```
布局中使用
	<com.lehand.tablayout.smarttab.SmartTabLayout
        android:id="@+id/viewpagertab"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:background="@color/colorPrimary"
        app:stl_defaultTabTextColor="@color/white"
        app:stl_defaultTabTextHorizontalPadding="24dp"
        app:stl_indicatorColor="@color/colorAccent"
        app:stl_indicatorInterpolation="linear"
        app:stl_titleOffset="auto_center"
        app:stl_indicatorThickness="3dp"
        app:stl_underlineThickness="1dp" />
        
     <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:visibility="visible"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />   
        
代码中使用
       SmartTabLayout mSmartTabLayout = findViewById(R.id.viewpagertab);
       mSmartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
               @Override
               public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
               	//item 的布局
                    View icon =  LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab_icon1, container,
                            false);
                    return icon;
                }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<String> titles = new ArrayList<>();
        titles.add("Item 1");
        titles.add("Item 2");
        titles.add("Item 3");
        titles.add("Item 4");
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (String title : titles) {
            pages.add(FragmentPagerItem.of(title, DemoFragment.class));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        mSmartTabLayout.setViewPager(viewPager);
```

#### 三、notify

~~~
 NotifyUtil.buildProgress(102,android.R.drawable.stat_notify_more,"正在下载"+title,progress,100,"下载进度:%dkb/%dkb").show()
~~~

#### 四、项目归档



