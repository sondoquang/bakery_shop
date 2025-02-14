package com.stlang.bakery_shop.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XSessionService {

    @Autowired
    HttpSession session;

    /**
     * Thiết lập session
     * @param key : Key của session
     * @param value : Giá trị của session
     */
    public void setAttribute(String key, String value) {
        session.setAttribute(key, value);
    }

    /**
     * Lấy ra giá trị session
     * @param key: key session
     * @return Kiểu dữ liệu T
     * @param <T>
     */
    public <T> T getAttribute(String key) {
        return (T) session.getAttribute(key);
    }

    /**
     * Xóa session
     * @param key : Key của session
     */
    public void removeAttribute(String key) {
        session.removeAttribute(key);
    }

}
