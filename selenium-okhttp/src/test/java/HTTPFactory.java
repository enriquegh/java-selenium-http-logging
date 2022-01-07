import com.google.common.base.Strings;
import okhttp3.ConnectionPool;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.internal.OkHttpClient;

import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * This is grabbed from
 * https://github.com/SeleniumHQ/selenium/blob/selenium-3.141.59/java/client/src/org/openqa/selenium/remote/internal/OkHttpClient.java#L114-L168
 *
 * Main addition is using the HttpLoggingInterceptor
 */
public class HTTPFactory implements HttpClient.Factory {

    private final ConnectionPool pool = new ConnectionPool();
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    @Override
    public HttpClient.Builder builder() {

        return new HttpClient.Builder() {
            @Override
            public HttpClient createClient(URL url) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                okhttp3.OkHttpClient.Builder client = new okhttp3.OkHttpClient.Builder()
                        .connectionPool(pool)
                        .followRedirects(true)
                        .followSslRedirects(true)
                        .proxy(proxy)
                        .readTimeout(readTimeout.toMillis(), MILLISECONDS)
                        .connectTimeout(connectionTimeout.toMillis(), MILLISECONDS)
                        .addNetworkInterceptor(logging);

                String info = url.getUserInfo();
                if (!Strings.isNullOrEmpty(info)) {
                    String[] parts = info.split(":", 2);
                    String user = parts[0];
                    String pass = parts.length > 1 ? parts[1] : null;

                    String credentials = Credentials.basic(user, pass);

                    client.authenticator((route, response) -> {
                        if (response.request().header("Authorization") != null) {
                            return null; // Give up, we've already attempted to authenticate.
                        }

                        return response.request().newBuilder()
                                .header("Authorization", credentials)
                                .build();
                    });
                }

                client.addNetworkInterceptor(chain -> {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    return response.code() == 408
                            ? response.newBuilder().code(500).message("Server-Side Timeout").build()
                            : response;
                });

                return new OkHttpClient(client.build(), url);
            }
        };
    }

    @Override
    public void cleanupIdleClients() {
        pool.evictAll();
    }
}
