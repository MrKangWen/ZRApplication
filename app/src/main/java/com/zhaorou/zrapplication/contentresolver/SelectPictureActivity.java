package com.zhaorou.zrapplication.contentresolver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaorou.zrapplication.R;
import com.zhaorou.zrapplication.base.GlideApp;
import com.zhaorou.zrapplication.widget.recyclerview.CustomItemDecoration;
import com.zhaorou.zrapplication.widget.recyclerview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectPictureActivity extends AppCompatActivity implements CustomRecyclerView.OnItemClickListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "SelectPictureActivity";
    public static final String RESULT_DATA = "com.lykevin.selectpicture.result_data";
    private TextView mTitleCenterTextTv;
    private TextView mTitleRightTextTv;
    private CustomRecyclerView mImageListRv;
    private GridLayoutManager mImageListRvManager;
    private ImageListAdapter mImageListAdapter;
    private List<ImageModel> mImageModelList = new ArrayList<>();
    private LinearLayout mPreviewLayoutLl;
    private ImageView mPreviewImageIv;
    private float mPivotX;
    private float mPivotY;
    private float mItemViewWidth;
    private float mItemViewHeight;
    private TextView mTitleLeftText;
    private ImageModel mImageModel;
    private TextView mFooterCenterTextTv;
    private ViewPager mPreviewMultipleImageVp;
    private TextView mPreviewCenterText;
    private TextView mPreviewRightText;
    private MultipleImageAdapter mMultipleImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_picture);
        getSupportActionBar().hide();

        mTitleLeftText = findViewById(R.id.tv_layout_title_left_text);
        mTitleLeftText.setText("取消");
        mTitleLeftText.setVisibility(View.VISIBLE);

        mTitleCenterTextTv = findViewById(R.id.tv_layout_title_center_text);
        mTitleCenterTextTv.setText("选择图片");

        mTitleRightTextTv = findViewById(R.id.tv_layout_title_right_text);
        mTitleRightTextTv.setText("全选");
        mTitleRightTextTv.setVisibility(View.VISIBLE);

        mFooterCenterTextTv = findViewById(R.id.tv_layout_footer_center_text);

        mImageListRv = findViewById(R.id.rv_image_list);
        mImageListRvManager = new GridLayoutManager(this, 4);
        mImageListRv.setLayoutManager(mImageListRvManager);
        mImageListAdapter = new ImageListAdapter();
        mImageListRv.setAdapter(mImageListAdapter);
        CustomItemDecoration customItemDecoration = new CustomItemDecoration(4, Color.WHITE);
        mImageListRv.addItemDecoration(customItemDecoration);
        mImageListRv.setOnItemClickListener(this);

        mPreviewCenterText = findViewById(R.id.tv_image_preview_center_text);
        mPreviewRightText = findViewById(R.id.tv_image_preview_right_text);
        mPreviewLayoutLl = findViewById(R.id.ll_layout_image_preview);
        mPreviewImageIv = findViewById(R.id.iv_image_preview_image);
        mPreviewMultipleImageVp = findViewById(R.id.vp_image_preview_multiple_image);

        List<ImageModel> imageModelList = ContentResolverHelper.queryImagesFromExternal(this);
        mImageModelList.addAll(imageModelList);
        mImageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemClick(ViewGroup parent, View view, int position) {
        mImageModel = mImageModelList.get(position);
        boolean selected = mImageModel.isSelected();
        if (!selected) {
            String imagePath = mImageModel.getPath();
            GlideApp.with(SelectPictureActivity.this).asBitmap().load(imagePath).into(mPreviewImageIv);
            mPreviewMultipleImageVp.setVisibility(View.GONE);
            mPreviewCenterText.setVisibility(View.INVISIBLE);
            mPreviewRightText.setVisibility(View.VISIBLE);
            mPreviewImageIv.setVisibility(View.VISIBLE);
            mPreviewLayoutLl.setVisibility(View.VISIBLE);
            mPivotX = view.getX();
            mPivotY = view.getY();
            mItemViewWidth = view.getMeasuredWidth();
            mItemViewHeight = view.getMeasuredHeight();
            ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, mPivotX + mItemViewWidth / 2, mPivotY + mItemViewHeight);
            scaleAnimation.setDuration(200);
            mPreviewLayoutLl.startAnimation(scaleAnimation);
        } else {
            mImageModel.setSelected(false);
            mImageListAdapter.notifyDataSetChanged();
        }
        selectedCount();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_layout_title_left_text) {
            selectAll(false);
            selectedCount();
        }
        if (v.getId() == R.id.tv_layout_title_right_text) {
            selectAll(true);
            selectedCount();
        }
        if (v.getId() == R.id.iv_image_preview_left_icon) {
            hidePreviewLayout();
        }
        if (v.getId() == R.id.tv_image_preview_right_text) {
            mImageModel.setSelected(true);
            mImageListAdapter.notifyDataSetChanged();
            hidePreviewLayout();
            selectedCount();
        }
        if (v.getId() == R.id.tv_layout_footer_left_text) {
            List<ImageModel> selectedImageList = new ArrayList<>();
            for (ImageModel imageModel : mImageModelList) {
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
        if (v.getId() == R.id.tv_layout_footer_right_text) {
            ArrayList<ImageModel> selectedImageList = new ArrayList<>();
            for (ImageModel imageModel : mImageModelList) {
                boolean selected = imageModel.isSelected();
                if (selected && !selectedImageList.contains(imageModel)) {
                    selectedImageList.add(imageModel);
                }
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RESULT_DATA, selectedImageList);
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }

    private void selectAll(boolean b) {
        for (ImageModel imageModel : mImageModelList) {
            imageModel.setSelected(b);
        }
        mImageListAdapter.notifyDataSetChanged();
    }

    private void selectedCount() {
        int count = 0;
        for (ImageModel imageModel : mImageModelList) {
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
        public void onBindViewHolder(@NonNull ImageListHolder holder, int position) {
            String imagePath = mImageModelList.get(position).getPath();
            GlideApp.with(SelectPictureActivity.this).asBitmap().load(imagePath).override(500, 500).into(holder.mImageIv);
            boolean selected = mImageModelList.get(position).isSelected();
            if (selected) {
                holder.mSelectedStateIv.setImageResource(R.mipmap.ic_select);
            } else {
                holder.mSelectedStateIv.setImageResource(R.mipmap.ic_not_select);
            }
        }

        @Override
        public int getItemCount() {
            return mImageModelList.size();
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
            GlideApp.with(SelectPictureActivity.this).asBitmap().load(imageList.get(position).getPath()).override(500, 500).into(imageView);
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
