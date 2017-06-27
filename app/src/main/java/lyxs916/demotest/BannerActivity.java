package lyxs916.demotest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.banner.banner.ConvenientBanner;
import com.example.banner.banner.NetworkImageHolderView;
import com.example.banner.banner.data.BannerAdData;
import com.example.banner.banner.holder.CBViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lyxs916.addressselect.R;
import lyxs916.ui_utils.SnackbarUtils;


/**
 * 说明:广告位的演示
 * 作者： 阳2012; 创建于：  2017-05-27  17:42
 */
public class BannerActivity extends Activity implements CBViewHolderCreator {
    @BindView(R.id.advertisement)
    ConvenientBanner advertisement;
    List<BannerAdData> advertisementDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        setBanner();

    }

    private void setBanner() {

        advertisementDatas.add(new BannerAdData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843394623&di=d2e0b3dea2530d2b79e71f1ed8a8052e&imgtype=0&src=http%3A%2F%2Fphoto.enterdesk.com%2F2012-3-24%2Fenterdesk.com-596F0AE83E5C7B99617C4343D6EB8199.jpg"));
        advertisementDatas.add(new BannerAdData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487842980354&di=0108918d74eb2df4de3281b39de9763e&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F74%2F96%2F41658PICP7t_1024.jpg"));
        advertisementDatas.add(new BannerAdData("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3901634069,2243065451&fm=23&gp=0.jpg"));

        advertisement.setPages(this, advertisementDatas);
        advertisement.startTurning(1000);

    }

    @Override
    public Object createHolder() {
        return new NetworkImageHolderView() {
            @Override
            public void onItemClicklistener(View item, int position, BannerAdData data) {
                SnackbarUtils.showBlue(BannerActivity.this, "position=" + position);
            }
        };
    }


}
