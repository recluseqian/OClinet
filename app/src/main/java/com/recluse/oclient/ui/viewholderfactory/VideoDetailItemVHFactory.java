package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.data.VideoDetailItemInfo;
import com.recluse.oclient.ui.viewholder.VideoDetailItemVH;

public class VideoDetailItemVHFactory extends BaseViewHolderFactory<VideoDetailItemInfo<?>> {

    private static final String TAG = "VideoDetailVHFactory";

    private static final int VIEW_TYPE_SUBSCRIBE_INTRO = 0;
    private static final int VIEW_TYPE_VIDEO_INFO = 1;
    private static final int VIEW_TYPE_HORIZONTAL_LIST = 2;
    private static final int VIEW_TYPE_VERTICAL_LIST = 3;
    private static final int VIEW_TYPE_PLACE_HOLDER = 4;

    private static final int TYPE_COUNT = 5;

    public VideoDetailItemVHFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(VideoDetailItemInfo<?> data, int position) {

        if (data != null) {
            int type;
            if (data.data == null && position == 0) {
                type = VIEW_TYPE_PLACE_HOLDER;
            } else {
                switch (data.type) {
                    case VideoDetailItemInfo.Type.VIDEO_DETAIL_INFO:
                        type = VIEW_TYPE_VIDEO_INFO;
                        break;
                    case VideoDetailItemInfo.Type.SUBSCRIBE_INFO:
                        type = VIEW_TYPE_SUBSCRIBE_INTRO;
                        break;
                    case VideoDetailItemInfo.Type.VIDEO_LIST:
                    case VideoDetailItemInfo.Type.SUBSCRIBE_RECOM_LIST:
                        type = VIEW_TYPE_HORIZONTAL_LIST;
                        break;
                    case VideoDetailItemInfo.Type.VIDEO_RECOM_LIST:
                        type = VIEW_TYPE_VERTICAL_LIST;
                        break;
                    default:
                        return super.getViewType(data, position);
                }
            }
            return type + getBaseType();
        }
        return super.getViewType(data, position);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (inflater == null) {
            Log.e(TAG, "The inflater is null");
            return null;
        }

        View view;
        switch (viewType - getBaseType()) {
            case VIEW_TYPE_SUBSCRIBE_INTRO:
                view = inflater.inflate(R.layout.video_detail_subscribe_header_layout, parent, false);
                return new VideoDetailItemVH.VideoSubscribeInfoVH(view);
            case VIEW_TYPE_VIDEO_INFO:
                view = inflater.inflate(R.layout.video_detail_description_layout, parent, false);
                return new VideoDetailItemVH.VideoDetailInfoVH(view);
            case VIEW_TYPE_HORIZONTAL_LIST:
                view = inflater.inflate(R.layout.nested_list_layout, parent, false);
                return new VideoDetailItemVH.VideoHorizontalRecomListVH(view);
            case VIEW_TYPE_VERTICAL_LIST:
                view = inflater.inflate(R.layout.nested_list_layout, parent, false);
                return new VideoDetailItemVH.VideoVerticalRecomListVH(view);
            case VIEW_TYPE_PLACE_HOLDER:
                view = inflater.inflate(R.layout.place_holder_layout, parent, false);
                return new VideoDetailItemVH.PlaceHolderVH(view);
            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

}
