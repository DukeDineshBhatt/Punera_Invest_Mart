package com.puneragroups.punerainvestmart;


import com.puneragroups.punerainvestmart.NOtificationPOJO.DataBean;
import com.puneragroups.punerainvestmart.NOtificationPOJO.ResultBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AllApiIneterface {


   /* @Multipart
    @POST("dev/bulk")
    Call<OtpBean> getOtp(
            @Part("sender_id") String sender_id,
            @Part("language") String language,
            @Part("route") String route,
            @Part("numbers") String numbers,
            @Part("message") String message,
            @Part("variables") String variables,
            @Part("variables_values") String variables_values,
            @Header("authorization") String authorization

    );*/

    @Headers({"Authorization:key=AAAAhYTx0hE:APA91bG24zffvXbxLuiT3CkjkUJXs8FfX5OIWmKzbzhCOMXEJgelmd0PSdQB_Lwdw8hTTy6TlRCOuCDX_JWKDRtKqHU-UwaC1i6wJg75Qo-YeSO61s0D5AGYPeliArPIz8Y8u5EDh1Y9",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResultBean> sendNotification(@Body DataBean requestNotificaton
    );

}

