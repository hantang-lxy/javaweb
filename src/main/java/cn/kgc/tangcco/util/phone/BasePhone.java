package cn.kgc.tangcco.util.phone;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import cn.hutool.setting.dialect.Props;

public abstract class BasePhone {

    private static Props props = new Props("phone.properties");
    private static String host = props.getProperty("host");
    private static String path = props.getProperty("path");
    private static String method = props.getProperty("method");
    private static String appcode = props.getProperty("appcode");
    private static String tpl_id = props.getProperty("tpl_id");

    /**
     * 向手机发送验证码的方法
     */
    public static String phoneCode(String phone) {
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        String codeNumber = RandomStringUtils.randomNumeric(6);
        String code = "code:" + codeNumber;
        querys.put("param", code);
        querys.put("tpl_id", tpl_id);
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            /**
             * 重要提示如下: HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse resp = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(resp.toString());
            // 获取response的body
            // System.out.println(EntityUtils.toString(response.getEntity()));
            // 状态行
            StatusLine statusLine = resp.getStatusLine();
            // 获取响应的状态码
            int statusCode = statusLine.getStatusCode();
            String reasonPhrase = statusLine.getReasonPhrase();
            if ("OK".equalsIgnoreCase(reasonPhrase) && 200 == statusCode) {
                return codeNumber;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
