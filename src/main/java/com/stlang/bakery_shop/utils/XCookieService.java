package com.stlang.bakery_shop.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XCookieService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    /**
     * Tạo cookie
     * @param name : key của cookie
     * @param value : Giá trị của cookie
     * @param days : Thời gian tồn tại của cookie
     * @return Cookie vừa tạo.
     */
    public Cookie create(String name, String value, int days) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(days * 24 * 60 * 60);
        response.addCookie(cookie);
        return cookie;
    }

    /**
     * Lấy Cookie
     * @param name : key của cookie
     * @return cookie tìm thấy nếu không return null
     */
    public Cookie get(String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * Xóa cookie
     * @param name Key của cookie cần xóa
     */
    public void remove(String name){
        this.create(name,"",0);
    }


    /**
     * Lấy giá trị Cookie
     * @param name Key của cookie cần lấy
     * @return Cookie tìm thấy.
     */
    public Cookie getValue(String name){
        return this.get(name);
    }

}
