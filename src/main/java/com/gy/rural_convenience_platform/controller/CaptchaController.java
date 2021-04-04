package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.utils.RedisUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@CrossOrigin
public class CaptchaController {

    @Autowired
    private RedisUtil re;

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 40, 5);
        String verCode = specCaptcha.text().toLowerCase();
        System.out.println(verCode);
        re.set(verCode, verCode,60);
        CaptchaUtil.out(specCaptcha,request, response);
    }

}
