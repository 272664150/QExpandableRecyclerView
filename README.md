# QExpandableRecyclerView
支持空态显示和可展开二级列表的RecyclerView<br>
![可展开二级列表](https://github.com/272664150/QExpandableRecyclerView/blob//master/screenshots/20180406014959.png)    ![空态显示](https://github.com/272664150/QExpandableRecyclerView/blob/master/screenshots/20180406015027.png)

可展开二级列表：
-----
1.List\<Object\>
##
    用Object类型是为了把不同数据都装进一个list中。将传入的数据和显示的数据分开，维护一个显示数据列表，展开就添加item，收起就移
    除item，这样添加和移除可以利用RecyclerView的默认动画。

2.局部控件刷新
##
        onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads)
        notifyItemRangeChanged(int positionStart, int itemCount, Object payload)
    如果payload传一个不为null的参数，就可以实现对列表项中的具体控件更新。在RecyclerView.Adapter中,使用像notifyItemChanged
    带payload参数的方法，以及重写带payload参数的onBindViewHolder方法，在onBindViewHolder中刷新需要更新的控件即可。

空态显示：
-----
    1.创建一个继承RecyclerView的类。
    2.通过getRootView().addView(emptyView)将空数据时显示的View添加到当前View的层次结构中。
    3.通过AdapterDataObserver监听RecyclerView的数据变化，如果adapter为空，那么隐藏RecyclerView，显示EmptyView。

用法一：
##
    View inflate = LayoutInflater.from(this).inflate(R.layout.view_empty_status, null, false); //root为null
    recyclerView.setEmptyView(inflate);
    recyclerView.setAdapter(...); //可以不setAdapter，与setEmptyView无次序关系

用法二：
##
    View inflate = View.inflate(this, R.layout.view_empty_status, null); //root为null
    recyclerView.setEmptyView(inflate);
    recyclerView.setAdapter(...); //可以不setAdapter，与setEmptyView无次序关系

用法三：
##
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        tools:context=".EmptyShowThreeActivity">

        <com.example.qexpandablerecyclerview.view.QRecyclerViewEmptySupport
            android:id="@+id/empty_and_expandable_rv"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

        <include
            android:id="@+id/empty_status"
            layout="@layout/view_empty_status" />
    </LinearLayout>

    recyclerView.setEmptyView(findViewById(R.id.empty_status));
    recyclerView.setAdapter(...); //可以不setAdapter，与setEmptyView无次序关系
