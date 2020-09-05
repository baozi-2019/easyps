package com.baozi.easyps.component;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocalResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String lang = httpServletRequest.getParameter("lang");
        Locale locale = null;
        if (!StringUtils.isEmpty(lang)) {
            String[] splitLang = lang.split("_");
            locale = new Locale(splitLang[0], splitLang[1]);
            httpServletRequest.getSession().setAttribute("lang", lang);
        } else {
            lang = (String)httpServletRequest.getSession().getAttribute("lang");
            if (lang == null)
                locale = httpServletRequest.getLocale();
            else {
                String[] splitLang = lang.split("_");
                locale = new Locale(splitLang[0], splitLang[1]);
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
