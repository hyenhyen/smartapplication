package com.monad.kpu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*====================================
======================================
######### HttpAsync #########
======================================
====================================*/

public class HttpAsync {
    private static final String BASE_URL = "http://52.78.144.49/"; //서버 바뀐 부분 체크ㅇㅇ!

    private static AsyncHttpClient client = new AsyncHttpClient();

    //서버 데이터 받기
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getNoToken(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    //서버 데이터 보내기
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}


/* TODO 사용 예시

        RequestParams params = new RequestParams();
        params.put("chatId", 1);
        params.put("userId", 3);
        params.put("blahblah", "asdasdasd");
        File f = new File();
        params.put("file", file); //이러면 알아서 form-data 파일로 들어감 개꿀 ;

        //get이나 post두개있음 아무거나 써도 되는 서버임 이미지 올릴떈 무조건 post
        HttpAsync.get("/get_chat.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (statusCode == 200) {
                    try {
                        //이런식으로 파싱해서 쓰던가 직접 여기서 파싱해서 쓰던가
                        ChatModelData m = ParseModel.parseChatModel(response);
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // 에러 코드별로 잘 코딩
            }
        });


 */