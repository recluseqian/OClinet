package com.recluse.oclient.ui.viewholderfactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.recluse.base.model.event.ClickEvent;
import com.recluse.base.utils.DisplayUtils;
import com.recluse.base.utils.TimerUtils;
import com.recluse.base.view.activity.BaseAppCompatActivity;
import com.recluse.base.view.listview.BaseRecyclerViewHolder;
import com.recluse.base.view.listview.BaseViewHolderFactory;
import com.recluse.oclient.R;
import com.recluse.oclient.StartActivityUtils;
import com.recluse.oclient.data.SubscribeModuleInfo;
import com.recluse.oclient.data.VideoDetailInfo;
import com.recluse.oclient.data.VideoDetailItemInfo;
import com.recluse.oclient.data.VideoDetailSubItemInfo;
import com.recluse.oclient.ui.viewholder.VideoDetailSubItemVH;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoDetailSubItemFactory extends BaseViewHolderFactory<VideoDetailSubItemInfo<?>>{

    private static final String TAG = "VideoSubItemFactory";

    private static final int VIEW_TYPE_VIDEO_LIST = 0;
    private static final int VIEW_TYPE_SUBSCRIBE_RECOM_LIST = 1;
    private static final int VIEW_TYPE_VIDEO_RECOM_LIST = 2;

    private static final int TYPE_COUNT = 3;

    public VideoDetailSubItemFactory(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getViewType(VideoDetailSubItemInfo<?> data, int position) {
        if (data != null) {
            int type = 0;
            switch (data.type) {
                case VideoDetailItemInfo.Type.VIDEO_LIST:
                    type = VIEW_TYPE_VIDEO_LIST;
                    break;
                case VideoDetailItemInfo.Type.SUBSCRIBE_RECOM_LIST:
                    type = VIEW_TYPE_SUBSCRIBE_RECOM_LIST;
                    break;
                case VideoDetailItemInfo.Type.VIDEO_RECOM_LIST:
                    type = VIEW_TYPE_VIDEO_RECOM_LIST;
                    break;
                default:
                    return super.getViewType(data, position);
            }
            return getBaseType() + type;
        }
        return super.getViewType(data, position);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (inflater == null) {
            Log.e(TAG, "createViewHolder: inflater is null");
            return null;
        }
        View view;
        switch (viewType - getBaseType()) {
            case VIEW_TYPE_VIDEO_LIST:
                view = inflater.inflate(R.layout.video_horizontal_list_item_layout, parent, false);
                return new VideoDetailSubItemVH.VideoListSubItemVH(view);
            case VIEW_TYPE_SUBSCRIBE_RECOM_LIST:
                view = inflater.inflate(R.layout.video_horizontal_subscribe_recom_item_layout, parent, false);
                return new VideoDetailSubItemVH.SubscribeListSubItemVH(view);
            case VIEW_TYPE_VIDEO_RECOM_LIST:
                view = inflater.inflate(R.layout.video_recom_sub_item_layout, parent, false);
                return new VideoDetailSubItemVH.VideoRecomSubItemVH(view);
            default:
                return null;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }
}
