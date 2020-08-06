package com.tanghs.tmall.test;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


/**
 * 简单生成验证码
 */
public class VerificationCode_SimpleDemo extends HttpServlet implements Servlet {


    @Override
    @GetMapping("/test12")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //让浏览器3秒刷新一次
        response.setHeader("refresh","3");

        //在内存中创建一个图片
        BufferedImage image = new BufferedImage(80,20,BufferedImage.TYPE_INT_RGB);
        //得到图片
        Graphics2D graphics2D = (Graphics2D)image.getGraphics();
        //设置图片的背景颜色
        graphics2D.setColor(Color.white);
        //填充背景颜色
        graphics2D.fillRect(0,0,80,20);
        //给图片写数据
        graphics2D.setFont(new Font(null,Font.BOLD,20));
        graphics2D.drawString(makeNum(),0,20);

        //浏览器打开类型
        response.setContentType("images/jpeg");
        //网站存在缓存,不让浏览器缓存
        response.setDateHeader("expire",-1);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");

        //把图片写给浏览器
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    //生成随机数
    private String makeNum() {
        Random random = new Random();
        String num = random.nextInt(9999999) + "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 7 - num.length(); i++) {
            sb.append("0");
        }
        num = sb.toString() + num;
        return num;
    }

}
