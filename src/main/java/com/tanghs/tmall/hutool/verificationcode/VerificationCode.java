package com.tanghs.tmall.hutool.verificationcode;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.tanghs.tmall.temporaryvariable.CodeVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

        //删除原来的验证码图片,避免图片名重复导致刷新失败
       if(CodeVariable.VERIFICATION_CODE_IMAGE_NAME != null){
           File imageFolderDelete = new File(request.getServletContext().getRealPath("img/verificationcode")); //获取图片路径、
           File file = new File(imageFolderDelete,CodeVariable.VERIFICATION_CODE_IMAGE_NAME+".png");
           BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
           in.close();        //关闭流，避免没关闭流导致删除失败
           file.delete();  //删除图片
       }

        int ran = (int)(Math.random()*1000000+1);             //取1~1000000内的随机数
        CodeVariable.VERIFICATION_CODE_IMAGE_NAME = ran;      //将图片名称存入临时变量
        File imageFolder = new File(request.getServletContext().getRealPath("img/verificationcode"));
        File file = new File(imageFolder,ran+".png");
        CaptchaUtil.createCircleCaptcha(width, height, codeCount, circleCount);
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, circleCount);
        System.out.println("当前验证码图片名:"+ran);
        System.out.println("圆圈干扰验证码是:" + captcha.getCode());
        CodeVariable.VERIFICATION_CODE = captcha.getCode();          //存入临时变量
        /* String path = "d:/captcha2.png";*/
        captcha.write(file);
        return ran;                                                 //返回图片名
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
