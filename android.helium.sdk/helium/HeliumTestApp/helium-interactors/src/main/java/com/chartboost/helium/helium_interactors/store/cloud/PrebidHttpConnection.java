package com.chartboost.helium.helium_interactors.store.cloud;

//
// Lot of code in here is based on the example here ->
// https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/guide/PostExample.java
//
public class PrebidHttpConnection implements Callable<String> {
    private String url_;
    private String httpMethod_;
    private URL usableUrl_;

    private PrebidHttpConnection() {this(null, null);}
    private PrebidHttpConnection(String url, String httpMethod) {
        url_ = url;
        httpMethod_ = httpMethod;
    }

    public static PrebidHttpConnection post(String url, String httpMethod) {
        return new PrebidHttpConnection(url, httpMethod);
    }
    @Override
    public String call() throws Exception {
        return synchronousConnect();
    }

    private String synchronousConnect() throws Exception {
        String stringizedJson = createBidRequest();
        return connectToPrebid(stringizedJson);
    }

    private String createBidRequest() {
        String bidRequest = RtbHelper.createBidRequest();
        return bidRequest;
    }

    private String connectToPrebid(String stringizedJson) throws IOException {
        if (usableUrl_ != null) {
            usableUrl_ = null; //help gc
        }

        Precondition.isNotNull(url_);
        try {
            usableUrl_ = new URL(url_);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final Request request = createRequest(stringizedJson);
        final OkHttpClient httpClient = createHttpClient();
        Precondition.isNotNull(httpClient);

        final Response response = httpClient.newCall(request).execute();
        if (response != null) {
            final String got = response.body().string();
            if (got != null) {
                System.out.println("got some response from prebid server");
                System.out.println("RESPONSE: " + got);
                return got;
            }
        }

        return null;
    }

    private Request createRequest(String stringizedJson) {
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        final RequestBody body = RequestBody.create(mediaType, stringizedJson);
        return new Request.Builder()
                        .url(usableUrl_)
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                            //TODO: add more headers
                        .post(body)
                        .build();
    }

    private OkHttpClient createHttpClient() {
         return new OkHttpClient.Builder()
             .readTimeout(3, TimeUnit.SECONDS)
             .connectTimeout(5, TimeUnit.SECONDS)
             .followRedirects(false)
             .build();
    }

}
