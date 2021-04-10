package com.gy.rural_convenience_platform.config;

import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@Configuration
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092400585570";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCG5AldflEZgHLW+q1i4BX8KnwXhTHkC79pzpuIjsmbpsHyieHlpDp7AbigSB+2ggDQTAG2Fi5QQoXPKRAymw41TrCQfko3ZJ98hAWlNyVCpkbkTNoeW7B5VArRjor7USkgS1SRu2Hx8F3ERZHhpYaS2Fr/Y4RA7CHCu+tlgQ1wx6p4Fwua7+LLpIRc5DGJJwgGcWRtUt7QGmrJdUDueM5NfqRDYAx9I4BwdVYFtUGUyDKtwfpPkFlPdC/zgk9/0hEQSX6TCSeiMPS2yHh6ju52joSw6NrfhxXLwC82y6mo2U7PibDcwaGRGev2p4/hU9nJvQfaT+pW5bh5h2RqlY/RAgMBAAECggEAQEdeZx0QjHR3RRaDlUBveZDNPXANTDyE5Gi+GR7h17hpZ6ho60ONUpyJXGfCJPhiZuAxWJHNVBW/Ke7JdzQIW0K6RMDJoQfkMDPBdO+IY/BUi+pLbngG6mowFZg1hnkwfeEn1aJTlDUUmbkFQV/ddxB+nIDRvxZHKELgc6hBvWHXMNGSbmbHkelFjY95+42zNIo55y658pp/Sqix0ESlO3gxTEqZecKlsrU92PWbhyoQGtr5aJIZSXnlOgcpOdj4tPw0SAxu7sWJf5hzlJh0a2jqJbXV52QG/L2TyCmwgW944sUgCz11uUA+GstJoSO796XGUqStB4vhk0vF2VFNIQKBgQC6QBdCfzLBDlpyk0DzPEUILzXjvlomhP6LiHcxrApis9FttvQs6jwgxfXTJUWJlSn2/hSwc2/f7ctI9HrWAdTKyEwGgpenA61B1WzDmgxetciFfWC7du61U1F5i3eFlEdWhvhqn2A2LHKkUgea9QB/Q9cDNWF1xZUGnMtLWN2qBQKBgQC5aBP0+vCcWSDkEq7e7NSAIC0ay+tIljmMBqUAN8Vp4MBfOCNcxo/U4CIXQvjF0A+fmmeHHQlkUWtmnI+J4NzoQlfK0lGbahCKp8PJGWYLDp6z8gku7JJKmAlSYubyuUT6iz5o8aRd4NeM7yBa3FA6xaBCg36yX4BT9ho1ljFcXQKBgQCOhZjG89r8zGT+JByhhM82choEypblvwOh9q1vPz07RmrOUCANdrjIQ3PXPUA3BgP00301gUBs1OSoURzmQsR44Ej8+lXeIfwk0pLGI02v898b91NcYKhN7RH9tnGhsFPerK3PuWHAUsbvYy4ar7zPpjQUanbHzog5ZSZT5A7LmQKBgBi7P15AUvQoF3EU5OqroLTuldWrOdlW4JpDEKN0zQqQhlitPlpTyj/VHfcIYfLRDCR4DjNBtk0BZ1gqriLwMcdxghB1+ZjHjylPs5gRoUD8VEMgmAUAXYtBuIya0L+z4vWguVR6VziGk3ojM6erF7CfR/9XVwEoKBALFffr7u2pAoGAXcgmqm6kRjg8bUfQ7idccbmUnABsM1gEND/xALRb4VmcaqIlm1CnZuwH+iVmJqBZjkuGquCOUngznTcd7esg/ed7RywKWq7wydPiyisXIwIui1RUj75HtcUXYYjDnlIsFbGWMns8paaFhcKST8xx+elDT+wmmEI647rRn8BB8G0=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAocWaymthh8vovEBgh5/EhdWcjuMUiIgBTHLCdnwUW9E+gZVqVSpwmKhaN6d7ZHJ+XW1vbaUjOhJ6ClZRHFj4yMweVl2shntYVVj+hTbAPEaLTHk2DSkvFuU0+HgodNIBrGGol/mXmHyTgjkYYPBMHOvNvp7jTPONAE6Kr+EODO0anTBxiDSzDiAxoveJp4436l/KSns2wUnfRJfufOW99Yl4sJ9IXhxAg4Cpr5i96STvUB78mQhnWzRoFZTwSOadEhJbf0asgAXXtnt+T9ymFnNuqfcC3eopVcD03HDoe1xhKlLQp7AEl3Lg1NmhBHk+lTasq/x+k96Z+jXoMNVrbQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://i28n197382.51vip.biz/notifyPay";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://192.168.31.24:8020/rural_convenience_platform/my-account.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "E:\\Users\\dell\\workspace2020\\青软实训\\rural_convenience_platform\\log";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}