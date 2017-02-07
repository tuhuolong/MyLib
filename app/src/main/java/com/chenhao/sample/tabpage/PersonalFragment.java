
package com.chenhao.sample.tabpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.sample.R;

import app.lib.commonui.widget.TitleBar;

/**
 * Created by chenhao on 17/2/7.
 */

public class PersonalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_personal, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TitleBar titleBar = (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.setupTitleText("Personal");
    }
}
