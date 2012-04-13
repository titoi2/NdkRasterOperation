package jp.kobegtug.codesprint.ndkrasteroperation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class NdkRasterOperationActivity extends Activity {
    private static final String TAG = "NdkRasterOperationActivity";
    private ImageView mImageViewSRC;
    private ImageView mImageViewMASK;
    private ImageView mImageViewRESULT;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int pixels[] = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        final Bitmap.Config c = Bitmap.Config.ARGB_8888;
        Bitmap mask = Bitmap.createBitmap(width, height, c);
        Bitmap result = Bitmap.createBitmap(width, height, c);

        Canvas canvas = new Canvas();
        canvas.setBitmap(mask);
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.FILL_AND_STROKE);
        canvas.drawCircle(width / 2, height / 2, width / 3, paint);

        int maskpixels[] = new int[width * height];
        mask.getPixels(maskpixels, 0, width, 0, 0, width, height);

        raster(pixels, maskpixels, width, height, 1);   // 1:AND

        result.setPixels(pixels, 0, width, 0, 0, width, height);

        mImageViewSRC = (ImageView) findViewById(R.id.imageviewSRC);
        mImageViewMASK = (ImageView) findViewById(R.id.imageviewMASK);
        mImageViewRESULT = (ImageView) findViewById(R.id.imageviewRESULT);
        mImageViewSRC.setImageBitmap(bmp);
        mImageViewMASK.setImageBitmap(mask);
        mImageViewRESULT.setImageBitmap(result);
    }

    public native int raster(int[] src, int[] dst, int width, int height,
            int ope);

    static {
        System.loadLibrary("raster");
    }
}
