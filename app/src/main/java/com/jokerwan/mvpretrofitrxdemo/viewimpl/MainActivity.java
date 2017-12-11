package com.jokerwan.mvpretrofitrxdemo.viewimpl;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jokerwan.mvpretrofitrxdemo.R;
import com.jokerwan.mvpretrofitrxdemo.base.AWanBaseActivity;
import com.jokerwan.mvpretrofitrxdemo.base.CreatePresenter;
import com.jokerwan.mvpretrofitrxdemo.model.MoiveListResponse;
import com.jokerwan.mvpretrofitrxdemo.presenter.MoiveListPresenter;
import com.jokerwan.mvpretrofitrxdemo.view.IMoiveListView;

import butterknife.BindView;

@CreatePresenter(MoiveListPresenter.class)
public class MainActivity extends AWanBaseActivity<IMoiveListView,MoiveListPresenter> implements IMoiveListView {

    @BindView(R.id.tv_moive_name)
    TextView tvMoiveName;
    @BindView(R.id.img_moive)
    ImageView imgMoive;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            // TODO: 2017/12/11 数据恢复
        }

        getPresenter().getMoiveList();

    }

    @Override
    public void onLoading() {
        tvMoiveName.setText("数据加载中，请稍后...");
    }

    @Override
    public void onLoadSucess(MoiveListResponse moiveListResponse) {
        MoiveListResponse.TrailersBean trailersBean = moiveListResponse.getTrailers().get(1);
        tvMoiveName.setText(trailersBean.getMovieName());
        Glide.with(this).load(trailersBean.getCoverImg()).into(imgMoive);
    }

    @Override
    public void onLoadFail(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
