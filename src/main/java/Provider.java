import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Provides data related to countries.
 */
public abstract class Provider {


    /**
     * Provides a specific data related to countries.
     */
    public abstract void provide();


    /**
     * Gets the data from the given url.
     */
    protected String fetchData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            if (response.body() != null)
                return response.body().string();
            else
                throw new IOException("response.body() is null!");
        }
    }


}
