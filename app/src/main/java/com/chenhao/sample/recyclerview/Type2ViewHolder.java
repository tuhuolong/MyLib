
package com.chenhao.sample.recyclerview;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhao.sample.R;
import com.chenhao.sample.app.MainApplication;

/**
 * Created by chenhao on 17/2/6.
 */

public class Type2ViewHolder extends BaseViewHolder {

    TextView mTitle;
    ImageView mNext;

    public Type2ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void initViews() {
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mNext = (ImageView) itemView.findViewById(R.id.next);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter adapter, ViewData viewData, int position) {
        if (!(viewData instanceof Type2ViewData)) {
            return;
        }

        final Type2ViewData data = (Type2ViewData) viewData;
        mTitle.setText(data.title);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(data.next));
                MainApplication.getAppContext().startActivity(intent);
            }
        });
    }
}
