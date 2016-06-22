package cn.nbhope.imageproxylib.proxy;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import cn.nbhope.imageproxylib.abs.ICreate;
import cn.nbhope.imageproxylib.abs.ImageProxy;

/**
 * @Description Picasso代理
 * Created by EthanCo on 2016/6/23.
 */
public class PicassoProxy extends ImageProxy<Picasso> {

    private PicassoProxy() {
    }

    private static class SingleTonHolder {
        private static PicassoProxy sInstance = new PicassoProxy();
    }

    public static PicassoProxy getInstace() {
        return SingleTonHolder.sInstance;
    }

    @Override
    public ICreate load(String url) {
        RequestCreator creator = proxy.load(url);
        return new Creator(creator);
    }

    @Override
    public PicassoProxy with(Context context) {
        Picasso proxy = Picasso.with(context);
        SingleTonHolder.sInstance.setProxy(proxy);
        return SingleTonHolder.sInstance;
    }

    @Override
    public PicassoProxy with(Activity activity) {
        proxy = Picasso.with(activity);
        return SingleTonHolder.sInstance;
    }

    @Override
    public PicassoProxy with(Fragment fragment) {
        proxy = Picasso.with(fragment.getActivity());
        return SingleTonHolder.sInstance;
    }

    @Override
    public PicassoProxy with(android.app.Fragment fragment) {
        proxy = Picasso.with(fragment.getActivity());
        return SingleTonHolder.sInstance;
    }

    public static class Creator implements ICreate {
        private final RequestCreator creator;

        public Creator(RequestCreator creator) {
            this.creator = creator;
        }

        @Override
        public Creator crossFade() {
            creator.noFade();
            return this;
        }

        @Override
        public void into(ImageView view) {
            creator.into(view);
        }
    }
}
