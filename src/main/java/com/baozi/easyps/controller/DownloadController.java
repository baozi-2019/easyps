package com.baozi.easyps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class DownloadController {

    @RequestMapping("/down")
    public String down(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String fileAbsolutePath = (String) session.getAttribute("fileAbsolutePath");
        String fileName = (String) session.getAttribute("fileName");
        fileAbsolutePath = fileAbsolutePath + "/" + fileName;
        //下载
        //设置消息头
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //获取要下载的文件
        File file = new File(fileAbsolutePath);
        InputStream inputSteam = null;
        try {
            inputSteam = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream binSteam = new BufferedInputStream(inputSteam);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[100];
            while ( binSteam.read(bytes) != -1) {
                outputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (binSteam != null)
                    binSteam.close();
                if (inputSteam != null) {
                    inputSteam.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "uploadFile";
    }
}
