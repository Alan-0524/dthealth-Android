package com.dthealth;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRetrofit() {
//        userRetrofitService = RetrofitUtils.getInstance(userRetrofitService);
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("page", "1");
//        parameters.put("rows", "20");
//        Call<List<User>> call = userRetrofitService.findAllByPage(parameters);
//        try {
//            call.enqueue(new Callback<List<User>>() {
//                @Override
//                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<List<User>> call, Throwable t) {
//
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
//        WebSocketClientApi.getInstance().sendText("ssssssssssssss");
//        try (Response response = client.newCall(reqBuild.build()).execute()) {
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}