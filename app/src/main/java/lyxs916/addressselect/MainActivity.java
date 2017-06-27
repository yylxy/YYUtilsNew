package lyxs916.addressselect;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lyxs916.demotest.BannerActivity;
import lyxs916.demotest.PhotoCropTest;
import lyxs916.dialogselectaddress.DialogCitySelect;
import lyxs916.guidance_activity.GuidanceActivity;
import lyxs916.java_bean.MainData;
import lyxs916.ui_utils.SnackbarUtils;
import lyxs_916.view_utils.photo.PhotoActivity;

import static lyxs916.guidance_activity.GuidanceActivity.pressListener;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<MainData> mDatas = new ArrayList<>();
    MyAdapter adapter;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivity = this;

        mDatas.add(new MainData(0, "地址选择", getResources().getColor(R.color.blue), ""));
        mDatas.add(new MainData(1, "snackbar 测试", getResources().getColor(R.color.blue), ""));
        mDatas.add(new MainData(2, "引导页", getResources().getColor(R.color.blue), ""));
        mDatas.add(new MainData(3, "图片的缩放", getResources().getColor(R.color.blue), PhotoActivity.class.getName()));
        mDatas.add(new MainData(4, "图片裁剪", getResources().getColor(R.color.blue), PhotoCropTest.class.getName()));
        mDatas.add(new MainData(5, "广告位", getResources().getColor(R.color.blue), BannerActivity.class.getName()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new MyAdapter(mDatas));

        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, final int postion) {

                switch (postion) {
                    //地址选择
                    case 0:
                        DialogCitySelect dialogCitySelect = new DialogCitySelect(MainActivity.this);
                        dialogCitySelect.setCallBack(new DialogCitySelect.CallBack() {
                            @Override
                            public void setData(DialogCitySelect.PCCData data) {
                                SnackbarUtils.showFinishBlue(MainActivity.this, data.province + " - " + data.city + " - " + data.county);
                            }
                        });
                        dialogCitySelect.show();
                        break;

                    //snackbar 测试
                    case 1:
                        SnackbarUtils.showFinishBlue(MainActivity.this, "snackbar测试");
                        break;

                    //引导页
                    case 2:
                        ArrayList<Integer> mimageIds = new ArrayList<>();
                        mimageIds.add(R.mipmap.aa);
                        mimageIds.add(R.mipmap.ab);
                        mimageIds.add(R.mipmap.ac);
                        GuidanceActivity.startUI(MainActivity.this, 0xff399be6, mimageIds, new GuidanceActivity.OnClickListenerCallback() {
                            @Override
                            public void clickCallback(boolean isOnClick) {
                            }
                        });
                        break;
                    case 3://图片的缩放
                        PhotoActivity.starUi(mActivity);
                        break;
                    case 4://照片的裁剪演示
                        PhotoCropTest.starUi(mActivity);
                        break;
                    case 5://广告位的演示
                        BannerActivity.starUi(mActivity);
                        break;
                }
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
                SnackbarUtils.showRed(MainActivity.this, "长按功能没有添加");
            }
        });

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<MainData> mDatas;
        private OnItemClickListener mListener;

        public MyAdapter(ArrayList<MainData> datas) {
            mDatas = datas;
        }

        public void setOnClickListener(OnItemClickListener listener) {
            this.mListener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.itme_main, parent, false));
            return holder;

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.number.setText(mDatas.get(position).getNumber() + "");
            holder.describe.setText(mDatas.get(position).getDescribe());
            holder.itemView.setBackgroundColor(mDatas.get(position).getColor());

            pressListener(holder.itemView);
            //设置item 的监听
            if (mListener != null) {// 如果设置了监听那么它就不为空，然后回调相应的方法
                //点击监听
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        mListener.ItemClickListener(holder.itemView, position);// 把事件交给我们实现的接口那里处理

                    }
                });
//                长按监听
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mListener.ItemLongClickListener(holder.itemView, position);// 把事件交给我们实现的接口那里处理
                        return true;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView number;
            TextView describe;

            public MyViewHolder(View itemView) {
                super(itemView);
                number = (TextView) itemView.findViewById(R.id.number);
                describe = (TextView) itemView.findViewById(R.id.describe);

            }
        }
    }

    /**
     * 定义回调接口
     */
    interface OnItemClickListener {
        void ItemClickListener(View view, int postion);

        void ItemLongClickListener(View view, int postion);
    }

}
