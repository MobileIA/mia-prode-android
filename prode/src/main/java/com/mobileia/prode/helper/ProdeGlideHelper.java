package com.mobileia.prode.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;
import com.mobileia.prode.rest.UnsafeHttpClientProde;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by matiascamiletti on 9/2/18.
 */

@GlideModule
public class ProdeGlideHelper extends LibraryGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //Unsafe Okhttp client
        OkHttpClient okHttpClient= UnsafeHttpClientProde.getUnsafeOkHttpClient();

        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }
}
