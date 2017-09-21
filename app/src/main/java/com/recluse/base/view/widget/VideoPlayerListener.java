package com.recluse.base.view.widget;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public interface VideoPlayerListener {

    void onPrepared(IMediaPlayer player);

    void onBufferingUpdate(IMediaPlayer mp, int percent);

    void onComplete(IMediaPlayer player);

    void onError(IMediaPlayer player, int frameErr, int implErr);
}
