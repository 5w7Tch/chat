package servlets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")  // Intercepts all requests
public class filter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (requestURI.endsWith("favicon.ico")) {
            ((HttpServletResponse) response).sendRedirect("chat.png");
            return;
        }
        chain.doFilter(request, response);  // Continue with the next filter or servlet
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
