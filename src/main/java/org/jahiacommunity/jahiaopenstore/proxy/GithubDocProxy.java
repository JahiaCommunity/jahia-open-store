package org.jahiacommunity.jahiaopenstore.proxy;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.jahiacommunity.jahiaopenstore.utils.Constants.GITHUB_REPO_URL;

public class GithubDocProxy implements Controller {
    private static transient Logger logger = LoggerFactory.getLogger(GithubDocProxy.class);
    public static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String CROSS_ORIGIN_RESOURCE_POLICY = "Cross-Origin-Resource-Policy";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

    public GithubDocProxy() {
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("GET params; handleRequest");

        if (request.getMethod().equals("GET")) {
            String repoName = request.getParameter("repoName");
            logger.debug("GET params; repoName="+repoName);

            String githubDocUrl = GITHUB_REPO_URL + repoName;

            HttpClient client = new HttpClient();
            GetMethod getMethod = new GetMethod(githubDocUrl);
            //getMethod.addRequestHeader("Authorization", authHeader);
            int res = client.executeMethod(null, getMethod);
            if (res == 200) {
                Header[] arr$ = getMethod.getResponseHeaders();
                int len$ = arr$.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    Header header = arr$[i$];
                    response.setHeader(header.getName(), header.getValue());
                    response.setHeader(CONTENT_SECURITY_POLICY_HEADER, "frame-ancestors 'self' http://github.com ");
                    response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*" );
                    response.setHeader(CROSS_ORIGIN_RESOURCE_POLICY, "cross-origin");
                    response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                }

                IOUtils.copy(getMethod.getResponseBodyAsStream(), response.getOutputStream());
            } else {
                response.sendError(res);
            }
        }
        return null;
    }

}
