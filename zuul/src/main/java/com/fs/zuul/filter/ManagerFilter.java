package com.fs.zuul.filter;

import com.fs.common.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //直接放行OPTIONS方法
        if ("OPTIONS".equals(request.getMethod())) {
            return null;
        }
        //直接放行登录方法
        if (request.getRequestURL().indexOf("login") > 0) {
            return null;
        }
        String authorization = request.getHeader("Authorization");
        if (!TextUtils.isEmpty(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = claims.get("roles", String.class);
                    if (!TextUtils.isEmpty(roles)) {
                        if ("admin".equals(roles)) {
                            currentContext.addZuulRequestHeader("Authorization1", authorization);
                            return null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);
                }
            }
        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        return null;
    }
}
