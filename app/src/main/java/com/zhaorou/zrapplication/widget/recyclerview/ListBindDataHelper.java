package com.zhaorou.zrapplication.widget.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseModel;
import com.zhaorou.zrapplication.network.imp.HttpDialogLoading;
import com.zhaorou.zrapplication.network.retrofit.AbsZCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


public class ListBindDataHelper<T extends BaseModel, D> {

    private static final int STATE_INIT = 0x000; //初始化
    private static final int STATE_OK = 0x004;//加载完成
    private static final int STATE_LOADING_MORE = 0x006; //加载更多
    private static final int STATE_LOADING_RE_FRESH = 0x007; //刷新
    private static final int STATE_LOADING_NO_MORE = 0x008; //没有更多数据了
    private static final String TAG = "ListBindDataHelper";
    private int mState = STATE_INIT;
    private RecyclerView recyclerView;
    private boolean isLoadMore = true;
    private List<D> tempData;
    private Map<String, Object> mMap = new HashMap<>();
    private TextView loadMoreView;
    private CommonRecycleViewAdapter<D> adapter;

    private int mAdapterLayoutId;
    private Activity mActivity;
    private ListBindDataInterface<T, D> listBindDataInterface;

    private boolean isPostIng = false;//是否在请求中


    private static final String mPage = "page";
    private static final String mPagesize = "pageSize";

    private HttpDialogLoading httpDialogLoading;
    private ListRefreshListener listRefreshListener;

    /*
        public ListBindDataHelper(Activity activity, int adapterLayoutId, ListBindDataInterface<T, D> listBindDataInterface) {

            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        //    recyclerView.setOnRefreshListener(this);
            //recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));

            init(activity, recyclerView, adapterLayoutId, listBindDataInterface);
        }
    */
    public ListBindDataHelper(Activity activity, RecyclerView recyclerView, int adapterLayoutId,
                              ListBindDataInterface<T, D> listBindDataInterface) {
        init(activity, recyclerView, adapterLayoutId, listBindDataInterface, null);
    }

    /**
     * 为了适配动态添加 recyclerView
     *
     * @param activity              activity
     * @param recyclerView          recyclerView
     * @param adapterLayoutId       布局id
     * @param listBindDataInterface 接口
     */
    public ListBindDataHelper(Activity activity, RecyclerView recyclerView, int adapterLayoutId,
                              ListBindDataInterface<T, D> listBindDataInterface, HttpDialogLoading httpDialogLoading) {
        init(activity, recyclerView, adapterLayoutId, listBindDataInterface, httpDialogLoading);
    }


    private void init(Activity activity, RecyclerView recyclerView, int mAdapterLayoutId,
                      ListBindDataInterface<T, D> listBindDataInterface, HttpDialogLoading httpDialogLoading) {

        this.httpDialogLoading = httpDialogLoading;
        this.mAdapterLayoutId = mAdapterLayoutId;
        this.mActivity = activity;
        this.listBindDataInterface = listBindDataInterface;
        this.recyclerView = recyclerView;


        //内置二个基础参数，可以被覆盖
        mMap.put(mPage, 1);
        mMap.put(mPagesize, CommonRecycleViewAdapter.DATA_SIZE_STRING);

    }

    public void startPostData(Call<T> call) {
        startPostData(call, null, false);
    }

    public void startPostData(Call<T> call, boolean isInitEmptyData) {
        startPostData(call, null, isInitEmptyData);
    }

    public void startPostData(Call<T> call, HashMap<String, Object> requestMap, boolean isInitEmptyData) {
        if (requestMap != null) {
            mMap.putAll(requestMap);
        }
        mState = STATE_LOADING_RE_FRESH;
        getData(call, isInitEmptyData);
    }

    public void onRefreshData() {
        mState = STATE_LOADING_RE_FRESH;
        mMap.put(mPage, 1);
        getData(listBindDataInterface.getCall(mMap), false);
    }


    public void setListRefreshListener(ListRefreshListener listRefreshListener) {
        this.listRefreshListener = listRefreshListener;
    }

