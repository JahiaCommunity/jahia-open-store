package org.jahiacommunity.jahiaopenstore.utils;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import org.jahiacommunity.jahiaopenstore.LaunchDetailsQuery;
import org.jahiacommunity.jahiaopenstore.LaunchDetailsQuery.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static org.jahiacommunity.jahiaopenstore.utils.Constants.*;
import static org.jahiacommunity.jahiaopenstore.utils.GithubClient.toCompletableFuture;

public class GithubService {
    private static transient Logger logger = LoggerFactory.getLogger(GithubService.class);

    public static String getResponse(){

        String resp = "";

        ApolloClient apolloClient = GithubClient.getClient();
        ApolloQueryCall<Optional<LaunchDetailsQuery.Data>> call = apolloClient.query(new LaunchDetailsQuery()).toBuilder().build();

        CompletableFuture<Response<Optional<LaunchDetailsQuery.Data>>> compl = toCompletableFuture(call);
        try {

            JSONObject mainObj = new JSONObject();
            mainObj.put("id", "cc3e34b6-2a9c-4cec-8e71-972eae0448b2");
            mainObj.put("path", "/sites/jahiaopenstore/contents/modules-repository");
            mainObj.put("name", "modules-repository");
            mainObj.put("title", "Jahia Open App Store Modules Repository");

            JSONArray mainArray = new JSONArray();
            mainArray.add(mainObj);
            // ----------- end Main ------------ //

            Optional<LaunchDetailsQuery.Data> optionalData = compl.get().getData();
            if (optionalData.isPresent()) {
                LaunchDetailsQuery.Data data = optionalData.get();
                Organization organization = data.getOrganization().orElse(null);
                Repositories repos = (organization != null) ? organization.getRepositories() : null;
                List<Node> reposNodesList = (repos != null) ? repos.getNodes().get() :  Collections.<Node>emptyList();

                JSONArray modulesList = new JSONArray();
                for (Node repoNode : reposNodesList){
                    // "name": artifactId; ex: "ml-image-auto"
                    String repoId = repoNode.getId();
                    String repoName = repoNode.getName();
                    String resourcePath = repoNode.getResourcePath().toString();
                    String repoDesc = repoNode.getDescription().orElse("");
                    String repoUrl = repoNode.getUrl().toString();

                    List<Node1> packagesList = repoNode.getPackages().getNodes().orElse(null);
                    for (Node1 pkg : packagesList){
                        JSONObject module = new JSONObject();
                        module.put("id", repoId);
                        module.put("path", resourcePath);
                        module.put("jcrprimarytype", "jnt:forgeModule");
                        module.put("remoteUrl", repoUrl);
                        //"name": groupID.artifactID; ex: "org.jahiacommunity.modules.ml-image-auto"
                        String pkgName = pkg.getName();
                        int ind = pkgName.lastIndexOf(repoName);
                        if (ind > 1) {
                            String groupId = pkgName.substring(0, ind-1);
                            module.put("groupId", groupId);
                        }else {
                            module.put("groupId", "");
                        }
                        module.put("name", repoName);
                        module.put("title", repoName);
                        module.put("icon", "");

                        LatestVersion vers = pkg.getLatestVersion().orElse(null);
                        if (vers != null) {
                            String versNb = vers.getVersion();
                            JSONObject version = new JSONObject();
                            version.put("version", versNb);
                            version.put("requiredVersion", "version-7.0.0.0");
                            Files files = vers.getFiles();
                            if (files != null) {
                                List<Node2> filesNodes = files.getNodes().orElse(null);
                                if (filesNodes != null) {
                                    Node2 file = filesNodes.get(0);
                                    //String downloadUrl = file.getUrl().get().toString();
                                    String downloadUrl = PROXY_SERVER_URL+"?pkgName="+pkgName+"&repoName="+repoName+"&versionNb="+versNb ;
                                    version.put("downloadUrl", downloadUrl);
                                }
                            }
                            JSONArray versions = new JSONArray();
                            versions.add(version);
                            module.put("versions", versions);
                        }
                        modulesList.add(module);
                    }
                }

                mainObj.put("modules", modulesList);
            }
            resp = mainArray.toJSONString();
        } catch(InterruptedException e){
            e.printStackTrace();
        } catch(ExecutionException e){
            e.printStackTrace();
        }
        return resp;
    }




}
