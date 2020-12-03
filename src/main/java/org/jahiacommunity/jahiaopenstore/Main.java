package org.jahiacommunity.jahiaopenstore;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    private static String authHeader = "Bearer 793692bbeb0f29f6ae0a47472a54b85c32651e2f";

    public static void main(String[] args) {

        /* // add dependancy:
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        */

        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization",  authHeader);
                    System.out.println("AUTH_TAG:"+authHeader);
                    return chain.proceed(builder.build());
                })
                .build();



        // First, create an `ApolloClient`
        // Replace the serverUrl with your GraphQL endpoint
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("https://api.github.com/graphql")
                .okHttpClient(client)
                .build();

        // Then enqueue your query


        // Then enqueue your query
        /*
        apolloClient.query(new LaunchDetailsQuery())
                .enqueue(new ApolloCall.Callback<Optional<LaunchDetailsQuery.Data>>() {
                    @Override
                    public void onResponse(@NotNull Response<Optional<LaunchDetailsQuery.Data>> response) {
                        System.out.println("Apollo >> Launch site: " + response.getData().get().getViewer().getLogin());
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println("Apollo >> Error:"+e);
                    }
                });
*/


/*
        CompletableFuture<Response<LaunchDetailsQuery.Data>> responseCompletableFuture = toCompletableFuture(
                new ApolloCall.Callback<Optional<LaunchDetailsQuery.Data>>() {
                    @Override
                    public void onResponse(@NotNull Response<Optional<LaunchDetailsQuery.Data>> response) {
                        System.out.println("Apollo >> Launch site: " + response.getData().get().getViewer().getLogin());
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println("Apollo >> Error:"+e);
                    }
                });
        );
        LaunchDetailsQuery.Repositories repositories;
        try {
            repositories = responseCompletableFuture.get().getData().getOrganization().get().getRepositories();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Repositories="+repositories);
        */
    }

    /*
    public static <T> CompletableFuture<Response<T>> toCompletableFuture(ApolloCall.Callback<Optional<LaunchDetailsQuery.Data>> apolloCall) {
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
*/


}
