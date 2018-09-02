package com.zhaorou.zhuanquanapp.contentresolver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaorou.zhuanquanapp.R;
import com.zhaorou.zhuanquanapp.base.GlideApp;
import com.zhaorou.zhuanquanapp.constants.ZRDConstants;
import com.zhaorou.zhuanquanapp.eventbus.MessageEvent;
import com.zhaorou.zhuanquanapp.recyclerview.CustomItemDecoration;
import com.zhaorou.zhuanquanapp.recyclerview.CustomRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectPictureActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "SelectPictureActivity";
    public static final String RESULT_DATA = "com.lykevin.selectpicture.result_data";
    private ImageView mBtnGoBack;
    private TextView mTitleTextTv;
    private TextView mBtnChageAlbum;
    private CustomRecyclerView mImageListRv;
    private GridLayoutManager mImageListRvManager;
    private ImageListAdapter mImageListAdapter;
    private LinearLayout mPreviewLayoutLl;
    private ImageView mPreviewImageIv;
    private ImageModel mImageModel;
    private TextView mFooterCenterTextTv;
    private ViewPager mPreviewMultipleImageVp;
    private TextView mPreviewCenterText;
    private TextView mPreviewRightText;
    private MultipleImageAdapter mMultipleImageAdapter;
    private float mPivotX;
    private float mPivotY;
    private float mItemViewWidth;
    private float mItemViewHeight;
    private ArrayList<ImageModel> mAdapterImageModelList = new ArrayList<>();
    private ArrayList<ImageModel> mAllImageModelList = new ArrayList<>();
    private ArrayList<ImageModel> mSelectedImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_picture);
        getSupportActionBar().hide();

        initTitleViews();
        initImageListRv();
        initFooterViews();
        initPreviewViews();
        getAllPictures();
    }

    private void getAllPictures() {
        List<ImageModel> imageModelList = ContentResolverHelper.queryImagesFromExternal(this);
        mAdapterImageModelList.addAll(imageModelList);
        mAllImageModelList.addAll(imageModelList);
        mImageListAdapter.notifyDataSetChanged();
    }

    private void initFooterViews() {
        mFooterCenterTextTv = findViewById(R.id.tv_select_count_activity_select_picture);
    }

    private void initPreviewViews() {
        mPreviewCenterText = findViewById(R.id.tv_image_preview_center_text);
        mPreviewRightText = findViewById(R.id.tv_image_preview_right_text);
        mPreviewLayoutLl = findViewById(R.id.ll_layout_image_preview);
        mPreviewImageIv = findViewById(R.id.iv_image_preview_image);
        mPreviewMultipleImageVp = findViewById(R.id.vp_image_preview_multiple_image);
    }

    private void initImageListRv() {
        mImageListRv = findViewById(R.id.rv_image_list);
        mImageListRvManager = new GridLayoutManager(this, 4);
        mImageListRv.setLayoutManager(mImageListRvManager);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(1, Color.parseColor("#E5E5E5"));
        mImageListRv.addItemDecoration(itemDecoration);
        mImageListAdapter = new ImageListAdapter();
        mImageListRv.setAdapter(mImageListAdapter);
    }

    private void initTitleViews() {
        mBtnGoBack = findViewById(R.id.iv_layout_title_left_icon);
        mBtnGoBack.setImageResource(R.drawable.ic_back);
        mBtnGoBack.setVisibility(View.VISIBLE);
        mBtnGoBack.setOnClickListener(this);

        mTitleTextTv = findViewById(R.id.tv_layout_title_center_text);
        mTitleTextTv.setText("所有图片");

        mBtnChageAlbum = findViewById(R.id.tv_layout_title_right_text);
        mBtnChageAlbum.setText("切换相册");
        mBtnChageAlbum.setVisibility(View.VISIBLE);
        mBtnChageAlbum.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ArrayList<ImageModel> imageModelList = data.getParcelableArrayListExtra(RESULT_DATA);
            mAdapterImageModelList.clear();
            mAdapterImageModelList.addAll(imageModelList);
            mImageListAdapter.notifyDataSetChanged();
            selectedCount();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_layout_title_left_icon) {
            finish();
        }
        if (v.getId() == R.id.tv_layout_title_right_text) {
            Intent intent = new Intent(SelectPictureActivity.this, AlbumListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RESULT_DATA, mAllImageModelList);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
        if (v.getId() == R.id.btn_cancel_activity_select_picture) {
            selectAll(false);
            selectedCount();
        }
        if (v.getId() == R.id.btn_select_all_activity_select_picture) {
            selectAll(true);
            selectedCount();
        }
        if (v.getId() == R.id.iv_image_preview_left_icon) {
            hidePreviewLayout();
        }
        if (v.getId() == R.id.tv_image_preview_right_text) {
            mImageModel.setSelected(true);
            refreshAllPictureList(mImageModel, true);
            mImageListAdapter.notifyDataSetChanged();
            hidePreviewLayout();
            selectedCount();
        }
        if (v.getId() == R.id.btn_preview_activity_select_picture) {
            List<ImageModel> selectedImageList = new ArrayList<>();
            for (ImageModel imageModel : mAdapterImageModelList) {
                boolean selected = imageModel.isSelected();
                if (selected) {
                    selectedImageList.add(imageModel);
                }
            }
            if (selectedImageList.size() == 0) {
                return;
            }
            mMultipleImageAdapter = new MultipleImageAdapter(selectedImageList);
            mPreviewMultipleImageVp.setAdapter(mMultipleImageAdapter);
            mPreviewMultipleImageVp.addOnPageChangeListener(this);

            mPreviewCenterText.setText("1/" + selectedImageList.size());
            mPreviewCenterText.setVisibility(View.VISIBLE);
            mPreviewRightText.setVisibility(View.INVISIBLE);
            mPreviewMultipleImageVp.setVisibility(View.VISIBLE);
            mPreviewImageIv.setVisibility(View.GONE);
            mPreviewLayoutLl.setVisibility(View.VISIBLE);

            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
            scaleAnimation.setDuration(200);
            mPreviewLayoutLl.startAnimation(scaleAnimation);
        }
        if (v.getId() == R.id.btn_complete_activity_select_picture) {

            for (ImageModel imageModel : mAllImageModelList) {
                boolean selected = imageModel.isSelected();
                if (selected && !mSelectedImageList.contains(imageModel)) {
                    mSelectedImageList.add(imageModel);
                }
            }
            Intent intent = getIntent();
            String command = intent.getStringExtra("command");
            if (TextUtils.equals(command, ZRDConstants.EventCommand.COMMAND_SELECT_IMAGES)) {
                ArrayList<String> imageList = intent.getStringArrayListExtra("images");
                if (imageList != null && imageList.size() > 0) {
                    for (int i = 0; i < mSelectedImageList.size(); i++) {
                        if (i >= imageList.size()) {
                            break;
                        }
                        String path = mSelectedImageList.get(i).getPath();
                        if (!imageList.contains(path)) {
                            ImageModel imageModel = new ImageModel();
                            imageModel.setPath(imageList.get(i));
                            mSelectedImageList.add(imageModel);
                        }
                    }
                }
            }
            if (mSelectedImageList.size() > 9) {
                Toast.makeText(this, "最多只能选择9张图片", Toast.LENGTH_SHORT).show();
            } else {
                MessageEvent<ArrayList<ImageModel>> event = new MessageEvent<>();
                event.setCommand(command);
                event.setData(mSelectedImageList);
                EventBus.getDefault().post(event);
                finish();
            }
        }
    }

    private void selectAll(boolean selected) {
        for (ImageModel imageModel : mAdapterImageModelList) {
            imageModel.setSelected(selected);
            refreshAllPictureList(imageModel, selected);
        }

        mImageListAdapter.notifyDataSetChanged();
    }

    private void selectedCount() {
        int count = 0;
        for (ImageModel imageModel : mAdapterImageModelList) {
            boolean selected = imageModel.isSelected();
            if (selected) {
                count++;
            }
        }
        mFooterCenterTextTv.setText("已选择" + count + "项");
    }

    @Override
    public void onBackPressed() {
        if (mPreviewLayoutLl.getVisibility() == View.VISIBLE) {
            hidePreviewLayout();
        } else {
            super.onBackPressed();
        }
    }

    private void hidePreviewLayout() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, mPivotX + mItemViewWidth / 2, mPivotY + mItemViewHeight);
        scaleAnimation.setDuration(200);
        mPreviewLayoutLl.startAnimation(scaleAnimation);
        mPreviewLayoutLl.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int childCount = mMultipleImageAdapter.imageList.size();
        mPreviewCenterText.setText((position + 1) + "/" + childCount);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ImageListAdapter extends RecyclerView.Adapter<ImageListHolder> {

        @NonNull
        @Override
        public ImageListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.layout_image_list_item, null);
            return new ImageListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageListHolder holder, final int position) {
            String imagePath = mAdapterImageModelList.get(position).getPath();
            GlideApp.with(SelectPictureActivity.this).asBitmap().load(imagePath).override(500, 500).into(holder.mImageIv);
            final boolean selected = mAdapterImageModelList.get(position).isSelected();
            if (selected) {
                holder.mSelectedStateIv.setImageResource(R.drawable.ic_select);
            } else {
                holder.mSelectedStateIv.setImageResource(R.drawable.ic_not_select);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageModel = mAdapterImageModelList.get(position);
                    String path = mImageModel.getPath();
                    boolean selected1 = mImageModel.isSelected();
                    if (!selected1) {
                        GlideApp.with(SelectPictureActivity.this).asBitmap().load(path).into(mPreviewImageIv);
                        mPreviewMultipleImageVp.setVisibility(View.GONE);
                        mPreviewCenterText.setVisibility(View.INVISIBLE);
                        mPreviewRightText.setVisibility(View.VISIBLE);
                        mPreviewImageIv.setVisibility(View.VISIBLE);
                        mPreviewLayoutLl.setVisibility(View.VISIBLE);
                        mPivotX = v.getX();
                        mPivotY = v.getY();
                        mItemViewWidth = v.getMeasuredWidth();
                        mItemViewHeight = v.getMeasuredHeight();
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, mPivotX + mItemViewWidth / 2, mPivotY + mItemViewHeight);
                        scaleAnimation.setDuration(200);
                        mPreviewLayoutLl.startAnimation(scaleAnimation);
                    } else {
                        mImageModel.setSelected(false);
                        refreshAllPictureList(mImageModel, false);
                        mImageListAdapter.notifyDataSetChanged();
                    }
                    selectedCount();
                }
            });
            holder.mSelectedStateIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageModel = mAdapterImageModelList.get(position);
                    boolean selected1 = mImageModel.isSelected();
                    mImageModel.setSelected(!selected1);
                    refreshAllPictureList(mImageModel, !selected1);
                    mImageListAdapter.notifyDataSetChanged();
                    selectedCount();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mAdapterImageModelList.size();
        }
    }

    private void refreshAllPictureList(ImageModel comparedImageModel, boolean selected) {
        for (ImageModel imageModel : mAllImageModelList) {
            String id = imageModel.getId();
            if (TextUtils.equals(id, comparedImageModel.getId())) {
                imageModel.setSelected(selected);
            }
        }
    }

    private class ImageListHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageIv;
        private final ImageView mSelectedStateIv;

        public ImageListHolder(View itemView) {
            super(itemView);
            mImageIv = itemView.findViewById(R.id.iv_image_list_item_image);
            mSelectedStateIv = itemView.findViewById(R.id.iv_image_list_selected_state);
        }
    }

    private class MultipleImageAdapter extends PagerAdapter {
        private List<ImageModel> imageList;

        public MultipleImageAdapter(List<ImageModel> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_multiple_preview_item, null);
            ImageView imageView = view.findViewById(R.id.iv_multiple_preview_item_image);
            GlideApp.with(SelectPictureActivity.this).asBitmap().load(imageList.get(position).getPath()).into(imageView);
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
