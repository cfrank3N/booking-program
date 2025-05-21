package backend1.bookingprogram.enums;

public enum RoutingInfo {

    REGISTER_GUEST("/guest/register", "register-guest"),
    HOMEPAGE("/", "index");

    private final String uri;
    private final String viewName;

    RoutingInfo(String uri, String viewName) {
        this.uri = uri;
        this.viewName = viewName;
    }

    public String getUri() {
        return uri;
    }

    public String getViewName() {
        return viewName;
    }
}
