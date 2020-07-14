package com.tanghs.tmall.hutool;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.tanghs.tmall.temporaryvariable.CodeVariable;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public class VerificationCode {
   /* public static void main(String[] args) {
        String dateStr = "2012-12-12 12:12:12";
        Date date = DateUtil.parse(dateStr);
        System.out.println(date);
        test1();
        *//*test2() ;*//*
        test3();
    }*/

    public static void test1(){      //创建 线段干扰的验证码
        int width = 200;
        int height = 100;
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height);
        System.out.println("线段干扰验证码是" + captcha.getCode());
        String path = "d:/captcha1.png";
        captcha.write(path);
    }

    public static Integer test2(HttpServletRequest request)throws IOException {    //创建圆圈干扰的验证码
        int width = 100;
        int height = 40;
        int codeCount = 5;
        int circleCount = 25;
        int ran = (int)(Math.random()*10+1);
        File imageFolder= new File(request.getServletContext().getRealPath("img/verificationcode"));
        File file = new File(imageFolder,ran+".png");
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, circleCount);
        System.out.println("圆圈干扰验证码是" + captcha.getCode());
        CodeVariable.VERIFICATION_CODE = captcha.getCode();          //存入临时变量
        /* String path = "d:/captcha2.png";*/
        captcha.write(file);
        return ran;
    }


    public static void test3(){        //创建 扭曲线干扰的验证码
        int width = 200;
        int height = 100;
        int codeCount = 5;
        int thickness = 2;
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(width, height,codeCount,thickness);
        System.out.println("扭曲线干扰验证码是" + captcha.getCode());
        String path = "d:/captcha3.png";
        captcha.write(path);
    }
}
