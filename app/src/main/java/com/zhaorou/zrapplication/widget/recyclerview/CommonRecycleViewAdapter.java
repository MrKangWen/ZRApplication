
package com.zhaorou.zrapplication.widget.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaorou.zrapplication.R;

import java.util.List;



public abstract class CommonRecycleViewAdapter<T> extends RecyclerView.Adapter<CombinationViewHolder> {

    /**
     * list 列表请求数量
     */
    public final static int DATA_SIZE = 10;
    public final static String DATA_SIZE_STRING = String.valueOf(DATA_SIZE);//list 列表请求数量
    private static final String TAG = "CommonRecycleViewAdapter";
    private static final int TYPE_FOOTER = -2;
    private static final int TYPE_EMPTY = 0x00000555;
    private static final int TYPE_HEADER = 0x00000556;
    private List<T> list;
    private int layout;
    private RecyclerView recyclerView;
    private View mEmptyView;
    private View mFooterView;
    private View mHeaderView;
    private Context mContext;


    public CommonRecycleViewAdapter(List<T> list, int layout) {
        this.list = list;
        this.layout = layout;


    }

    public Context getContext() {

        return mContext;
    }

    @Override
    public CombinationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.mContext = parent.getContext();
     /*   CombinationViewHolder holder = new CombinationViewHolder(
                LayoutInflater.from(context).inflate(R.layout.ad_home_combination, parent, false));*/
        View view = null;

        if (viewType == TYPE_EMPTY) {
            if (mEmptyView != null) {
                view = mEmptyView;
            } else {
                //默认为空时显示的视图
                view = LayoutInflater.from(getContext()).inflate(R.layout.view_list_emptyview, parent, false);
                mEmptyView = view;


            }

        } else if (viewType == TYPE_HEADER) {
            view = mHeaderView;

        } else if (viewType == TYPE_FOOTER) {
            view = mFooterView;
            mFooterView.setVisibility(View.GONE);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        }
        return new CombinationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CombinationViewHolder holder, int position) {

        // holder.setIsRecyclable(IsRecyclable);

        int type = holder.getItemViewType();

        switch (type) {
            case TYPE_HEADER:
                //  LogUtil.i("data", "add TYPE_HEADER view");
                break;
            case TYPE_FOOTER:
                //     LogUtil.i("data", "add TYPE_FOOTER view");
                break;
            case TYPE_EMPTY:
                // Logger.getLogger().logD(TAG, "add TYPE_EMPTY view");
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }

                break;
            default:
                //LogUtil.i("data", "数据不为空 显示视图");
                // mEmptyView.setVisibility(View.GONE);
                if (list.size() > 0) {
                    //  if (list.size() > 0 && list.size() > position) {

                    //   int p=position;
                    if (mHeaderView != null) {
                        //   p=p-1;
                        setData(holder, list.get(position - 1), position - 1);
                    } /*else if(mErrorView!=null){
                      //  p=p-1;
                        setData(holder, list.get(position - 1), position - 1);
                    }*/ else {

                        setData(holder, list.get(position), position);
                    }
                    //   LogUtil.i("data", "position:" + position);

                }
                break;

        }


    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return (type == TYPE_FOOTER || type == TYPE_EMPTY) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 添加底部视图
     *
     * @param mFooterView
     */
    public void addFooterView(View mFooterView) {

        this.mFooterView = mFooterView;
    }

    public View getFooterView() {

        return this.mFooterView;
    }

    /**
     * 添加头部
     *
     * @param mHeaderView
     */
    public void addHeader(View mHeaderView) {

        this.mHeaderView = mHeaderView;
    }

    /**
     * 设置数据为空时显示的视图
     *
     * @param mEmptyView
     */
    public void setEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }
    @Override
    public int getItemViewType(int position) {

        if (list.size() == 0) {
            return TYPE_EMPTY;
        }
        if (list.size() > 0 && position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        if (position > 0 && position == list.size() && mFooterView != null) {
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }
    @Override
    public int getItemCount() {

        if (list == null) {
            return 0;
        }

        int count = list.size();
        if (list.size() == 0) {
            count++;
        } else if (mHeaderView != null && list.size() != 0) {
            //   LogUtil.i( "getItemCount ++");
            count++;
        } else if (mFooterView != null && list.size() != 0) {

            count++;
        }
        //  LogUtil.i( "getItemCount ++"+count);
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setFooterViewVisibility(int state) {
        if (mFooterView != null) {
            mFooterView.setVisibility(state);
        }
    }

    public void addScrollFooterListtener(final RecyclerViewScrollFooterListener listener) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    //   Logger.getLogger().logD(TAG,"加载更多。。。。"+lastVisibleItem+"-----"+totalItemCount);
                    // 判断是否滚动到底部，并且是向右滚动

                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        //   Logger.getLogger().logD(TAG,"加载更多。。。。"+list.size());
                        //  LogUtil.d("howes right=" + manager.findLastCompletelyVisibleItemPosition());
                        //       Logger.getLogger().logD(TAG,"加载更多。。。。"+list.size());
                        if (list.size() < DATA_SIZE) {
                            if (mFooterView != null) {
                                mFooterView.setVisibility(View.GONE);
                            }
                            return; //至少满足超过 DATA_SIZE 条 具体数字参考 DATA_SIZE
                        }

                        if (mFooterView != null) {
                            mFooterView.setVisibility(View.VISIBLE);
                        }

                        if (listener != null) {
                            listener.onScrollFooterListtener(recyclerView, newState);
                        }

                        //  if (mLoadState == STATE_LOADING) return;
                        //   if (HotFurniture.this.result.size() < StringUtils.toInt(AppApi.PAGE_SIZE)) return;
                        // getData(IS_LOADMORE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    public abstract void setData(CombinationViewHolder holder, T t, int position);

    public interface RecyclerViewScrollFooterListener {

        void onScrollFooterListtener(RecyclerView recyclerView, int newState);

    }
}