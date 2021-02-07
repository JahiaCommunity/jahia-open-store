package org.jahiacommunity.jahiaopenstore.utils;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;

import static org.jahiacommunity.jahiaopenstore.utils.Constants.*;

public class GithubClient {
    private static transient Logger logger = LoggerFactory.getLogger(GithubClient.class);

    public static ApolloClient getClient() {
         HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
         loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization", AUTH_HEADER);
                    logger.debug("AUTH_TAG:" + AUTH_HEADER);
                    return chain.proceed(builder.build());
                })
                .build();

        // First, create an `ApolloClient`
        // Replace the serverUrl with your GraphQL endpoint
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(API_URL)
                .okHttpClient(client)
                .build();

        return apolloClient;
    }


    public static <T> CompletableFuture<Response<T>> toCompletableFuture(ApolloCall<T> apolloCall) {
        CompletableFuture<Response<T>> completableFuture = new CompletableFuture<>();

        completableFuture.whenComplete((tResponse, throwable) -> {
            if (completableFuture.isCancelled()) {
                completableFuture.cancel(true);
            }
        });

        apolloCall.enqueue(new ApolloCall.Callback<T>() {
            @Override
            public void onResponse(@NotNull Response<T> response) {
                completableFuture.complete(response);
            }
            @Override
            public void onFailure(@NotNull ApolloException e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }
}
