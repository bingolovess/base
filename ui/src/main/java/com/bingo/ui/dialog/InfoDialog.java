package com.bingo.ui.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.bingo.ui.R;
import com.bingo.ui.adapter.CommonAdapter;
import com.bingo.ui.adapter.CommonHolder;
import java.util.List;

/**
 * InfoDialog
 */
public class InfoDialog extends AbsSheetDialog<InfoDialog.Bean> {

    public InfoDialog(Context context, String title, List<Bean> datas) {
        super(context, R.style.lib_pub_dialog_style, false, 0, 0, 0);
        this.title = title;
        this.datas = datas;
        initView(rootView);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.lib_pub_dialog_info;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new SheetAdapter(context, datas, R.layout.lib_pub_adapter_dlg_info);
    }

    @Override
    protected void initView(View rootView) {
        initRecyclerList(rootView, R.id.rv_list, LinearLayoutManager.VERTICAL);

        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public class SheetAdapter extends CommonAdapter<Bean> {
        SheetAdapter(Context context, List<Bean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(final int position, CommonHolder holder, final Bean item) {
            holder.setText(R.id.tv_title, item.title);
            holder.setText(R.id.tv_content, item.content);
        }
    }

    public static class Bean {
        public String title;
        public String content;

        public Bean(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
