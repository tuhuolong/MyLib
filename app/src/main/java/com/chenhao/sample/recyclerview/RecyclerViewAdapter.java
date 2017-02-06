
package com.chenhao.sample.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.sample.R;
import com.chenhao.sample.app.MainApplication;

import java.util.List;

/**
 * Created by chenhao on 17/2/6.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<ViewData> mDataList;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(MainApplication.getAppContext());
        BaseViewHolder viewHolder = null;

        if (viewType == ViewData.VIEW_TYPE_1) {
            View view = inflater.inflate(R.layout.recycler_view_item_type_1, parent, false);
            viewHolder = new Type1ViewHolder(view);
        } else if (viewType == ViewData.VIEW_TYPE_2) {
            View view = inflater.inflate(R.layout.recycler_view_item_type_2, parent, false);
            viewHolder = new Type2ViewHolder(view);
        }

        if (viewHolder != null) {
            viewHolder.initViews();
            return viewHolder;
        }

        throw new IllegalArgumentException("unexpected view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder != null && mDataList != null && position >= 0 && position < mDataList.size()) {
            holder.onBindViewHolder(this, mDataList.get(position), position);
        }
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
    public int getItemViewType(int position) {
        if (mDataList == null || mDataList.size() <= 0) {
            return super.getItemViewType(position);
        }
        ViewData data = mDataList.get(position);
        return data.mViewType;
    }

    public void updateData(List<ViewData> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }
}
