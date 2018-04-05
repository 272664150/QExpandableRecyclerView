package com.example.qexpandablerecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qexpandablerecyclerview.R;
import com.example.qexpandablerecyclerview.bean.ChildBean;
import com.example.qexpandablerecyclerview.bean.GroupBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String FLAG_PART_UPDATE = "part_update"; //实现局部刷新需要传非null参数，无特殊含义
    private static final int STATUS_EXPAND = 1; //已展开状态

    private List<Object> mDataList;
    private int mRecentlyClickedGroupItemIndex = -1; //最近点击的groupItem下标，-1代表没有任何点击操作

    private enum ViewType {
        GROUP(0), //一级item
        CHILD(1); //二级item

        private int value;

        ViewType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public RecyclerViewAdapter(List<GroupBean> groupList) {
        if (groupList == null) {
            return;
        }

        mDataList = new ArrayList<>();
        for (GroupBean groupBean : groupList) {
            mDataList.add(groupBean); //即使没有child数据，groupItem也显示，只是不能展开
            if (groupBean.getChildList() != null && groupBean.getChildList().size() > 0) {
                mDataList.addAll(groupBean.getChildList());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == ViewType.GROUP.getValue()) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
            return new GroupViewHolder(itemView);
        } else if (viewType == ViewType.CHILD.getValue()) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
            return new ChildViewHolder(itemView);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mDataList.get(position) instanceof GroupBean) {
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.itemView.setTag(position);
            //由于groupItem默认展开，所以第一次加载时，初始化Tag信息
            if (groupViewHolder.itemView.getTag(R.id.tag_expand_status) == null) {
                groupViewHolder.itemView.setTag(R.id.tag_expand_status, STATUS_EXPAND);
            }
            groupViewHolder.mTitleTv.setText(((GroupBean) mDataList.get(position)).getTitle());
            groupViewHolder.mDescTv.setText(((GroupBean) mDataList.get(position)).getDesc());
            //若没有child数据，隐藏groupItem的arrow图标
            if (((GroupBean) mDataList.get(position)).getChildList().size() <= 0) {
                groupViewHolder.mArrowIv.setVisibility(View.INVISIBLE);
            } else {
                groupViewHolder.mArrowIv.setVisibility(View.VISIBLE);
            }
        } else if (mDataList.get(position) instanceof ChildBean) {
            ChildViewHolder childViewHolder = (ChildViewHolder) holder;
            childViewHolder.itemView.setTag(position);
            childViewHolder.mTitleTv.setText(((ChildBean) mDataList.get(position)).getTitle());
            childViewHolder.mDescTv.setText(((ChildBean) mDataList.get(position)).getDesc());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            String payload = (String) payloads.get(0);
            if (payload.equals(FLAG_PART_UPDATE) && getItemViewType(position) == ViewType.GROUP.getValue()) {
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                groupViewHolder.itemView.setTag(position);

                //屏蔽在刷新groupItem时，将前面的groupItem也刷新的问题
                if (mRecentlyClickedGroupItemIndex != position) {
                    return;
                }

                if (Integer.valueOf(groupViewHolder.itemView.getTag(R.id.tag_expand_status).toString()) == STATUS_EXPAND) {
                    groupViewHolder.itemView.setTag(R.id.tag_expand_status, ~STATUS_EXPAND);
                    groupViewHolder.mArrowIv.setBackgroundResource(R.drawable.arrow_down);
                } else {
                    groupViewHolder.itemView.setTag(R.id.tag_expand_status, STATUS_EXPAND);
                    groupViewHolder.mArrowIv.setBackgroundResource(R.drawable.arrow_up);
                }
            } else if (payload.equals(FLAG_PART_UPDATE) && getItemViewType(position) == ViewType.CHILD.getValue()) {
                ChildViewHolder childViewHolder = (ChildViewHolder) holder;
                childViewHolder.itemView.setTag(position);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof GroupBean) {
            return ViewType.GROUP.getValue();
        } else if (mDataList.get(position) instanceof ChildBean) {
            return ViewType.CHILD.getValue();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        } else {
            return mDataList.size();
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (getItemViewType(position) == ViewType.GROUP.getValue()) {
            mRecentlyClickedGroupItemIndex = position;

            //若没有child数据，屏蔽groupItem的click事件
            if (((GroupBean) mDataList.get(position)).getChildList().size() <= 0) {
                return;
            }

            if (Integer.valueOf(v.getTag(R.id.tag_expand_status).toString()) == STATUS_EXPAND) {
                narrow(position);
            } else {
                expand(position);
            }
        } else if (getItemViewType(position) == ViewType.CHILD.getValue()) {
            Toast.makeText(v.getContext(), "child position: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 展开，局部刷新
     *
     * @param position
     */
    private void expand(int position) {
        mDataList.addAll(position + 1, ((GroupBean) mDataList.get(position)).getChildList());
        notifyItemRangeInserted(position + 1, ((GroupBean) mDataList.get(position)).getChildList().size());
        notifyItemRangeChanged(0, getItemCount(), FLAG_PART_UPDATE);
    }

    /**
     * 收起，局部刷新
     *
     * @param position
     */
    private void narrow(int position) {
        mDataList.removeAll(((GroupBean) mDataList.get(position)).getChildList());
        notifyItemRangeRemoved(position + 1, ((GroupBean) mDataList.get(position)).getChildList().size());
        notifyItemRangeChanged(0, getItemCount(), FLAG_PART_UPDATE);
    }

    private class GroupViewHolder extends BaseViewHolder {
        private ImageView mArrowIv;

        public GroupViewHolder(View view) {
            super(view);
            mArrowIv = view.findViewById(R.id.arrow_iv);
        }
    }

    private class ChildViewHolder extends BaseViewHolder {
        public ChildViewHolder(View view) {
            super(view);
        }
    }

    private abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTitleTv;
        protected TextView mDescTv;

        public BaseViewHolder(View view) {
            super(view);
            view.setOnClickListener(RecyclerViewAdapter.this);
            mTitleTv = view.findViewById(R.id.title_tv);
            mDescTv = view.findViewById(R.id.desc_tv);
        }
    }
}
