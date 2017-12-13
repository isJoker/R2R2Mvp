package com.jokerwan.mvpretrofitrxdemo.base.proxy;

import android.os.Bundle;

import com.jokerwan.mvpretrofitrxdemo.base.AWanBasePresenter;
import com.jokerwan.mvpretrofitrxdemo.base.IWanBaseView;
import com.jokerwan.mvpretrofitrxdemo.base.factory.IWanPresenterFactory;

/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function: 代理实现类，用来管理Presenter的生命周期，还有和view之间的关联
 */

public class PresenterProxy<V extends IWanBaseView, P extends AWanBasePresenter<V>> implements IPresenterProxy<V,P> {

    /**
     * 获取onSaveInstanceState中bundle的key
     */
    private static final String PRESENTER_KEY = "presenter_key";
    /**
     * Presenter工厂类
     */
    private IWanPresenterFactory<V, P> mFactory;
    private P mPresenter;
    private Bundle mBundle;
    private boolean mIsBindView;

    public PresenterProxy(IWanPresenterFactory<V, P> mFactory) {
        this.mFactory = mFactory;
    }

    /**
     * 设置Presenter的工厂类,这个方法只能在创建Presenter之前调用,也就是调用getPresenter()之前，如果Presenter已经创建则不能再修改
     * @param presenterFactory IWanPresenterFactory类型
     */
    @Override
    public void setPresenterFactory(IWanPresenterFactory<V, P> presenterFactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("setPresenterFactory() can only be called before getPresenter() be called");
        }
        this.mFactory = presenterFactory;
    }

    /**
     * 获取Presenter工厂类
     * @return
     */
    @Override
    public IWanPresenterFactory<V, P> getPresenterFactory() {
        return mFactory;
    }

    @Override
    public P getPresenter() {
        if(mFactory != null) {
            if(mPresenter == null) {
                mPresenter = mFactory.createPresenter();
            }
        }
        return mPresenter;
    }

    /**
     * 绑定Presenter和view
     * @param mvpView
     */
    public void onCreate(V mvpView) {
        getPresenter();
        if (mPresenter != null && !mIsBindView) {
            mPresenter.onBindView(mvpView);
            mIsBindView = true;
        }
    }

    /**
     * 销毁Presenter持有的View
     */
    private void onUnbindView() {
        if (mPresenter != null && mIsBindView) {
            mPresenter.onUnbindView();
            mIsBindView = false;
        }
    }

    /**
     * 销毁Presenter
     */
    public void onDestroy() {
        if (mPresenter != null ) {
            onUnbindView();
            mPresenter.onDestroyPersenter();
            mPresenter = null;
        }
    }

    /**
     * 意外销毁的时候调用
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getPresenter();
        if(mPresenter != null){
            Bundle presenterBundle = new Bundle();
            //回调Presenter
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY,presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复Presenter
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }

}
