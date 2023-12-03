package leafspider.rest;

import leafspider.util.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public abstract class Rest {

    public abstract void parsePathInfo( String pathInfo );

    public void init( HttpServletRequest request, HttpServletResponse response ) {

        setContentLength(request.getContentLength());
        setContentType(request.getContentType());
        setRequest(request);
        setResponse(response);
        parsePathInfo(request.getPathInfo());
    }

    public UUID uuid = UUID.randomUUID();

    private String project = null;
    public String getProject() {
        return project;
    }
    public void setProject(String collection) {
        this.project = collection;
    }

    private String extension = null;
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }

    private HttpServletRequest request = null;
    public synchronized HttpServletRequest getRequest() { return request; }
    public synchronized void setRequest(HttpServletRequest request) { this.request = request; }

    private HttpServletResponse response = null;
    public HttpServletResponse getResponse() {
        return response;
    }
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    private int contentLength = -1;
    public int getContentLength() {
        return contentLength;
    }
    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    private String contentType = null;
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private String resourceId = null;
    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getHostUrl() {
        return "http://" + request.getServerName() + ":" + request.getServerPort();
    }

    public void report( String resource ) {
        String pathInfo = request.getPathInfo();
        if ( pathInfo == null ) { pathInfo = ""; }
        Log.infoln( uuid + " " + request.getMethod() + " " + resource + pathInfo );
    }


}
