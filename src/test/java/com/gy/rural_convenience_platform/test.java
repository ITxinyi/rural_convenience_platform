package com.gy.rural_convenience_platform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class test {

    public static void main(String[] args) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        int rand = new Random().nextInt(1000000);
        String date = sd.format(new Date());
        String orderNumber = date + rand;
        System.out.println(orderNumber);
    }

}
