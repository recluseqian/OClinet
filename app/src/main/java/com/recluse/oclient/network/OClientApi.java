package com.recluse.oclient.network;

import com.recluse.oclient.data.BaseDataEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OClientApi {

    String HOME_PAGE_MODULE = "/open/mob/module/home.do";
    String HOME_PAGE_BANNER_INFO = "";
    String SUBSCRIBE_PAGE_INFO = "/open/mob/subscribe/home.do";
    String SUBSCRIBE_BANNER_INFO = "open/mob/subscribe/banner.do";
    String VIDEO_RECOMMEND_INFO = "/mob/{plid}/getMoviesForAndroid.do";
    String VIDEO_SUBSCRIBE_RECOMMEND = "/open/mob/subscribe/detail/relate.do";

    String AGENT_HEADER = "User-Agent: NETS_Android";
    String COOKIE_HEADER = "Cookie: ntes_open_client_i=android#5.1.3#de701f3637ddfe2b11b22b22db9b6c6413dc2370#v7_1wan#6.0";
    String TOKEN_HEADER = "mob-token: 56d054805dd4ba1c4dfc69c8ac3309fe60b433af7a85508f36d9a2fed9df08f054e66aad739173e4e5cf96fb76749c2bfe09682e6e112ee8f27a62be4dfeb2cc4f207ca775f24ec77cfa8c26340bc7bfac8be6e1ba318dfbbcf25e4d6a5403edd677533282a681eac2ae33622b670eb211d8b17647947328599231e566bc5e513fa3c09029523360055dba2b34db1786";

    String BANNER_TYPES = "2,3,4,5,6,8,9,10,11,12,88,100,120";
    String VIDEO_SUBSCRIBE_R_TYPES = "2,3,4,5,6,8,9,10,11,12";

    @Headers({AGENT_HEADER,
            COOKIE_HEADER,
            TOKEN_HEADER})
    @GET(OClientApi.HOME_PAGE_MODULE)
    Observable<BaseDataEntity.HomeModuleEntity> getHomePageModule();

    @Headers({AGENT_HEADER,
            COOKIE_HEADER,
            TOKEN_HEADER})
    @GET(OClientApi.SUBSCRIBE_PAGE_INFO)
    Observable<BaseDataEntity.SubscribeEntity> getSubscribePageInfo(
            @Query("cursor") String cursor,
            @Query("pagesize") int pageSize);

    @Headers({AGENT_HEADER,
            COOKIE_HEADER,
            TOKEN_HEADER})
    @GET(OClientApi.SUBSCRIBE_BANNER_INFO)
    Observable<BaseDataEntity.BannerInfoEntity> getBannerInfo(
            @Query("position") int position,
            @Query("rtypes") String rTypes);

    @Headers({AGENT_HEADER,
            COOKIE_HEADER,
            TOKEN_HEADER})
    @GET(OClientApi.VIDEO_RECOMMEND_INFO)
    Observable<BaseDataEntity.VideoDetailEntity> getVideoDetail(@Path("plid") String plid);

    @Headers({AGENT_HEADER,
            COOKIE_HEADER,
            TOKEN_HEADER})
    @GET(OClientApi.VIDEO_SUBSCRIBE_RECOMMEND)
    Observable<BaseDataEntity.VideoSubscribeEntity> getVideoSubscribe(
            @Query("mid") String mid,
            @Query("rtypes") String rTypes);


}

