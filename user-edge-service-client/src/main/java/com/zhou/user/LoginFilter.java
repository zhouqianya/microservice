package com.zhou.user;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zhou.thrift.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 18:21
 */
@Slf4j
public abstract class LoginFilter implements Filter {


    //guava 缓存
    private static Cache<String, UserDto> cache =
            CacheBuilder.newBuilder().maximumSize(10000)
                    .expireAfterWrite(3, TimeUnit.SECONDS).build();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getParameter("token");

        log.info("token = {}",token);

        if (StringUtils.isBlank(token)) {
            Cookie[] cookie = request.getCookies();
            if (cookie != null) {
                for (Cookie c : cookie) {
                    if (c.getName().equals("token")) {
                        token = c.getValue();
                    }
                }
            }
        }
        UserDto userDto = null;
        if (!StringUtils.isBlank(token)) {
            userDto = cache.getIfPresent(token);
            if (userDto == null) {
                userDto = requestUserInfo(token);
                if (userDto!=null){
                    cache.put(token, userDto);
                }
            }

        }
        if (userDto == null) {
            response.sendRedirect("http://127.0.0.1:8222/user/login");
            return;
        }
        login(request, response, userDto);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDto userDto);

    @Override
    public void destroy() {

    }

    private UserDto requestUserInfo(String token) {

        String url = "http://127.0.0.1:8222/user/authentication";

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", token);
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);

        String result;
        UserDto userDTO=new UserDto();
        try {
            result = restTemplate.postForObject(url, httpEntity, String.class);
            log.info("result={}",result);
            userDTO = new ObjectMapper().readValue(result, UserDto.class);

        } catch (RestClientResponseException e) {
            //使用捕获异常来处理返回的非200状态的不同响应
            String message = e.getMessage();
            //获取接口返回状态码
            int statis = e.getRawStatusCode();
            log.info("[响应码]{}", statis);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return userDTO;
    }
/*
    private UserDto requestUserInfo(String token) {
        String url = "http://user-edge-service:8082/user/authentication";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("token", token);
        InputStream inputStream = null;
        try {
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK) {
                throw new RuntimeException("request user info failed! StatusLine:"+response.getStatusLine());
            }
            inputStream = response.getEntity().getContent();
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while((len = inputStream.read(temp))>0) {
                sb.append(new String(temp,0,len));
            }

            //字符串转类
            UserDto userDTO = new ObjectMapper().readValue(sb.toString(), UserDto.class);
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream!=null) {
                try{
                    inputStream.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }*/

}
