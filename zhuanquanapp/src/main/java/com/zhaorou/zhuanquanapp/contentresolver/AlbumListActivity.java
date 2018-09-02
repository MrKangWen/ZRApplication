package com.zhaorou.zhuanquanapp.contentresolver;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaorou.zhuanquanapp.R;
import com.zhaorou.zhuanquanapp.base.GlideApp;
import com.zhaorou.zhuanquanapp.recyclerview.CustomItemDecoration;
import com.zhaorou.zhuanquanapp.recyclerview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumListActivity extends AppCompatActivity implements CustomRecyclerView.OnItemClickListener, View.OnClickListener{
    private ImageView mBtnGoBack;
    private TextView mTitleCenterTextTv;
    private CustomRecyclerView mAlbumListRv;
    private LinearLayoutManager mLayoutManager;
    private Map<String, ArrayList<ImageModel>> mAlbumListMap = new HashMap<>();
    private List<String> mBucketNameList;
    private AlbumListAdapter mAlbumListAdapter;
    private ArrayList<ImageModel> mImageModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        getSupportActionBar().hide();

        initTitleViews();
        initAlbumListRv();
        initData();
    }
    private void initData() {
        Intent intent = getIntent();
        mImageModelList = intent.getParcelableArrayListExtra(SelectPictureActivity.RESULT_DATA);
        mBucketNameList = new ArrayList<>();
        mBucketNameList.add("所有");
        for (ImageModel imageModel : mImageModelList) {
            String bucketName = imageModel.getBucket_display_name();
            if (!mBucketNameList.contains(bucketName)) {
                mBucketNameList.add(bucketName);
            }
        }

        for (String bucketName : mBucketNameList) {
            if (TextUtils.equals(bucketName, "所有")) {
                mAlbumListMap.put(bucketName, mImageModelList);
            } else {
                ArrayList<ImageModel> list = new ArrayList<>();
                for (ImageModel imageModel : mImageModelList) {
                    String bucket_display_name = imageModel.getBucket_display_name();
                    if (TextUtils.equals(bucket_display_name, bucketName)) {
                        list.add(imageModel);
                    }
                }
                mAlbumListMap.put(bucketName, list);
            }
        }
        mAlbumListAdapter.notifyDataSetChanged();
    }

    private void initAlbumListRv() {
        mAlbumListRv = findViewById(R.id.rv_album_list_activity_album_list);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAlbumListRv.setLayoutManager(mLayoutManager);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(1, Color.parseColor("#E5E5E5"));
        mAlbumListRv.addItemDecoration(itemDecoration);
        mAlbumListAdapter = new AlbumListAdapter();
        mAlbumListRv.setAdapter(mAlbumListAdapter);
        mAlbumListRv.setOnItemClickListener(this);
    }

    private void initTitleViews() {
        mBtnGoBack = findViewById(R.id.iv_layout_title_left_icon);
        mBtnGoBack.setImageResource(R.drawable.ic_back);
        mBtnGoBack.setVisibility(View.VISIBLE);
        mBtnGoBack.setOnClickListener(this);

        mTitleCenterTextTv = findViewById(R.id.tv_layout_title_center_text);
        mTitleCenterTextTv.setText("选择相册");

    }

    @Override
    public void OnItemClick(ViewGroup parent, View view, int position) {
        String bucketName = mBucketNameList.get(position);
        ArrayList<ImageModel> imageModelList = mAlbumListMap.get(bucketName);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SelectPictureActivity.RESULT_DATA, imageModelList);
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_layout_title_left_icon) {
            finish();
        }
    }

    private class AlbumListAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

        @NonNull
        @Override
        public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_album_list, parent, false);
            return new AlbumViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
            String bucketName = mBucketNameList.get(position);
            holder.albumNameTv.setText(bucketName);

            ArrayList<ImageModel> imageModelList = mAlbumListMap.get(bucketName);
            if (imageModelList != null && imageModelList.size() > 0) {
                String imagePath = imageModelList.get(0).getPath();
                GlideApp.with(AlbumListActivity.this).asBitmap().thumbnail(0.8f)
                        .load(imagePath).into(holder.albumThumbIv);
                holder.pictureCountTv.setText("" + imageModelList.size());
            }
        }

        @Override
        public int getItemCount() {
            return mBucketNameList.size();
        }
    }

    private class AlbumViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumThumbIv;
        private TextView albumNameTv;
        private TextView pictureCountTv;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            albumThumbIv = itemView.findViewById(R.id.iv_thumb_item_album_list);
            albumNameTv = itemView.findViewById(R.id.tv_album_name_item_album_list);
            pictureCountTv = itemView.findViewById(R.id.tv_picture_count_item_album_list);
        }
    }
}
