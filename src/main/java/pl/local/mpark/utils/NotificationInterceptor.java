package pl.local.mpark.utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotificationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        String successMsg = request.getParameter("successMsg");
        String errorMsg = request.getParameter("errorMsg");
        String infoMsg = request.getParameter("infoMsg");
        if(successMsg != null) modelAndView.getModelMap().addAttribute("success", HtmlUtils.htmlEscape(successMsg));
        if(errorMsg != null) modelAndView.getModelMap().addAttribute("error", HtmlUtils.htmlEscape(errorMsg));
        if(infoMsg != null) modelAndView.getModelMap().addAttribute("info", HtmlUtils.htmlEscape(infoMsg));
    }
}
