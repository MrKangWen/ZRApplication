package com.zhaorou.zrapplication.home.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    private LinearLayout mHasFriendPopLl;
    private LinearLayout mNoFriendPop;
    private TextView mBtnCopyWords;
    private TextView mBtnEditWords;
    private TextView mBtnClose;
    private ViewPager mPreviewMultipleImageVp;

    private HomeFragmentPresenter mPresenter = new HomeFragmentPresenter();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getContext(), "文案提交成功，请等待审核", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };
    private TextView mDialogTitle;
    private MultipleImageAdapter mMultipleImageAdapter;
    private FrameLayout mPreviewImgLayout;


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
                onShowLoading();
                uploadMarketImage();
                break;
            case R.id.perfect_wx_circle_dialog_btn_copy_words:
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("words", mContentEt.getText().toString());
                cm.setPrimaryClip(clipData);
                Toast.makeText(mContext, "文案已复制", Toast.LENGTH_SHORT).show();
                break;
            case R.id.perfect_wx_circle_dialog_btn_edit_words:
                mContentEt.setFocusable(true);
                mContentEt.setFocusableInTouchMode(true);
                mContentEt.requestFocus();
                mNoFriendPop.setVisibility(View.VISIBLE);
                mHasFriendPopLl.setVisibility(View.GONE);
                break;
            case R.id.perfect_wx_circle_dialog_btn_close:
                dismiss();
                break;
            case R.id.perfet_wx_circle_dialog_market_img_iv:
                List<String> tempList = new ArrayList<>();
                tempList.add(mMarketImageUrl);
                tempList.addAll(mImagesList);
                MultipleImageAdapter multipleImageAdapter = new MultipleImageAdapter(tempList);
                mPreviewMultipleImageVp.setAdapter(multipleImageAdapter);
                mPreviewImgLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.preview_img_btn_back:
                if (mPreviewImgLayout.getVisibility() == View.VISIBLE) {
                    mPreviewImgLayout.setVisibility(View.GONE);
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
            mImagesAdapter.notifyDataSetChanged("picker");
        }
    }

    private void uploadMarketImage() {
        if (!TextUtils.isEmpty(mMarketImageUrl)) {
            File file = new File(mMarketImageUrl);
            if (file != null && file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().uploadFile(ZRDConstants.HttpUrls.UPLOAD_FILE, file.getName(), part);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e(TAG, "onResponse: uploadMarketImage: " + response);
                        uploadImages();
                        try {
                            if (response != null && response.body() != null) {
                                String responseStr = response.body().string();
                                Log.e(TAG, "onResponse: uploadMarketImage: " + responseStr);
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
                        Log.e(TAG, "onFailure: Throwable:mMarketImgUrl " + t);
                        uploadImages();
                    }

                });
            } else {
                uploadImages();
            }
        } else {
            uploadImages();
        }
    }

    private void uploadImages() {
        final List<File> fileList = new ArrayList<>();
        for (String imagePath : mImagesList) {
            File file = new File(imagePath);
            fileList.add(file);
        }
        if (fileList.size() > 0) {
            final List<String> uploadList = new ArrayList<>();
            for (File file : fileList) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                Call<ResponseBody> call = HttpRequestUtil.getRetrofitService().uploadFile(ZRDConstants.HttpUrls.UPLOAD_FILE, file.getName(), part);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e(TAG, "onResponse: uploadImages: " + response);
                        try {
                            if (response != null && response.body() != null) {
                                final String responseStr = response.body().string();
                                Log.e(TAG, "onResponse: uploadImages: " + responseStr);
                                JSONObject jsonObject = new JSONObject(responseStr);
                                if (jsonObject != null && jsonObject.optInt("code") == 200) {
                                    String uploadPath = jsonObject.optString("data");
                                    uploadList.add(uploadPath);
                                }
                                if (uploadList.size() == fileList.size()) {
                                    saveFriendPop(uploadList);
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
                        Log.e(TAG, "onFailure: Throwable:images " + t);
                        uploadList.add("");
                        if (uploadList.size() == fileList.size()) {
                            saveFriendPop(uploadList);
                        }
                    }
                });
            }
        } else {
            saveFriendPop(null);
        }

    }

    private void saveFriendPop(List<String> uploadList) {
        String goodsId = mGoodsBean.getGoods_id();
        String goodsTitle = mTitleTv.getText().toString();
        String content = mContentEt.getText().toString();
        String image = "";
        if (uploadList != null) {
            for (String img : uploadList) {
                image = img + "#";
            }
            image = image.substring(0, image.lastIndexOf("#"));
        }
        String token = SPreferenceUtil.getString(getContext(), ZRDConstants.SPreferenceKey.SP_LOGIN_TOKEN, "");
        Map<String, String> params = new HashMap<>();
        params.put("keyid", goodsId);
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
                Log.e(TAG, "onResponse: response: " + response);
                try {
                    if (response != null && response.body() != null) {
                        String str = response.body().string();
                        Log.e(TAG, "onResponse: str: " + str);
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
                Log.e(TAG, "onFailure: Throwable: " + t);
            }
        });
    }

    private void initViews() {
        mMarketImageUrl = "";
        mImagesList.clear();

        mDialogTitle = findViewById(R.id.perfect_wx_circle_dialog_dialog_title);
        mUrlTv = findViewById(R.id.perfect_wx_circle_dialog_url_tv);
        mTitleTv = findViewById(R.id.perfect_wx_circle_dialog_title_tv);
        mMarketImgIv = findViewById(R.id.perfet_wx_circle_dialog_market_img_iv);
        mLoadingLayout = findViewById(R.id.perfect_wx_dialog_loading_ll);
        mLoadingLayout.setOnClickListener(this);

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
        mHasFriendPopLl = findViewById(R.id.perfect_wx_circle_dialog_has_friendpop);
        mNoFriendPop = findViewById(R.id.perfect_wx_circle_dialog_no_friendpop);
        mBtnCopyWords = findViewById(R.id.perfect_wx_circle_dialog_btn_copy_words);
        mBtnCopyWords.setOnClickListener(this);
        mBtnEditWords = findViewById(R.id.perfect_wx_circle_dialog_btn_edit_words);
        mBtnEditWords.setOnClickListener(this);
        mBtnClose = findViewById(R.id.perfect_wx_circle_dialog_btn_close);
        mBtnClose.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.perfect_wx_circle_dialog_images_rv);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mImagesAdapter = new ImagesAdapter();
        mRecyclerView.setAdapter(mImagesAdapter);

        mPreviewMultipleImageVp = findViewById(R.id.perfect_wx_circle_dialog_vp_image_preview);
        mPreviewImgLayout = findViewById(R.id.preview_img_layout);
        mMarketImgIv.setOnClickListener(this);
        findViewById(R.id.preview_img_btn_back).setOnClickListener(this);

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

        String quanGuidContent = goodsBean.getQuan_guid_content();
        mContentEt.setText(quanGuidContent);

        int isFriendpop = goodsBean.getIs_friendpop();
        if (isFriendpop == 0) {
            mDialogTitle.setText("完善朋友圈文案");
            mUrlTv.setVisibility(View.VISIBLE);
            mUrlTv.setText("https://detail.tmall.com/item.html?id=" + goodsId);

            String goodsName = goodsBean.getGoods_name();
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(goodsName);

            mHasFriendPopLl.setVisibility(View.GONE);
            mNoFriendPop.setVisibility(View.VISIBLE);
            mContentEt.setFocusable(true);
            mContentEt.setFocusableInTouchMode(true);
        } else if (isFriendpop == 1) {
            mDialogTitle.setText("朋友圈文案");
            mUrlTv.setVisibility(View.GONE);
            mTitleTv.setVisibility(View.GONE);

            mHasFriendPopLl.setVisibility(View.VISIBLE);
            mNoFriendPop.setVisibility(View.GONE);
            mContentEt.setFocusable(false);
            mContentEt.setFocusableInTouchMode(false);
        }

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
            GlideApp.with(getContext()).asBitmap().load(ZRDConstants.HttpUrls.BASE_URL + mMarketImageUrl).into(mMarketImgIv);
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
            mImagesAdapter.notifyDataSetChanged("server");
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

        String imageFrom;

        public void notifyDataSetChanged(String imageFrom) {
            this.imageFrom = imageFrom;
            mImagesAdapter.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_wx_circle_dialog, parent, false);
            ImagesViewHolder imagesViewHolder = new ImagesViewHolder(view);
            return imagesViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
            String imageUrl = mImagesList.get(position);
            if (TextUtils.equals(imageFrom, "server")) {
                imageUrl = ZRDConstants.HttpUrls.BASE_URL + imageUrl;
            }
            GlideApp.with(getContext()).asBitmap().override(80).load(imageUrl).into(holder.mImageView);
            final String finalImageUrl = imageUrl;
            holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImagesList.remove(finalImageUrl);
                    mImagesAdapter.notifyDataSetChanged();
                }
            });
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mMultipleImageAdapter = new MultipleImageAdapter(mImagesList);
                    mPreviewMultipleImageVp.setAdapter(mMultipleImageAdapter);
                    mPreviewImgLayout.setVisibility(View.VISIBLE);
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

    private class MultipleImageAdapter extends PagerAdapter {
        private List<String> imageList;

        public MultipleImageAdapter(List<String> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_multiple_preview_item, null);
            ImageView imageView = view.findViewById(R.id.iv_multiple_preview_item_image);
            GlideApp.with(getContext()).asBitmap().load(ZRDConstants.HttpUrls.BASE_URL + imageList.get(position)).override(500, 500).into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
