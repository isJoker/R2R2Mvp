package com.jokerwan.mvpretrofitrxdemo.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jokerwan.mvpretrofitrxdemo.base.factory.IWanPresenterFactory;
import com.jokerwan.mvpretrofitrxdemo.base.factory.WanPresenterFactory;
import com.jokerwan.mvpretrofitrxdemo.base.proxy.IPresenterProxy;
import com.jokerwan.mvpretrofitrxdemo.base.proxy.PresenterProxy;

import butterknife.ButterKnife;

/**
 * Created by JokerWan on 2017/12/13.
 * WeChat: wjc398556712
 * Function:
 */

public abstract class AWanBaseFragment<V extends IWanBaseView, P extends AWanBasePresenter<V>> extends Fragment implements IPresenterProxy<V,P> {

    private static final String PRESENTER_SAVE_KEY_FRAGMENT = "presenter_save_key_fragment";
    //创建被代理对象,传入默认Presenter的工厂
    private PresenterProxy<V,P> mProxy = new PresenterProxy<>(WanPresenterFactory.<V,P>createFactory(getClass()));


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getViewLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 获取子类布局id
     * @return
     */
    public abstract int getViewLayoutId();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }
    /**
     * 初始化数据
     */
    public abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        mProxy.onCreate((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY_FRAGMENT,mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(IWanPresenterFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public IWanPresenterFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getPresenter() {
        return mProxy.getPresenter();
    }
}
