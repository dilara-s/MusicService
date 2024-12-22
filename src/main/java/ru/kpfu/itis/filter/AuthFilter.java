package ru.kpfu.itis.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private List<String> allowedPaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String allowedPathsParam = filterConfig.getServletContext().getInitParameter("allowedPaths");
        if (allowedPathsParam != null) {
            allowedPaths = Arrays.asList(allowedPathsParam.split(","));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        boolean isPublicResource = allowedPaths.stream().anyMatch(uri::contains);
        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        if (!isAuthenticated && !isPublicResource) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/mainPage");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
