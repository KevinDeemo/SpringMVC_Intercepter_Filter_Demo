package com.liyingyu.Interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YingyuLi on 2018/2/1.
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    /**
     * @param request 请求参数
     * @param response 返回参数
     * @param handler 请求对应的Controller类中的对应方法
     * @return 是否通过请求，true则表示通过，false则表示拒绝请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor.preHandler()");
        //在不用filter的情况下，下面这条语句也可以解决中文乱码问题
        //request.setCharacterEncoding("utf-8");
        return true;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView Controller返回的视图对象
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor.postHandle()");
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("MyInterceptor.afterCompletion()");
    }
}
