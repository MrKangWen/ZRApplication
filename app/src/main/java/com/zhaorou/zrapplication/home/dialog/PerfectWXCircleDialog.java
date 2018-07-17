package com.zhaorou.zrapplication.home.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.BaseDialog;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.constants.ZRDConstants;
import com.zhaorou.zrapplication.contentresolver.ImageModel;
import com.zhaorou.zrapplication.contentresolver.SelectPictureActivity;
import com.zhaorou.zrapplication.eventbus.MessageEvent;
import com.zhaorou.zrapplication.home.HomeVPItemFragment;
import com.zhaorou.zrapplication.home.IHomeFragmentView;
import com.zhaorou.zrapplication.home.model.ClassListModel;
import com.zhaorou.zrapplication.home.model.FriendPopDetailModel;
import com.zhaorou.zrapplication.home.model.GoodsListModel;
import com.zhaorou.zrapplication.home.model.TaowordsModel;
import com.zhaorou.zrapplication.home.presenter.HomeFragmentPresenter;
import com.zhaorou.zrapplication.network.HttpRequestUtil;
import com.zhaorou.zrapplication.utils.SPreferenceUtil;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfectWXCircleDialog extends BaseDialog implements IHomeFragmentView, View.OnClickListener {

    private static final String TAG = "PerfectWXCircleDialog";

    private Context mContext;
    private HomeVPItemFragment mFragment;

    private TextView mUrlTv;
    private TextView mTitleTv;
    private EditText mContentEt;
    private ImageView mBtnAddMainImage;
    private ImageView mBtnAddImages;
    private TextView mBtnCancel;
    private TextView mBtnSubmit;
    private CustomRecyclerView mRecyclerView;
    private LinearLayout mLoadingLayout;

    private LinearLayoutManager mLayoutManager;
    private ImagesAdapter mImagesAdapter;
    private ArrayList<String> mImagesList = new ArrayList<>();
    private String mMarketImageUrl = "";
    private ImageView mMarketImgIv;
    private GoodsListModel.DataBean.ListBean mGoodsBean;
    private ImageView mDeleMarketImg;
    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getContext(), "文案提交成功", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };

    public PerfectWXCircleDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_perfect_wx_circle_dialog);
        EventBus.getDefault().register(this);
        mPresenter.attachView(this);
        initViews();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.perfect_wx_circle_dialog_btn_add_main_image:
                Intent intent = new Intent(getContext(), SelectPictureActivity.class);
                intent.putExtra("command", ZRDConstants.EventCommand.COMMAND_SELECT_MARKET_IMAGE);
                intent.putExtra("image", mMarketImageUrl);
                mContext.startActivity(intent);
                break;
            case R.id.perfect_wx_circle_dialog_btn_add_images:
                Intent intent1 = new Intent(getContext(), SelectPictureActivity.class);
                intent1.putExtra("command", ZRDConstants.EventCommand.COMMAND_SELECT_IMAGES);
                intent1.putStringArrayListExtra("images", mImagesList);
                mContext.startActivity(intent1);
                break;
            case R.id.perfet_wx_circle_dialog_delete_market_img_iv:
                mMarketImgIv.setImageResource(0);
                mDeleMarketImg.setVisibility(View.GONE);
                mMarketImageUrl = "";
                break;
            case R.id.perfect_wx_circle_dialog_btn_cancel:
                dismiss();
                break;
            case R.id.perfect_wx_circle_dialog_btn_submit:
                String content = mContentEt.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), "请先完善朋友圈文案~", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mMarketImageUrl)) {
                    Toast.makeText(getContext(), "请添加营销主图~", Toast.LENGTH_SHORT).show();
                } else if (mImagesList.size() == 0) {
                    Toast.makeText(getContext(), "请至少添加三张晒图~", Toast.LENGTH_SHORT).show();
                } else {
                    onShowLoading();
                    uploadMarketImage();

                }
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onMessageEvent(MessageEvent<ArrayList<ImageModel>> event) {
        String command = event.getCommand();
        ArrayList<ImageModel> imageModelList = event.getData();
        if (TextUtils.equals(command, ZRDConstants.EventCommand.COMMAND_SELECT_MARKET_IMAGE)) {
            ImageModel imageModel = imageModelList.get(imageModelList.size() - 1);
            String path = imageModel.getPath();
            mMarketImageUrl = path;
            mDeleMarketImg.setVisibility(View.VISIBLE);
            GlideApp.with(getContext()).asBitmap().load(path).into(mMarketImgIv);
        }
        if (TextUtils.equals(command, ZRDConstants.EventCommand.COMMAND_SELECT_IMAGES)) {
            mImagesList.clear();
            for (ImageModel imageModel : imageModelList) {
                String path = imageModel.getPath();
                if (!mImagesList.contains(path)) {
                    mImagesList.add(path);
                }
            }
            mImagesAdapter.notifyDataSetChanged();
        }
    }

    private void uploadMarketImage() {
        File file = new File(mMarketImageUrl);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().uploadFile(ZRDConstants.HttpUrls.UPLOAD_FILE, file.getName(), part);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                uploadImages();
                try {
                    if (response != null && response.body() != null) {
                        String responseStr = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseStr);
                        if (jsonObject != null && jsonObject.optInt("code") == 200) {
                            mMarketImageUrl = jsonObject.optString("data");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void uploadImages() {
        final List<File> fileList = new ArrayList<>();
        for (String imagePath : mImagesList) {
            File file = new File(imagePath);
            fileList.add(file);
        }
        final List<String> uploadList = new ArrayList<>();
        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().uploadFile(ZRDConstants.HttpUrls.UPLOAD_FILE, file.getName(), part);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response != null && response.body() != null) {
                            final String responseStr = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseStr);
                            if (jsonObject != null && jsonObject.optInt("code") == 200) {
                                String uploadPath = jsonObject.optString("data");
                                uploadList.add(uploadPath);
                            }
                            if (uploadList.size() == fileList.size()) {
                                int keyid = mGoodsBean.getKeyid();
                                String goodsId = mGoodsBean.getGoods_id();
                                String goodsTitle = mTitleTv.getText().toString();
                                String content = mContentEt.getText().toString();
                                String image = "";
                                for (String img : uploadList) {
                                    image = img + "#";
                                }
                                image = image.substring(0, image.lastIndexOf("#"));
                                String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
                                Map<String, String> params = new HashMap<>();
                                params.put("keyid", keyid + "");
                                params.put("goods_id", goodsId);
                                params.put("goods_title", goodsTitle);
                                params.put("content", content);
                                params.put("image", image);
                                params.put("token", token);
                                params.put("market_image", mMarketImageUrl);

                                Call<ResponseBody> call1 = HttpRequestUtil.getRetrofitService().executePost(ZRDConstants.HttpUrls.ADD_FRIEND_POP, params);
                                call1.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {
                                            if (response != null && response.body() != null) {
                                                String str = response.body().string();
                                                JSONObject jsonObj = new JSONObject(str);
                                                if (jsonObj.optInt("code") == 200) {
                                                    mHandler.sendEmptyMessage(0);
                                                }
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }

    private void initViews() {
        mMarketImageUrl = "";
        mImagesList.clear();
        mUrlTv = findViewById(R.id.perfect_wx_circle_dialog_url_tv);
        mTitleTv = findViewById(R.id.perfect_wx_circle_dialog_title_tv);
        mMarketImgIv = findViewById(R.id.perfet_wx_circle_dialog_market_img_iv);
        mLoadingLayout = findViewById(R.id.perfect_wx_dialog_loading_ll);

        mDeleMarketImg = findViewById(R.id.perfet_wx_circle_dialog_delete_market_img_iv);
        mDeleMarketImg.setOnClickListener(this);

        mBtnAddMainImage = findViewById(R.id.perfect_wx_circle_dialog_btn_add_main_image);
        mBtnAddMainImage.setOnClickListener(this);

        mBtnAddImages = findViewById(R.id.perfect_wx_circle_dialog_btn_add_images);
        mBtnAddImages.setOnClickListener(this);

        mBtnSubmit = findViewById(R.id.perfect_wx_circle_dialog_btn_submit);
        mBtnSubmit.setOnClickListener(this);

        mBtnCancel = findViewById(R.id.perfect_wx_circle_dialog_btn_cancel);
        mBtnCancel.setOnClickListener(this);

        mContentEt = findViewById(R.id.perfect_wx_circle_dialog_content_et);

        mRecyclerView = findViewById(R.id.perfect_wx_circle_dialog_images_rv);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mImagesAdapter = new ImagesAdapter();
        mRecyclerView.setAdapter(mImagesAdapter);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mPresenter.detachView();
            }
        });
    }

    public void setGoodsInfo(GoodsListModel.DataBean.ListBean goodsBean) {
        mGoodsBean = goodsBean;
        String goodsId = goodsBean.getGoods_id();
        mUrlTv.setText("https://detail.tmall.com/item.html?id=" + goodsId);

        String goodsName = goodsBean.getGoods_name();
        mTitleTv.setText(goodsName);

        String quanGuidContent = goodsBean.getQuan_guid_content();
        mContentEt.setText(quanGuidContent);

        Map<String, String> params = new HashMap<>();
        params.put("goods_id", goodsId);
        mPresenter.getFriendPopDetail(params);
    }

    public void setFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean) {
        if (entityBean != null) {
            String content = entityBean.getContent();
            if (!TextUtils.isEmpty(content)) {
                mContentEt.setText(content);
                mContentEt.setSelection(content.length());
            }
            mMarketImageUrl = entityBean.getMarket_image();
            GlideApp.with(getContext()).asBitmap().load(mMarketImageUrl).into(mMarketImgIv);
            String image = entityBean.getImage();
            if (!TextUtils.isEmpty(image)) {
                if (image.contains("#")) {
                    String[] imageArray = image.split("#");
                    for (String img : imageArray) {
                        mImagesList.add(img);
                    }
                } else {
                    mImagesList.add(image);
                }
            }
        }
    }

    @Override
    public void onFetchedClassList(List<ClassListModel.DataBean.ListBean> list) {

    }

    @Override
    public void onFetchDtkGoodsList(List<GoodsListModel.DataBean.ListBean> list) {

    }

    @Override
    public void onLoadMore(boolean loadMore) {

    }

    @Override
    public void onGetFriendPopDetail(FriendPopDetailModel.DataBean.EntityBean entityBean) {
        setFriendPopDetail(entityBean);
    }

    @Override
    public void onGetTaowords(String tkl) {

    }

    @Override
    public void onShowLoading() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    private class ImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {

        @NonNull
        @Override
        public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_wx_circle_dialog, parent, false);
            ImagesViewHolder imagesViewHolder = new ImagesViewHolder(view);
            return imagesViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
            final String imageUrl = mImagesList.get(position);
            GlideApp.with(getContext()).asBitmap().load(imageUrl).into(holder.mImageView);
            holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImagesList.remove(imageUrl);
                    mImagesAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mImagesList.size();
        }
    }

    private class ImagesViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private ImageView mBtnDelete;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_wx_circle_dialog_img_iv);
            mBtnDelete = itemView.findViewById(R.id.item_wx_circle_dialog_delete_icon);
        }
    }
}
