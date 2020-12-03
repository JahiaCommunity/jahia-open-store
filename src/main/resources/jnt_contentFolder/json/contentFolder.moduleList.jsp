<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="functions" uri="http://www.jahia.org/tags/functions" %>
<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="github" uri="http://www.jahiacommunity.org/tags/githubLib" %>


${github:response()}

<%--
<json:array>
    <json:object>
        <json:property name="id" value="cc3e34b6-2a9c-4cec-8e71-972eae0448b2"/>
        <json:property name="path" value="/sites/private-app-store/contents/modules-repository"/>
        <json:property name="name" value="modules-repository"/>
        <json:property name="title" value="Private App Store Modules Repository"/>
        <json:array name="modules">
            <c:forEach items="${github:response()}" var="child">
                    <c:set var="versions" value="${jcr:getChildrenOfType(child, 'jnt:forgePackageVersion')}"/>
                    <c:set var="groupID" value="${'package'}"/>
                    <c:url var="downloadUrl" value="${url.server}${url.context}${url.files}${file.path}" context="/"/>

                    <c:if test="${child.properties.published.boolean}">
                        <c:remove var="iconFolder"/>
                        <c:remove var="icon"/>

                        <jcr:node var="iconFolder" path="${child.path}/icon"/>
                        <c:forEach var="iconItem" items="${iconFolder.nodes}">
                            <c:set var="icon" value="${iconItem}"/>
                        </c:forEach>
                        <json:object>
                            <json:property name="id" value="${child.identifier}"/>
                            <json:property name="path" value="${child.path}"/>
                            <json:property name="jcrprimarytype" value="${child.properties['jcr:primaryType'].string}"/>
                            <c:url context="/" var="localUrl" value="${url.server}${child.url}">
                                <c:param name="dx" value="true"/>
                            </c:url>
                            <c:if test="${!fn:contains(localUrl, 'https') && !fn:contains(localUrl, 'localhost')}">
                                <c:set var="localUrl" value="${fn:replace(localUrl, 'http', 'https')}"/>
                            </c:if>
                            <json:property name="remoteUrl" value="${localUrl}"/>
                            <json:property name="groupId" value="${groupID}"/>
                            <json:property name="name" value="${child.name}"/>
                            <json:property name="title" value="${child.displayableName}"/>
                            <c:if test="${not empty icon}">
                                <json:property name="icon" value="${url.server}${icon.url}"/>
                            </c:if>
                            <json:array name="versions">
                                <c:forEach var="version" items="${versions}">
                                    <c:if test="${version.properties.published.boolean and child.properties.published.boolean}">
                                        <json:object>
                                            <json:property name="version"
                                                           value="${version.properties.versionNumber.string}"/>
                                            <json:property name="requiredVersion"
                                                           value="${version.properties.requiredVersion.node.name}"/>
                                            <c:choose>
                                                <c:when test="${child.properties['jcr:primaryType'].string eq 'jnt:forgeModule'}">
                                                    <c:set var="downloadUrl" value="${version.properties.url.string}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="files" value="${jcr:getChildrenOfType(version, 'jnt:file')}"/>
                                                    <c:forEach var="file" items="${files}">
                                                        <c:url var="downloadUrl" value="${url.server}${url.context}${url.files}${file.path}" context="/"/>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                            <json:property name="downloadUrl" value="${downloadUrl}"/>
                                        </json:object>
                                    </c:if>
                                </c:forEach>
                            </json:array>
                        </json:object>
                    </c:if>
            </c:forEach>
        </json:array>
    </json:object>
</json:array>

--%>