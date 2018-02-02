# SpringMVC_Intercepter_Demo
# SpringMVC中拦截器Interceptor和过滤器Filter的使用

拦截器和过滤器的区别：过滤器Filter依赖于Servlet容器，基于回调函数，过滤范围大。拦截器Interceptor依赖于砍价容器，基于反射机制，只过滤请求。

总结：拦截器可以处理Web应用中请求的一些通用性问题，如解决中文乱码问题和用户权限登录问题，可以减少重复代码，便于维护。

拦截器的实现：
1、编写拦截器类实现HandlerInterceptor接口或继承HandlerInterceptorAdapter类
2、将拦截器注册到SpringMVC框架中
3、配置拦截器的拦截规则

具体逻辑编写：重写preHandle、postHandle、afterCompletion这三个方法。

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


在springmvc.xml中添加如下语句：
<!--配置拦截器-->
<mvc:interceptors>
    <!--方式一：默认拦截所有请求-->
    <!--<bean class="com.liyingyu.Interceptor.MyInterceptor"/>-->
    <!--方式二：自定义拦截路径-->
    <mvc:interceptor>
        <!--1和2的顺序不能变-->
        <!--1--><mvc:mapping path="/doLogin"/>
        <!--2--><bean class="com.liyingyu.Interceptor.MyInterceptor"/>
    </mvc:interceptor>
    <mvc:interceptor>
        <!--1和2的顺序不能变-->
        <!--1--><mvc:mapping path="/doLogin"/>
        <!--2--><bean class="com.liyingyu.Interceptor.MyInterceptor2"/>
    </mvc:interceptor>
</mvc:interceptors>

多拦截器协同工作时跟他们在配置文件中的顺序有关

Filter配置：在web.xml文件中添加如下配置语句

<!--filter配置要放在Servlet之前-->
<!--配置过滤器：解决中文乱码问题-->
<filter>
  <filter-name>encoding</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <init-param>
    <!--这里的param-name固定是encoding不能变-->
    <param-name>encoding</param-name>
    <param-value>utf8</param-value>
  </init-param>
</filter>
<filter-mapping>
  <!--对所有请求进行过滤-->
  <filter-name>encoding</filter-name>
  <url-pattern>*</url-pattern>
</filter-mapping>

一、环境配置


二、具体代码实现

web.xml
<!DOCTYPE web-app PUBLIC
"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
"http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app >
  <display-name>Archetype Created Web Application</display-name>
  <!--filter配置要放在Servlet之前-->
  <!--配置过滤器：解决中文乱码问题-->
  <filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <!--这里的param-name固定是encoding不能变-->
      <param-name>encoding</param-name>
      <param-value>utf8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <!--对所有请求进行过滤-->
    <filter-name>encoding</filter-name>
    <url-pattern>*</url-pattern>
  </filter-mapping>

  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/config/spring/springmvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>

</web-app>

springmvc.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xmlns:mvc="http://www.springframework.org/schema/mvc"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans.xsd
      http://www.springframework.org/schema/context
      http://www.springframework.org/schema/context/spring-context.xsd
      http://www.springframework.org/schema/mvc
      http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <mvc:annotation-driven/>
    <context:annotation-config/>
    <context:component-scan base-package="com.liyingyu.controller"/>
    <mvc:resources mapping="/resources/**" location="/resources/"/>
    <!--配置拦截器-->
    <mvc:interceptors>
        <!--方式一：默认拦截所有请求-->
        <!--<bean class="com.liyingyu.Interceptor.MyInterceptor"/>-->
        <!--方式二：自定义拦截路径-->
        <mvc:interceptor>
            <!--1和2的顺序不能变-->
            <!--1--><mvc:mapping path="/doLogin"/>
            <!--2--><bean class="com.liyingyu.Interceptor.MyInterceptor"/>
        </mvc:interceptor>
        <mvc:interceptor>
            <!--1和2的顺序不能变-->
            <!--1--><mvc:mapping path="/doLogin"/>
            <!--2--><bean class="com.liyingyu.Interceptor.MyInterceptor2"/>
        </mvc:interceptor>
    </mvc:interceptors>
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsps/"/>
        <property name="suffix" value=".jsp"/>
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
    </bean>
</beans>

pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.liyingyu.SpringMVC_Intercepter</groupId>
  <artifactId>Intercepter-test</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>Intercepter-test Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>5.0.2.RELEASE</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>5.0.2.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.0.2.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-expression -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
      <version>5.0.2.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet/jstl -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>

    <dependency>
      <groupId>taglibs</groupId>
      <artifactId>standard</artifactId>
      <version>1.1.2</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.0</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
  <build>
    <finalName>Intercepter-test</finalName>
  </build>
</project>

index.jsp
<%--
  Created by IntelliJ IDEA.
  User: YingyuLi
  Date: 2018/1/31
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form id="login" action="/doLogin" method="post">
    <input type="text" name="name" /><br/>
    <input type="password" name="password" /><br/>
    <input type="submit"/>
</form>
</body>
</html>

MyInterceptor.java
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

MyInterceptor2.java
package com.liyingyu.Interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Created by YingyuLi on 2018/2/2.
*/
public class MyInterceptor2 extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor2.preHandler()");
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor2.postHandle()");
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("MyInterceptor2.afterCompletion()");
    }
}

LoginController.java
package com.liyingyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Created by YingyuLi on 2018/1/31.
*/
@Controller
public class LoginController {
    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam String name,@RequestParam String password){
        System.out.println("name="+name+", password="+password);
        System.out.println("###LoginController.doLogin()");
        return "success";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}

login.jsp
<%--
  Created by IntelliJ IDEA.
  User: YingyuLi
  Date: 2018/1/31
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form id="login" action="/doLogin" method="post">
    <input type="text" name="name" /><br/>
    <input type="password" name="password" /><br/>
    <input type="submit"/>
</form>
</body>
</html>

success.jsp
<%--
  Created by IntelliJ IDEA.
  User: YingyuLi
  Date: 2018/1/31
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
<h3>登录成功！</h3>
</body>
</html>





























































