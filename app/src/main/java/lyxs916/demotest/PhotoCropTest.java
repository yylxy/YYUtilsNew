package lyxs916.demotest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;

import lyxs916.addressselect.R;


/**
 * 说明:照片的裁剪演示
 * 作者： 阳2012; 创建于：  2017-05-27  17:42
 */
public class PhotoCropTest extends Activity {
    private ImageView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phott_crop);
        resultView = (ImageView) findViewById(R.id.result_image);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.setImageDrawable(null);

                Crop.pickImage(PhotoCropTest.this);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        //第一种方法传一个uri
        Crop.of(source, destination).asSquare().start(this);

        //第二种方法传一个路径
//        Crop.of(this, "/storage/emulated/0/image/Android_avatar_20170518102043jLars.png").asSquare().start(this);

//        UriUtils.getImageContentUri();
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
