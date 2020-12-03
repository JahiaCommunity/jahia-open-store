package org.jahiacommunity.jahiaopenstore.proxy;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.jahiacommunity.jahiaopenstore.GetFileQuery;
import org.jahiacommunity.jahiaopenstore.utils.GithubClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.jahiacommunity.jahiaopenstore.utils.Constants.*;
import static org.jahiacommunity.jahiaopenstore.utils.GithubClient.toCompletableFuture;

public class GithubProxy implements Controller {
    private static transient Logger logger = LoggerFactory.getLogger(GithubProxy.class);

    public GithubProxy() {
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("GET params; handleRequest");

        if (request.getMethod().equals("GET")) {
            String pkgName = request.getParameter("pkgName");
            logger.info("GET params; pkgName="+pkgName);
            List<String> pkgNamesList = Arrays.asList(new String[]{pkgName});
            String repoName = pkgName.substring(pkgName.lastIndexOf('.')+1);
            logger.info("GET params; repoName="+repoName);
            String versionNb = request.getParameter("versionNb");
            logger.info("GET params; versionNb="+versionNb);
            ApolloClient apolloClient = GithubClient.getClient();

            ApolloQueryCall<Optional<GetFileQuery.Data>> fileCall = apolloClient.query(new GetFileQuery(repoName, pkgNamesList, versionNb)).toBuilder().build();
            CompletableFuture<Response<Optional<GetFileQuery.Data>>> complFile = toCompletableFuture(fileCall);

            String downloadUrl = complFile.get().getData().get().getOrganization().get().getRepository().get().getPackages().getNodes().get().get(0).getVersion().get().getFiles().getNodes().get().get(0).getUrl().get().toString();
            logger.info("GET params; downloadUrl="+downloadUrl);

            HttpClient client = new HttpClient();
            GetMethod getMethod = new GetMethod(downloadUrl);
            //getMethod.addRequestHeader("Authorization", authHeader);
            int res = client.executeMethod(null, getMethod);
            if (res == 200) {
                Header[] arr$ = getMethod.getResponseHeaders();
                int len$ = arr$.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    Header header = arr$[i$];
                    response.setHeader(header.getName(), header.getValue());
                }

                IOUtils.copy(getMethod.getResponseBodyAsStream(), response.getOutputStream());
            } else {
                response.sendError(res);
            }
        }
        return null;
    }

}
