$(function () {
    //获取后端服务器地址
    let $url = '';
    //从服务器加载数据,请求方式为GET。
    $.get('res/serverconfig.json', function (responseText, textStatus, XMLHttpRequest) {
        switch (textStatus) {
            case 'success':
                $url = responseText.protocol + responseText.domain + responseText.port + responseText.context;
                break;
            case 'error':
                layer.msg('走丢了', {
                    icon: 2,
                    shade: 0.01
                })
                break;
            default:
                break;
        }
    });
    //图片验证码所在标签元素
    let $captcha = $('#LAY-user-get-vercode');

    function getCaptcha() {
        //captcha_id=1,找回密码页请求图片验证码
        $captcha.attr("src", "/javaweb01/person.action?methodName=captcha&captcha_id=1&d=" + new Date().getTime())
    }

    getCaptcha();
    $captcha.on('click', function () {
        $captcha.attr("src", $url + "/person.action?methodName=captcha&captcha_id=1&d=" + new Date().getTime());
    })
    //使用layui组件
    layui.use(
        ['form', 'jquery', 'layer'],
        function () {
            let form = layui.form;
            let $ = layui.jquery;
            let layer = layui.layer;
            //验证密码
            form.verify({
                //密码验证规则
                pass: [
                    /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格，密码内也不能出现空格'
                ],
                //确认密码验证规则,value是密码框输入的值
                repass: function (value) {
                    if (!(/^[\S]{6,12}$/.test(value))) {
                        return '密码必须6到12位，且不能出现空格';
                    } else if (value.length != $('#LAY-user-login-password').val().length) {
                        return '两次密码长度不一致，请重新输入确认密码';
                    } else if (value != $('#LAY-user-login-password').val()) {
                        return '两次密码内容不一致，请重新输入确认密码';
                    }
                }
            });


            //验证码初始值
            let $code = '';
            //获取发送验证码按钮对象
            let smscodebtn = $('#LAY-user-getsmscode');
            //验证是否存在注册的手机号 true：存在 false：不存在
            let isExist = true;
            //添加单击事件
            let mobile;
            smscodebtn.on('click', function () {
                //获取用户输入的手机号
                mobile = $('#LAY-user-login-cellphone').val();
                //验证手机格式是否正确
                //手机号验证
                if (!(/^1[3456789]\d{9}$/.test(mobile))) {
                    layer.msg('请输入正确的手机号', {
                        icon: 2,
                        shade: 0.01
                    })
                    return false;
                }
                //初始化标记
                isExist = true;
                //验证手机号是否已注册
                $.ajax({
                    async: false,//同步，验证完再发短信
                    type: "post",
                    url: $url + "/person.action?methodName=verify",
                    data: {"mobile": mobile},
                    dataType: "text",
                    success: function (response) {
                        if ($.trim(response) == "50001") {
                            layer.msg('手机号未注册！请输入注册时的手机号！', {
                                icon: 2,
                                time: 2000,
                            })
                            $('#LAY-user-login-cellphone').focus().select();
                            isExist = false;
                            return;
                        }
                    },
                    error: function () {
                        layer.msg('哎呦,服务器走丢了', {
                            icon: 2,
                            time: 1000,
                        })
                    }
                });
                window.console.log('我是一个分割线++++++++++++++++++');
                //手机号可以使用时发送验证码
                if (isExist) {
                    // 点击'发送验证码按钮'后,ajax异步获取验证码
                    $.ajax({
                        type: "post",
                        url: $url + "/smsCode.action?methodName=getSmsCode",
                        data: {"mobile": mobile},
                        dataType: "text",
                        success: function (response) {
                            // 验证码赋值
                            $code = response;
                        },
                        error: function () {
                            layer.msg('哎呦,服务器走丢了', {
                                icon: 2,
                                time: 1000,
                            })
                        }
                    });
                    //倒计时总时间，一般都是60s
                    let countdown = 60;
                    //显示发送验证码的效果
                    let initCode = setInterval(function () {
                        if (countdown > -1) {
                            smscodebtn.addClass('disabled');
                            smscodebtn.attr('disabled', true);
                            smscodebtn.html("重新发送(" + countdown + ")");
                            countdown--;
                        } else {
                            smscodebtn.removeClass('disabled');
                            smscodebtn.attr('disabled', false);
                            smscodebtn.html("获取验证码");
                            countdown = 60;
                            // 清空验证码
                            $code = "";
                            clearInterval(initCode);
                        }
                    }, 1000);
                }
            });


            form.on('submit(LAY-user-forget-submit)', function (data) {

                let field = data.field;
                let vercode = field.vercode;
                if ($code != vercode) {
                    //获取验证码输入框元素对象
                    let $vercode = $('#LAY-user-login-smscode1');
                    $vercode.val('');
                    $vercode.focus();
                    return layer.msg('手机验证码错误，请重新输入验证码', {
                        icon: 2,
                        shade: 0.01
                    });
                }
                $.ajax({
                    async: false,//同步,先获得服务器端传来的数据
                    type: 'post',
                    url: $url + '/person.action?methodName=captchaVerify',
                    data: field,
                    dataType: 'text',
                    success: function (response) {
                        if (response == 'wrong') {
                            $('#LLAY-user-login-vercode').val('');
                            $('#LLAY-user-login-vercode').focus();
                            return layer.msg('图形验证码错误，请输入正确验证码', {
                                icon: 2,
                                shade: 0.01,
                            });
                        }
                        sessionStorage.setItem("mobile", mobile);
                        location.replace($url + '/resetpass.html')
                    },
                    error: function () {
                        layer.msg('走丢了，请检查网络！', {
                            icon: 2,
                            time: 1000,
                        })
                    }
                });
            });


            form.on('submit(LAY-user-forget-resetpass)', function (data) {

                let field = data.field;
                let password = field.password;
                let mobile = sessionStorage.getItem("mobile");
                window.console.debug(mobile);
                $.ajax({
                    type: 'post',
                    url: $url + '/person.action?methodName=resetPassword',
                    data: {"mobile": mobile, "password": password},
                    dataType: 'text',
                    success: function (response) {
                        window.console.log(response);
                        if (response == 'failed') {
                            window.console.log(response);
                            $('#LAY-user-login-password').select();
                            $('#LAY-user-login-password').focus();
                            return layer.msg('重置密码失败，请稍后再试！', {
                                icon: 2,
                                time: 1000
                            });
                        }
                        layer.msg('恭喜，重置密码成功！请重新登录！', {
                            icon: 1,
                            time: 1000
                        });
                        //重置密码成功后将记录的手机号清除
                        sessionStorage.removeItem("mobile")
                        setTimeout(function () {
                            location.replace($url + '/register.html')
                        }, 1000);

                    },
                    error: function () {
                        layer.msg('走丢了，请检查网络！', {
                            icon: 2,
                            time: 1000,
                        })
                    }
                });
            });
        }
    );


});