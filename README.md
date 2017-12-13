# R2R2Mvp
项目开发基本架构 rxjava2+retrofit2+mvp

----------


        
   <font size=3 face="黑体"> 上个项目忙完了第一版，在此对上个项目的架构总结下，不断总结分析，才能发现结构的短板，才能搭出属于自己的最完美的架构。</font>

-------------
  <font size=5 face="黑体">1．分包</font>
  
 <font size=3 face="黑体"> ------我的分包如下图：</font>
    
![这里写图片描述](http://img.blog.csdn.net/20171213125451943?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaXNKb2tlcg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

 <font size=3 face="黑体">  adapter：放一些adapter类，属于view层的
        api：放一些网络请求相关的类，如配置retrofit
	    base：放一些activity、fragment、presenter的基类
	    common：放一些全局都会用到的公共类，如application
	    model：我的model层只放了bean类，分为request和response，跟传统mvp的model不同，传统mvp的model层是请求网络数据，然后p层拿到m层和v层的引用，使m层和v层交互，但那样代码量太多了，每一个请求都要创建一个model类，为了简介，我就把请求网络数据直接放在了p层，在后面的代码中你会看到。
	    presenter：放presenter类
	    utils：放一些工具类
	    viewinterface：放view接口类
	    viewimpl：放view实现类
	    widget：放自定义view</font>
       
   <font size=3 face="黑体"> ------因为这个项目不大，界面不多，所以我采用此种分包，如果项目功能模块比较多的话，按功能模块分包比较好，结构清晰。下图是我之前做过的功能模块比较多的项目：</font>
        
   ![这里写图片描述](http://img.blog.csdn.net/20171213125528967?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaXNKb2tlcg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


----------

 <font size=5 face="黑体">2．mvp</font>
		
 <font size=3 face="黑体"> ------自2016年起，mvp框架一度兴起，越来越多的安卓开发者都选择用mvp框架来开发app。mvp结合rxjava和retrofit 简直爽的不要不要的，自从用了mvp结合rxjava和retrofi，代码简洁了，逻辑也清晰了，调用也优雅了，维护成本也低利了。公司的这个项目我是用mvp+rxjava2+retrofit2+butterknife。
		------此mvp框架用到了注解、工厂模式、代理模式来解决代码冗余、内存泄露（P在请求网络数据时，view(Activity或者Fragment)界面销毁，导致P仍然持有view的引用）、presenter生命周期以及数据存储问题；通过泛型将view和presenter绑定，可在view中直接调用getPresenter()获取presenter来请求网络数据，在presenter中可以直接通过getView获取view来更新界面。请求网络用的是封装后的rxjava+retrofit，添加了网络请求和响应拦截器，网络请求和响应json可直接在在Android Studio中的Logcat中一目了然。
		------具体的源码实现我就不一一介绍了，感兴趣的朋友可以下载源码，自行查看；框架源码链接(https://github.com/isJoker/R2R2Mvp)。</font>
		
		
		


----------

 <font size=5 face="黑体">3．使用</font>
	
 <font size=3 face="黑体">首先，先定义一个view接口IMoiveListView</font>
		
```
/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function:
 */
public interface IMoiveListView extends IWanBaseView{

    void onLoading();

    void onLoadSucess(MoiveListResponse moiveListResponse);

    void onLoadFail(String msg);
}
```
 <font size=3 face="黑体">Activity的使用(Fragment的使用类似)，通过注解 @CreatePresenter创建Presenter</font>
		
		

```
/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function:
 */
@CreatePresenter(MoiveListPresenter.class)
public class MainActivity extends AWanBaseActivity<IMoiveListView,MoiveListPresenter>
        implements IMoiveListView {

    @BindView(R.id.tv_moive_name)
    TextView tvMoiveName;
    @BindView(R.id.img_moive)
    ImageView imgMoive;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_main;
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
    public void onLoadSucess(MoiveListResponse     moiveListResponse) {
        MoiveListResponse.TrailersBean trailersBean = moiveListResponse.getTrailers().get(1);
        tvMoiveName.setText(trailersBean.getMovieName());
        Glide
            .with(this)
            .load(trailersBean.getCoverImg())
            .into(imgMoive);
    }

    @Override
    public void onLoadFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

```
 <font size=3 face="黑体">presenter的使用</font>
		
```
/**
 * Created by JokerWan on 2017/12/11.
 * WeChat: wjc398556712
 * Function:
 */

public class MoiveListPresenter extends AWanBasePresenter<IMoiveListView> {

    public void getMoiveList(){

        getView().onLoading();

        wApi.getMoiveList()
                .compose(ApiUtils.getScheduler())
                .subscribe(new ApiSubscriber<MoiveListResponse>() {
                    @Override
                    public void onNext(MoiveListResponse moiveListResponse) {
                        if(moiveListResponse != null) {
                            getView().onLoadSucess(moiveListResponse);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        getView().onLoadFail(t.getMessage());
                    }
                });
    }
}
```


----------

 <font size=5 face="黑体">4．demo运行后的效果</font>
	
![这里写图片描述](http://img.blog.csdn.net/20171213125737412?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaXNKb2tlcg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