    private void getData(Call<T> call, boolean isInitEmptyData) {


    /*    if (call.isExecuted()){
            recyclerView.onRefreshComplete();
            return;
        }*/


        if (isInitEmptyData) {

            if (tempData == null) {
                tempData = new ArrayList<>();

            }
            initAdapter(true);


            return;
        }


        if (isPostIng) {
            return;
        }

        isPostIng = true;

        if (mState == STATE_LOADING_NO_MORE) {
            return;
        }

        call.enqueue(new AbsZCallback<T>(httpDialogLoading) {
            @Override
            public void onSuccess(Call<T> call, Response<T> response) {
                isPostIng = false;
                Log.d(TAG, "---STATE_OK---");

                //     recyclerView.onRefreshComplete();

                BaseModel result = response.body();
                if (result == null) {
                    return;
                }

                switch (mState) {

                    case STATE_LOADING_RE_FRESH: //刷新

                        if (loadMoreView != null) {
                            loadMoreView.setText("正在加载更多....");
                        }

                        if (listRefreshListener != null) {
                            listRefreshListener.onRefresh();
                        }
                        if (tempData == null) {
                            tempData = listBindDataInterface.getAdapterList(response.body());

                        } else {
                            tempData.clear();
                            tempData.addAll(listBindDataInterface.getAdapterList(response.body()));
                        }
                        initAdapter(true);


                        break;
                    case STATE_LOADING_MORE:

                        List<D> list = listBindDataInterface.getAdapterList(response.body());

                        if (list != null) {

                            if (list.size() < CommonRecycleViewAdapter.DATA_SIZE) {
                                if (tempData != null && !list.isEmpty()) {
                                    tempData.addAll(list);
                                }
                                mState = STATE_LOADING_NO_MORE;//没有更多数据了
                                if (loadMoreView != null) {
                                    loadMoreView.setText("没有更多数据了！");
                                }
                                initAdapter(false);
                                return;
                            } else {
                                if (tempData != null) {
                                    tempData.addAll(list);
                                }
                            }


                            initAdapter(false);
                        }


                        break;

                    default:

                        break;

                }

                mState = STATE_OK;


            }

            @Override
            public void onFail(Call<T> call, Throwable t) {

                if (tempData == null) {
                    tempData = new ArrayList<>();
                    initAdapter(true);
                }


                if (listRefreshListener != null) {
                    listRefreshListener.onRefresh();
                }

                Log.d(TAG, "---onFail---");
                isPostIng = false;
                //  recyclerView.onRefreshComplete();
                mState = STATE_OK;
                int page = 1;
                try {
                    page = (int) mMap.get(mPage);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                page--;
                if (mMap != null) {
                    mMap.put(mPage, page);
                }

                if (!TextUtils.isEmpty((CharSequence) mMap.get("token"))) {
                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_LONG).show();
                } else {

                    adapter.setEmptyViewText("登录后，下拉刷新查看！");
                    Toast.makeText(mActivity, "请先登录查看", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void initAdapter(boolean isInit) {
        if (recyclerView == null) {

            throw new NullPointerException("recyclerView is null 。check recyclerView ！ ");
        }


        if (isInit) {
            if (adapter == null) {
                adapter = new CommonRecycleViewAdapter<D>(tempData, mAdapterLayoutId) {
                    @Override
                    public void setData(CombinationViewHolder holder, D t, int position) {
                        listBindDataInterface.bindData(holder, t, position);

                    }
                };

                if (loadMoreView == null) {
                    loadMoreView = new TextView(mActivity);
                    loadMoreView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    loadMoreView.setGravity(Gravity.CENTER);
                    loadMoreView.setPadding(10, 10, 10, 10);
                }


                loadMoreView.setText("正在加载更多....");

                adapter.addFooterView(loadMoreView);

                recyclerView.setAdapter(adapter);
                if (isLoadMore) {


                    adapter.addScrollFooterListtener(new CommonRecycleViewAdapter.RecyclerViewScrollFooterListener() {
                        @Override
                        public void onScrollFooterListtener(RecyclerView recyclerView, int newState) {
                            //  Logger.getLogger().logD(TAG, "---addScrollFooterListtener2---");
                            if (mState == STATE_LOADING_NO_MORE) {
                                return;
                            }
                            mState = STATE_LOADING_MORE;
                            int page = 1;
                            try {
                                page = (int) mMap.get(mPage);

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                            page++;
                            Log.d(TAG, "---STATE_LOADING_MORE---------" + page);
                            mMap.put(mPage, page);
                            getData(listBindDataInterface.getCall(mMap), false);
                        }
                    });
                }
            } else {

                adapter.notifyDataSetChanged();
            }

        } else {

            adapter.notifyDataSetChanged();
        }


    }


    public List<D> getListData() {

        return tempData;

    }

    public void removeData(int index) {
        if (tempData != null) {
            tempData.remove(index);
        }
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setNeedLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public Map<String, Object> getMap() {

        return mMap;

    }


}
