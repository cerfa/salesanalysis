//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package univ.fenncim.ddd.ha.finance.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class CustomInterceptor implements HandlerInterceptor {
    public CustomInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return true;
    }
}
