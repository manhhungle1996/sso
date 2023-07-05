package vn.com.atomi.openbanking.authservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class AfterKeycloakFilter implements Filter {
    static final private Logger logger = LoggerFactory.getLogger(AfterKeycloakFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(String.valueOf(authentication));
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String id = Optional.ofNullable(request.getHeader("x-fapi-xxx-id"))
                .orElse(UUID.randomUUID().toString());
        HttpServletRequestWrapper wrap = new HttpServletRequestWrapper(request){
            @Override
            public String getHeader(String name) {
                if (name.equals("x-fapi-xxx-id")){
                    return id;
                }
                return super.getHeader(name);
            }
            @Override
            public Enumeration<String> getHeaders(String name) {
                if (name.equals("x-fapi-xxx-id")){
                    List<String> temp = new ArrayList<>();
                    temp.add(id);
                    return Collections.enumeration(temp);
                }
                return super.getHeaders(name);
            }
            @Override
            public Enumeration<String> getHeaderNames() {
                // TODO Auto-generated method stub
                List<String> temp = Collections.list(super.getHeaderNames());
                temp.add("x-fapi-xxx-id");
                return Collections.enumeration(temp);
            }
        };
        filterChain.doFilter(wrap, response);
    }
}