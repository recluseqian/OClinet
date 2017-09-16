package com.recluse.oclient.network;

import com.recluse.oclient.data.ModuleEntity;
import com.recluse.oclient.data.SubscribeEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by recluse on 17-9-13.
 */

public interface OClientApi {

    String HOME_PAGE_MODULE = "/open/mob/module/home.do";
    String SUBSCRIBE_PAGE_INFO = "/open/mob/subscribe/home.do";

    @Headers({"User-Agent: NETS_Android",
            "Cookie: ntes_open_client_i=android#5.1.3#de701f3637ddfe2b11b22b22db9b6c6413dc2370#v7_1wan#6.0",
            "mob-token: 56d054805dd4ba1c4dfc69c8ac3309fe60b433af7a85508f36d9a2fed9df08f054e66aad739173e4e5cf96fb76749c2bfe09682e6e112ee8f27a62be4dfeb2cc4f207ca775f24ec77cfa8c26340bc7bfac8be6e1ba318dfbbcf25e4d6a5403edd677533282a681eac2ae33622b670eb211d8b17647947328599231e566bc5e513fa3c09029523360055dba2b34db1786",
            "Host: c.open.163.com"})
    @GET(OClientApi.HOME_PAGE_MODULE)
    Observable<ModuleEntity> getHomePageModule();

    @Headers({"User-Agent: NETS_Android",
            "Cookie: ntes_open_client_i=android#5.1.3#de701f3637ddfe2b11b22b22db9b6c6413dc2370#v7_1wan#6.0",
            "mob-token: 56d054805dd4ba1c4dfc69c8ac3309fe60b433af7a85508f36d9a2fed9df08f054e66aad739173e4e5cf96fb76749c2bfe09682e6e112ee8f27a62be4dfeb2cc4f207ca775f24ec77cfa8c26340bc7bfac8be6e1ba318dfbbcf25e4d6a5403edd677533282a681eac2ae33622b670eb211d8b17647947328599231e566bc5e513fa3c09029523360055dba2b34db1786",
            "Host: c.open.163.com"})
    @GET(OClientApi.SUBSCRIBE_PAGE_INFO)
    Observable<SubscribeEntity> getSubscribePageInfo(
            @Query("cursor") String cursor,
            @Query("pagesize") int pageSize);
}