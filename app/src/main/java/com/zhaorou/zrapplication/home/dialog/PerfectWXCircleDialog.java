package com.zhaorou.zrapplication.home.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.View;
import android.view.ViewGroup;
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
import com.zhaorou.zrapplication.utils.DataStorageDirectoryHelper;
import com.zhaorou.zrapplication.utils.FileUtils;
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

    private LinearLayoutManager mLayoutManager;
    private ImagesAdapter mImagesAdapter;
    private ArrayList<String> mImagesList = new ArrayList<>();
    private String mMarketImageUrl = "";
    private ImageView mMarketImgIv;
    private GoodsListModel.DataBean.ListBean mGoodsBean;
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
            switch (msg.what) {
                case 0:
                    Toast.makeText(getContext(), "文案提交成功，请等待审核", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                case 1:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        String data = bundle.getString("data");
                        mFragment.onLoadFail(data);
                    }
                    dismiss();
                    break;
                case 2:
                    mFragment.onLoadFail("网络请求失败");
                    break;
            }
        }
    };
    private TextView mDialogTitle;
    private MultipleImageAdapter mMultipleImageAdapter;
    private FrameLayout mPreviewImgLayout;
    private FriendPopDetailModel.DataBean.EntityBean mEntityBean;
    private LoadingDialog mLoadingDialog;


    public PerfectWXCircleDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public PerfectWXCircleDialog(@NonNull Context context, HomeVPItemFragment fragment) {
        super(context);
        mContext = context;
        mFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_perfect_wx_circle_dialog);
        EventBus.getDefault().register(this);
        mPresenter.attachView(this);
        mLoadingDialog = new LoadingDialog(mContext);
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
            case R.id.perfect_wx_circle_dialog_btn_cancel:
                dismiss();
                break;
            case R.id.perfect_wx_circle_dialog_btn_submit:
                if (TextUtils.isEmpty(mMarketImageUrl)) {
                    Toast.makeText(mContext, "请添加主营销图", Toast.LENGTH_SHORT).show();
                } else if (mImagesList == null || mImagesList.size() < 3) {
                    Toast.makeText(mContext, "至少添加3张晒图", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mContentEt.getText().toString())) {
                    Toast.makeText(mContext, "请填写文案内容", Toast.LENGTH_SHORT).show();
                } else {
                    onShowLoading();
                    uploadMarketImage();
                }
                break;
            case R.id.perfect_wx_circle_dialog_btn_share_wx:
                shareFriendPopToWx();
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
            if (TextUtils.isEmpty(mMarketImageUrl) || TextUtils.isEmpty(mContentEt.getText().toString()) || mImagesList.size() < 3) {
                mBtnSubmit.setEnabled(false);
                mBtnSubmit.setTextColor(mContext.getResources().getColor(R.color.colorGray_999999));
            } else {
                mBtnSubmit.setEnabled(true);
                mBtnSubmit.setTextColor(mContext.getResources().getColor(android.R.color.white));
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
                        uploadImages();
                        try {
                            if (response != null && response.body() != null) {
                                String responseStr = response.body().string();
                                JSONObject jsonObj = new JSONObject(responseStr);
                                if (jsonObj != null && jsonObj.optInt("code") == 200) {
                                    mMarketImageUrl = jsonObj.optString("data");
                                } else if (!jsonObj.isNull("data")) {
                                    String data = jsonObj.optString("data");
                                    Message message = new Message();
                                    message.what = 1;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("data", data);
                                    message.setData(bundle);
                                    mHandler.sendMessage(message);

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
                        try {
                            if (response != null && response.body() != null) {
                                final String responseStr = response.body().string();
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
                try {
                    if (response != null && response.body() != null) {
                        String str = response.body().string();
                        JSONObject jsonObj = new JSONObject(str);
                        if (jsonObj.optInt("code") == 200) {
                            mHandler.sendEmptyMessage(0);
                        } else if (!jsonObj.isNull("data")) {
                            String data = jsonObj.optString("data");
                            Message message = new Message();
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("data", data);
                            message.setData(bundle);
                            mHandler.sendMessage(message);
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
                mHandler.sendEmptyMessage(2);
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


        mBtnAddMainImage = findViewById(R.id.perfect_wx_circle_dialog_btn_add_main_image);
        mBtnAddMainImage.setOnClickListener(this);

        mBtnAddImages = findViewById(R.id.perfect_wx_circle_dialog_btn_add_images);
        mBtnAddImages.setOnClickListener(this);

        mBtnSubmit = findViewById(R.id.perfect_wx_circle_dialog_btn_submit);
        mBtnSubmit.setOnClickListener(this);
        mBtnSubmit.setEnabled(false);

        mBtnCancel = findViewById(R.id.perfect_wx_circle_dialog_btn_cancel);
        mBtnCancel.setOnClickListener(this);

        mContentEt = findViewById(R.id.perfect_wx_circle_dialog_content_et);
        mHasFriendPopLl = findViewById(R.id.perfect_wx_circle_dialog_has_friendpop);
        mNoFriendPop = findViewById(R.id.perfect_wx_circle_dialog_no_friendpop);
        mBtnCopyWords = findViewById(R.id.perfect_wx_circle_dialog_btn_share_wx);
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
            GlideApp.with(getContext()).asBitmap()
                    .override(50)
                    .load(ZRDConstants.HttpUrls.BASE_URL + mMarketImageUrl)
                    .placeholder(R.drawable.img_pre_load)
                    .error(R.drawable.img_load_error)
                    .into(mMarketImgIv);
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

            if (TextUtils.isEmpty(mMarketImageUrl) || TextUtils.isEmpty(mContentEt.getText().toString()) || mImagesList.size() < 3) {
                mBtnSubmit.setEnabled(false);
                mBtnSubmit.setTextColor(mContext.getResources().getColor(R.color.colorGray_999999));
            } else {
                mBtnSubmit.setEnabled(true);
                mBtnSubmit.setTextColor(mContext.getResources().getColor(android.R.color.white));
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
        mEntityBean = entityBean;
    }

    private void shareFriendPopToWx() {

        final String content = mContentEt.getText().toString();
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("taoword", content);
        cm.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "已复制文案，正在启动微信，请稍后...", Toast.LENGTH_SHORT).show();

        final List<String> list = new ArrayList<>();
        if (mEntityBean != null) {
            String marketImage = mEntityBean.getMarket_image();
            list.add(ZRDConstants.HttpUrls.BASE_URL + marketImage);
            String imageStr = mEntityBean.getImage();
            if (!TextUtils.isEmpty(imageStr)) {
                if (imageStr.contains("#")) {
                    String[] imageArray = imageStr.split("#");
                    for (String img : imageArray) {
                        list.add(ZRDConstants.HttpUrls.BASE_URL + img);
                    }
                } else {
                    list.add(ZRDConstants.HttpUrls.BASE_URL + imageStr);
                }
            }
        } else {
            list.add(mGoodsBean.getPic());
        }

        final List<File> fileList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String imgUrl : list) {
                    File file = FileUtils.saveImageToSdCard(getContext().getExternalCacheDir(), imgUrl);
                    fileList.add(file);
                }
                Intent intent = new Intent();
                ComponentName comp;
                comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.putExtra("Kdescription", content);
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                ArrayList<Uri> imgUriList = new ArrayList<>();
                for (File file : fileList) {
                    imgUriList.add(Uri.fromFile(file));
                }
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUriList);
                mContext.startActivity(intent);
            }
        }).start();
    }

    @Override
    public void onGetTaowords(String tkl) {

    }

    @Override
    public void onShowLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void onHideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onLoadFail(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
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
        public void onBindViewHolder(@NonNull ImagesViewHolder holder, final int position) {
            String imageUrl = mImagesList.get(position);
            if (TextUtils.equals(imageFrom, "server")) {
                imageUrl = ZRDConstants.HttpUrls.BASE_URL + imageUrl;
            }
            GlideApp.with(getContext()).load(imageUrl).override(50)
                    .placeholder(R.drawable.img_pre_load)
                    .error(R.drawable.img_load_error)
                    .into(holder.mImageView);
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mMultipleImageAdapter = new MultipleImageAdapter(mImagesList);
                    mPreviewMultipleImageVp.setAdapter(mMultipleImageAdapter);
                    mPreviewImgLayout.setVisibility(View.VISIBLE);
                    mPreviewMultipleImageVp.setCurrentItem(position);
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

        public ImagesViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_wx_circle_dialog_img_iv);
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
            final String imageUrl = ZRDConstants.HttpUrls.BASE_URL + imageList.get(position);
            View view = getLayoutInflater().inflate(R.layout.layout_multiple_preview_item, null);
            ImageView imageView = view.findViewById(R.id.iv_multiple_preview_item_image);
            GlideApp.with(getContext()).asBitmap().load(imageUrl).into(imageView);
            TextView btnSaveImage = view.findViewById(R.id.iv_multiple_preview_item_btn_save_image);
            btnSaveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SaveImageAsync(imageUrl).execute();
                }
            });
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

    private class SaveImageAsync extends AsyncTask<String, File, File> {

        private String imageUrl;

        public SaveImageAsync(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        protected File doInBackground(String... strings) {
            File file = FileUtils.saveImageToSdCard(DataStorageDirectoryHelper.getExternalPublicDcimDir(), imageUrl);
            return file;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onShowLoading();
        }


        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (file != null && file.exists()) {
                Toast.makeText(mContext, "图片保存成功", Toast.LENGTH_SHORT).show();
            }
            onHideLoading();
        }
    }
}
