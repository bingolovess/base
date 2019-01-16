package com.bingo.ui.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bingo.ui.R;
import com.bingo.ui.SwitchButton;
import com.bingo.ui.adapter.CommonAdapter;
import com.bingo.ui.adapter.CommonHolder;
import com.bingo.ui.adapter.MultiItemTypeSupport;

import java.util.List;

/**
 * BottomShareSheetDialog
 */
public class BottomShareSheetDialog extends AbsSheetDialog<BottomShareSheetDialog.Bean> {
    private SwithCheckListener checkedChangeListener;

    public BottomShareSheetDialog(Context context, String title, List<Bean> datas) {
        this(context, title, datas, null);
    }

    public BottomShareSheetDialog(Context context, String title, List<Bean> datas, SwithCheckListener l) {
        super(context);
        this.title = title;
        this.datas = datas;
        initView(rootView);
        this.checkedChangeListener = l;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.lib_pub_dialog_bottom_style_share;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new SheetAdapter(context, datas, new MultiItemTypeSupport<Bean>() {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == Bean.TYPE_CONTENT) {
                    return R.layout.lib_pub_adapter_dlg_bottom_share_content;
                } else if (viewType == Bean.TYPE_PASSWORD) {
                    return R.layout.lib_pub_adapter_dlg_bottom_share_psw;
                }
                return R.layout.lib_pub_adapter_dlg_bottom_share_content;
            }

            @Override
            public int getItemViewType(int position, Bean bean) {
                return bean.type;
            }
        });
    }

    @Override
    protected void initView(View rootView) {
        initRecyclerList(rootView, R.id.rv_list, LinearLayoutManager.VERTICAL);

        TextView tvCancle = (TextView) rootView.findViewById(R.id.tv_cancle);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(-1, null);
            }
        });
    }

    public class SheetAdapter extends CommonAdapter<Bean> {

        SheetAdapter(Context context, List<Bean> datas, MultiItemTypeSupport<Bean> multiItemTypeSupport) {
            super(context, datas, multiItemTypeSupport);
        }

        @Override
        public void convert(final int position, CommonHolder holder, final Bean item) {
            if (holder.mLayoutId == R.layout.lib_pub_adapter_dlg_bottom_share_content) {
                holder.setText(R.id.tv_title, item.title);
                holder.setText(R.id.tv_content, item.content);
            } else if (holder.mLayoutId == R.layout.lib_pub_adapter_dlg_bottom_share_psw) {
                holder.setText(R.id.tv_title, item.title);
                holder.setText(R.id.tv_psw, item.password);
                SwitchButton toggleButton = holder.getView(R.id.tbtn_toggle);
                toggleButton.setChecked(item.isChecked);
                if (checkedChangeListener != null) {
                    toggleButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                            checkedChangeListener.onCheckedChanged(isChecked, BottomShareSheetDialog.this);
                        }
                    });
                }
            }
        }
    }

    public static class Bean {
        public final static int TYPE_CONTENT = 0;
        public final static int TYPE_PASSWORD = 1;

        public int type;
        public String title;
        public String content;
        public String password;
        public boolean isChecked;

        public Bean(String title, String content) {
            this.type = TYPE_CONTENT;
            this.title = title;
            this.content = content;
        }

        public Bean(String title, String password, boolean isChecked) {
            this.type = TYPE_PASSWORD;
            this.title = title;
            this.password = password;
            this.isChecked = isChecked;
        }
    }

    public interface SwithCheckListener {
        void onCheckedChanged(boolean checked, BottomShareSheetDialog dialog);
    }
}
