package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.util.phone.BasePhone;
import cn.kgc.tangcco.util.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/12  16:06
 */
@WebServlet(urlPatterns = "/smsCode.action")
public class SmsCodeAction extends BaseServlet {

    private static final long serialVersionUID = -6785515854641071081L;

    public void getSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mobile = request.getParameter("mobile");
        String phoneCode = BasePhone.phoneCode(mobile);
        printJson(response, phoneCode);
        System.out.println("手机验证码>>>" + phoneCode);
    }


}
