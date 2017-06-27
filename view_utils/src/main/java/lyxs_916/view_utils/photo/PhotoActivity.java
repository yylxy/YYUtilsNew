package lyxs_916.view_utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import lyxs_916.view_utils.R;


/**
 * 说明:图片的缩放
 * 作者： 杨阳; 创建于：  2017-06-27  16:45
 */
public class PhotoActivity extends Activity {
    public static void starUi(Activity context) {
        Intent intent = new Intent(context, PhotoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photot);



    }
}
