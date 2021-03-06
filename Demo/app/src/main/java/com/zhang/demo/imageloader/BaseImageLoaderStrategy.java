package com.zhang.demo.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * 图片加载接口
 * Created by admin on 2017/7/25.
 */

public interface BaseImageLoaderStrategy {

    void loadImage(Context context, String url, ImageView imageView);

//    //这里的context指定为 ApplicationContext
//    void loadImageWithAppCxt(String url, ImageView imageView);
//    void loadImage(String url, int placeholder, ImageView imageView);
//
//    void loadImage(Context context, String url, int placeholder, ImageView imageView);
//
//    void loadCircleImage(String url, int placeholder, ImageView imageView);
//
//    void loadCircleBorderImage(String url, int placeholder, ImageView imageView,float borderWidth, int borderColor);
//
//    void loadCircleBorderImage(String url, int placeholder, ImageView imageView,float borderWidth, int borderColor, int heightPx, int widthPx);
//
//    void loadGifImage(String url, int placeholder, ImageView imageView);
//
//    void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener);
//
//    void loadImageWithPrepareCall(String url, ImageView imageView, int placeholder, SourceReadyListener listener);
//
//    void loadGifWithPrepareCall(String url, ImageView imageView, SourceReadyListener listener);
//
//    //清除硬盘缓存
//    void clearImageDiskCache(final Context context);
//
//    //清除内存缓存
//    void clearImageMemoryCache(Context context);
//
//    //根据不同的内存状态，来响应不同的内存释放策略
//    void trimMemory(Context context, int level);
//
//    //获取缓存大小
//    String getCacheSize(Context context);
//
//    void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener);

}
