package com.cnlab.caucse.useopencvwithcmake;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cnlab.caucse.useopencvwithcmake.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cameraActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imgMain, imgMain2;
    private Button btnCamera, btnAlbum;
    private int firstwidth, firstheight, secondwidth, secondheight;
    private byte[] firstimg;
    private byte[] secondimg;
    private int count =0;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private String path;
    private AssetManager assetManager;
    InputStream inputStream;

    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java3");
    }
    public native int whatisthis(byte[] firstimg, byte[] secondimg, int firstWidth, int secondWidth, int firstheight, int secondheight, AssetManager assetManager);
    public Uri photoUri;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final int MULTIPLE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        checkPermissions();
        if(checkPermissions()){
            initView();
            takePhoto();
        }

        assetManager = this.getAssets();
        inputStream = null;

    }

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void initView() {
        imgMain = findViewById(R.id.img_test);
        btnCamera = findViewById(R.id.btn_camera);
        btnAlbum = findViewById(R.id.btn_whatisthis);
        imgMain2 = findViewById(R.id.img_test2);
        btnCamera.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(cameraActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(cameraActivity.this,
                    "com.example.leey.findremo.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "nostest_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                goToAlbum();
                break;
            case R.id.btn_whatisthis:
                Bitmap newBit;
                int result = -1;

                if(path.equals("tv")){
                    try {
                        assetManager.open(path+"5.png");
                        newBit = BitmapFactory.decodeStream(inputStream);
                        firstimg = bitmapToByteArray(newBit);
                        result = whatisthis(firstimg, secondimg, newBit.getWidth(), secondwidth, newBit.getHeight(), secondheight, getAssets());
                        if(result == 0) {
                            Intent intent = new Intent(this, smatvRemoteActivity.class);
                            startActivity(intent);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        assetManager.open(path+"1.png");
                        newBit = BitmapFactory.decodeStream(inputStream);
                        firstimg = bitmapToByteArray(newBit);
                        result = whatisthis(firstimg, secondimg, newBit.getWidth(), secondwidth, newBit.getHeight(), secondheight, getAssets());
                        if(result == 0) {
                            Intent intent = new Intent(this, smatvRemoteActivity.class);
                            startActivity(intent);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        assetManager.open(path+"2.png");
                        newBit = BitmapFactory.decodeStream(inputStream);
                        firstimg = bitmapToByteArray(newBit);
                        result = whatisthis(firstimg, secondimg, newBit.getWidth(), secondwidth, newBit.getHeight(), secondheight, getAssets());
                        if(result == 0) {
                            Intent intent = new Intent(this, smatvRemoteActivity.class);
                            startActivity(intent);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        assetManager.open(path+"3.png");
                        newBit = BitmapFactory.decodeStream(inputStream);
                        firstimg = bitmapToByteArray(newBit);
                        result = whatisthis(firstimg, secondimg, newBit.getWidth(), secondwidth, newBit.getHeight(), secondheight, getAssets());
                        if(result == 0) {
                            Intent intent = new Intent(this, smatvRemoteActivity.class);
                            startActivity(intent);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        assetManager.open(path+"4.png");
                        newBit = BitmapFactory.decodeStream(inputStream);
                        firstimg = bitmapToByteArray(newBit);
                        result = whatisthis(firstimg, secondimg, newBit.getWidth(), secondwidth, newBit.getHeight(), secondheight, getAssets());
                        if(result == 0) {
                            Intent intent = new Intent(this, smatvRemoteActivity.class);
                            startActivity(intent);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                return;
            }
            photoUri = data.getData();
            cropImage();
            //cropImage2();
        } else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            // 갤러리에 나타나게
            MediaScannerConnection.scanFile(cameraActivity.this,
                    new String[]{photoUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });

            //cropImage2();
            /*MediaScannerConnection.scanFile(cameraActivity.this,
                    new String[]{photoUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });*/
        } else if (requestCode == CROP_FROM_CAMERA) {
            if(count == 0 ) {
                imgMain.setImageURI(null);
                imgMain.setImageURI(photoUri);
            }
            if(count ==1){
                imgMain2.setImageURI(null);
                imgMain2.setImageURI(photoUri);
            }
            try {
                if(count ==0) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

                    firstwidth = bitmap.getWidth();
                    firstheight = bitmap.getHeight();

                    if(firstwidth > firstheight){
                        path = "tv";
                    }


                    count++;
                }
                if(count ==1) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    secondwidth = bitmap.getWidth();
                    secondheight = bitmap.getHeight();
                    secondimg = bitmapToByteArray(bitmap);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            revokeUriPermission(photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
    public void cropImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();
            intent.putExtra("crop", "true");
            // intent.putExtra("aspectX", 1);
            // intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folder = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());
            photoUri = FileProvider.getUriForFile(cameraActivity.this,
                    "com.example.leey.findremo.provider", tempFile);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            grantUriPermission(res.activityInfo.packageName, photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);

        }
    }
    public void cropImage2() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();
            intent.putExtra("crop", "true");
            // intent.putExtra("aspectX", 1);
            // intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folder = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());
            photoUri = FileProvider.getUriForFile(cameraActivity.this,
                    "com.example.leey.findremo.provider", tempFile);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            grantUriPermission(res.activityInfo.packageName, photoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);

        }
    }
    public byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
