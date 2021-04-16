package com.gy.rural_convenience_platform.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.gy.rural_convenience_platform.config.AlipayConfig;
import com.gy.rural_convenience_platform.entity.*;
import com.gy.rural_convenience_platform.service.CheckOutService;
import com.gy.rural_convenience_platform.service.UserGoodsOrderService;
import com.gy.rural_convenience_platform.service.UserServerService;
import com.gy.rural_convenience_platform.service.WorkerService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.RedisUtil;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CheckOutController {

    @Autowired
    private CheckOutService checkOutService;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private UserGoodsOrderService userGoodsOrderService;
    @Autowired
    private UserServerService userServerService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/checkOutGoods")
    public Map<String, Object> getCheckOutGoods(String ids, HttpServletRequest request) {
        User user = currentUser.currentUser(request);
        if (user != null) {
            List<Cart> list = checkOutService.getCheckOutGoods(ids);
            return ResponseCode.ok(list);
        }
        return ResponseCode.error(false);
    }

    @RequestMapping("/pay")
    public void doPay(String ids, Integer addrId, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = checkOutService.doPay(user, ids, addrId, response);
        }
    }

    @RequestMapping("/rePay")
    public void rePay(Integer orderId,HttpServletRequest request,HttpServletResponse response) throws IOException, AlipayApiException {
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = checkOutService.rePay(orderId, response);
        }
    }

    @RequestMapping(value = "/notifyPay",method = RequestMethod.POST)
    public void notifyPay(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        System.out.println(params);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

        /* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
        4、验证app_id是否为该商户本身。
        */
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                /*如果是商品订单*/
                UserGoodsOrder goodsOrder = userGoodsOrderService.getOrderByNum(out_trade_no);
                if (goodsOrder != null) {
                    if(goodsOrder.getPayState() == 0){
                        userGoodsOrderService.updatePayState(out_trade_no);
                    }
                }else{
                    /*如果是服务订单*/
                    UserServerOrder serverOrder = userServerService.getServerOrderDtl(out_trade_no);
                    if (serverOrder != null) {
                        if (serverOrder.getPayState() == 0) {
                            userServerService.updServerOrderState(out_trade_no);
                        }
                    }else{
                        Map<String,Object> map = new HashMap<>();
                        map.put("depositOrdNum", out_trade_no);
                        Worker _worker = workerService.queryWorker(map);
                        if (_worker != null) {
                            map.put("workerId", _worker.getId());
                            map.put("deposit", 500);
                            workerService.updWorker(map);

//                            Worker worker = workerService.queryWorker(map);
//
//                            String s = request.getSession().getId();
//                            redisUtil.set(request.getSession().getId(), worker);
                        }
                    }
                }
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            response.getWriter().write("success");

        } else {//验证失败
            response.getWriter().write("fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——
    }

}
