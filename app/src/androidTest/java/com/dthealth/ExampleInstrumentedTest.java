package com.dthealth;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dthealth", appContext.getPackageName());
    }

    @Test
    public void testRetrofit(){
//        userRetrofitService = RetrofitUtils.getInstance(userRetrofitService);
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("page", "1");
//        parameters.put("rows", "20");
//        Call<List<User>> call = userRetrofitService.findAllByPage(parameters);
//        try {
//            call.enqueue(new Callback<List<User>>() {
//                @Override
//                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                    System.out.println(response.toString());
//                }
//
//                @Override
//                public void onFailure(Call<List<User>> call, Throwable t) {
//                    System.out.println("-----------------------------"+t.getMessage());
//                }
//            });
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        try {
//            Response<List<User>> list = call.execute();
//            System.out.println(list.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    @Test
    public void testOkHttp() {
//        Request.Builder reqBuild = new Request.Builder();
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://localhost:8082/").newBuilder();
//        urlBuilder.addPathSegments("user/findAllByPage");
//        urlBuilder.addQueryParameter("page", "1").addQueryParameter("rows","5");
//        reqBuild.url(urlBuilder.build());
////        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
//        try {
//            Response response = OkHttpClientApi.getInstance()
//                    .setPath("handshake")
//                    .get();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        WebSocketClientApi.getInstance();
//        try (Response response = client.newCall(reqBuild.build()).execute()) {
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
