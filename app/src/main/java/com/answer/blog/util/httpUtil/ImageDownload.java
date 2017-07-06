package com.answer.blog.util.httpUtil;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.answer.blog.view.MainActivity;

/**
 * Created by Answer on 2017/7/6.
 */

public class ImageDownload {

    /**
     *
     * 下载图片的方法：
     *  ImageLoader.ImageListener listener = ImageLoader.getImageListener(avatar,android.R.drawable.sym_def_app_icon,android.R.drawable.sym_def_app_icon);
        ImageDownload.imageLoader.get(url,listener);
     *
     *
     */

    public static ImageLoader imageLoader = new ImageLoader(MainActivity.mQueue, new ImageLoader.ImageCache() {
        @Override
        public Bitmap getBitmap(String url) {
            return null;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {

        }
    });
}
