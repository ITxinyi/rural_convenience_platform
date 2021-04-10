package com.gy.rural_convenience_platform.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gy.rural_convenience_platform.config.AlipayConfig;
import com.gy.rural_convenience_platform.entity.OrderInfo;
import com.gy.rural_convenience_platform.entity.UserGoodsOrder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class AlipayUtils {

    public void pay(OrderInfo orderInfo, HttpServletResponse response) throws AlipayApiException, IOException {

        if ("1".equals(orderInfo.getFlag())) {
            AlipayConfig.return_url = "http://192.168.31.24:8020/rural_convenience_platform/worker-account.html";
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(orderInfo.getOutTradeNo().getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填
//        String total_price = orderInfo.getPayPrice() + "";
        String total_amount = new String(orderInfo.getTotalAmount().getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        String subject = new String(orderInfo.getSubject().getBytes("UTF-8"),"UTF-8");
        //商品描述，可空
//        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //输出
//        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

}
