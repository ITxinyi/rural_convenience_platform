package com.gy.rural_convenience_platform.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
@Component
public class OrderNumberUtil {

    public String proOrderNumber(){
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        int rand = new Random().nextInt(1000000);
        String date = sd.format(new Date());
        String orderNumber = date + rand;
        return orderNumber;
    }

}
