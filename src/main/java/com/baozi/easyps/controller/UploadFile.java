package com.baozi.easyps.controller;

import com.baozi.easyps.utils.PhotoHandingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Controller
public class UploadFile {
    @Autowired
    ApplicationContext context;

    @Autowired
    PhotoHandingUtil photoHandingUtil;

//    @RequestMapping("/")
//    public String index(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        if (session.getAttribute("fileAbsolutePath") != null) {
//            session.removeAttribute("fileAbsolutePath");
//            session.removeAttribute("fileName");
//        }
//        return "uploadFile";
//    }

    @RequestMapping(value = "/uploadFile", produces = "text/plain;charset=UTF-8")
    public String uploadFile(@RequestParam("photo") MultipartFile photo, @RequestParam("content") String content,
                             HttpServletResponse response,
                             HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        if (photo.isEmpty()) {
            log.error("-----photo empty-----");
            return "uploadFail";
        }
        String name = photo.getOriginalFilename();
        String suffix = name.substring(name.indexOf("."));
        if (!(".jpg".equals(suffix) || ".jepg".equals(suffix) || ".gif".equals(suffix) || ".png".equals(suffix))) {
            log.error("------photo type not true" + suffix + "-------");
            return "uploadFail";
        }
        String fileName = UUID.randomUUID().toString() + suffix;
        String property = System.getProperty("user.dir");
        String path = property + "/images/";
        HttpSession session = request.getSession();
        session.setAttribute("fileAbsolutePath", path);
        session.setAttribute("fileName", fileName);
        File file = new File(path + fileName);
        if (!file.exists())
            file.mkdirs();
        try {
            photo.transferTo(file);
        } catch ( IOException e) {
            e.printStackTrace();
        }
        //处理图片
        try {
            photoHandingUtil.photoHanding(file, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "downloadFile";
    }
}
