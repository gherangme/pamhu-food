package com.example.securityhibernate.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieHandle {

    // Create Cookie
    public void createCookie(String name, String username, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, username);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(4 * 60 * 60);
        response.addCookie(cookie);
    }

    // Xoá cookie
    public void deleteCookie(Cookie cookie, HttpServletResponse response) {
        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
