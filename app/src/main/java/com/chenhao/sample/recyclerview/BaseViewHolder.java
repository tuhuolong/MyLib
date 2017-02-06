
package com.chenhao.sample.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenhao on 17/2/6.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void initViews() {
    }

    public void onBindViewHolder(RecyclerViewAdapter adapter, ViewData viewData, int position) {
    }
}
