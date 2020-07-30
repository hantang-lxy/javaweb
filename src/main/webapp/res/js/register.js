$(function () {
    //获取后端服务器地址
    let $url = '';
    //从服务器加载数据,请求方式为GET。
    $.get('res/serverconfig.json', function (responseText, textStatus, XMLHttpRequest) {
        window.console.log("何时现身的1");
        switch (textStatus) {
            case 'success':
                $url = responseText.protocol + responseText.domain + responseText.port + responseText.context;
                window.console.log("何时现身的2");
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
    let $captcha = $('#LAY-user-get-vercode');

    function getCaptcha() {
        // captcha_id=0,登录页请求图片验证码
        $captcha.attr("src", "/javaweb01/person.action?methodName=captcha&captcha_id=0&d=" + new Date().getTime())
    }

    getCaptcha();
    $captcha.on('click', function () {
        $captcha.attr("src", $url + "/person.action?methodName=captcha&captcha_id=0&d=" + new Date().getTime());
    })
    //使用layui组件
    layui.use(
        ['form', 'jquery', 'layer'],
        function () {
            let form = layui.form;
            let $ = layui.jquery;
            let layer = layui.layer;
            form.verify({
                //账号验证规则
                account: function (value) {
                    if (value == null || $.trim(value).length == 0) {
                        return '用户名不能为空';
                    }
                },
                //手机号验证
                mobile:
                    [/^1[3456789]\d{9}$/, '请输入正确的手机号'],
                //密码验证规则则
                password: [
                    /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
                ],
                //确认密码验证规则
                repass: function (value) {
                    if (!(/^[\S]{6,12}$/.test(value))) {
                        return '密码必须6到12位，且不能出现空格';
                    } else if (value.length != $('#password').val().length) {
                        return '两次密码长度不一致，请重新输入确认密码';
                    } else if (value != $('#password').val()) {
                        return '两次密码内容不一致，请重新输入确认密码';
                    }
                }
            });

            //验证码初始值
            let $code = '';
            //获取发送验证码按钮对象
            let smscodebtn = $('#getsmscode');
            //验证是否存在相同的手机号或者用户名标志 true：存在 false：不存在
            let isExist = false;
            //添加单击事件
            smscodebtn.on('click', function () {
                //获取用户输入的手机号
                let mobile = $('#phone').val();
                //验证手机格式是否正确
                //手机号验证
                if (!(/^1[3456789]\d{9}$/.test(mobile))) {
                    layer.msg('请输入正确的手机号', {
                        icon: 2,
                        shade: 0.01
                    })
                    return;
                }
                //初始化标记
                isExist = false;
                //验证手机号是否已注册
                $.ajax({
                    async: false,//同步
                    type: "post",
                    url: $url + "/person.action?methodName=verify",
                    data: {"mobile": mobile},
                    dataType: "text",
                    success: function (response) {
                        if ($.trim(response) == "0") {
                            layer.msg('手机号已注册！请输入新的手机号！', {
                                icon: 2,
                                time: 2000,
                            })
                            $('#phone').focus().select();
                            isExist = true;
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
                if (!isExist) {
                    // 点击'发送验证码按钮'后,ajax异步获取验证码
                    //这种获取验证码方式不安全，不返回验证码，在服务器端验证安全些
                    $.ajax({
                        type: "post",
                        url: $url + "/smsCode.action?methodName=getSmsCode",
                        data: {"mobile": mobile},
                        dataType: "text",
                        success: function (response) {
                            // 验证码赋值
                            $code = response;
                            layer.msg('验证码已发送，一分钟内有效！', {
                                icon: 1,
                                time: 2000,
                            })
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
            //初始化标记
            isExist = false;
            let $account = $("#account");
            $account.blur(function () {
                let username = $account.val();
                username = username.trim();
                //验证用户名是否已注册
                $.ajax({
                    async: false,//同步
                    type: "post",
                    url: $url + "/person.action?methodName=verify",
                    data: {"username": username},
                    dataType: "text",
                    success: function (response) {
                        window.console.log("从后端获取的值" + response);
                        if ($.trim(response) == "0") {
                            layer.msg('该用户名已注册！请输入新的用户名！', {
                                icon: 2,
                                time: 2000,
                            })
                            // $account.val('');
                            $account.focus().select();
                            isExist = true;
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


            })

            form.on('submit(submit)', function (param) {
                if (isExist) {
                    //用户名重复，不进行提交，标记初始化
                    isExist = false;
                    return;
                }
                // window.console.log(param);
                let field = param.field;
                let vercode = field.vercode;
                if ($code != vercode) {
                    //获取验证码输入框元素对象
                    let $vercode = $('#vercode');
                    $vercode.val('');
                    $vercode.focus();
                    return layer.msg('验证码错误，请重新输入验证码', {
                        icon: 2,
                        shade: 0.01
                    });
                }

                $.ajax({
                    type: 'post',
                    url: $url + '/person.action?methodName=register',
                    data: field,
                    dataType: 'text',
                    success: function (response) {
                        layer.msg('恭喜，注册成功！请登录！', {
                            icon: 1,
                            time: 2000,
                        });
                        //注册成功，清空输入框里面的内容
                        $('#clean').find("input").val('');
                    },
                    error: function () {
                        layer.msg('注册失败！', {
                            icon: 2,
                            time: 1000,
                        })
                    }
                });
            });

            form.on('submit(LAY-user-login-submit)', function (data) {


                let field = data.field;
                $.ajax({
                    async: false,//同步,先获得服务器端传来的数据
                    type: 'post',
                    url: $url + '/person.action?methodName=login',
                    data: field,
                    dataType: 'json',
                    success: function (response) {
                        window.console.log(response);
                        let code = response.code;
                        let result = response.captchaResult;

                        if (result == 'wrong') {
                            $('#LAY-user-login-vercode').val('');
                            return layer.msg('验证码错误，请输入正确验证码', {
                                icon: 2,
                                shade: 0.01,
                            });
                        }
                        if (code != 0) {
                            $('#LAY-user-login-username').focus().select();
                            return layer.msg('账号密码错误，请重新输入', {
                                icon: 2,
                                time: 1000
                            });

                        }
                        layer.msg('登录成功！', {
                            icon: 1,
                            time: 1000
                        });
                        let person = response.person;
                        localStorage.setItem("person", JSON.stringify(person));
                        let nickname = person.uNickname;
                        localStorage.setItem("nickname", nickname);
                        window.console.log(person);
                        let uPower = person.uPower;
                        //延时登录，等上面提示完跳转
                        setTimeout(function () {
                            if (uPower == 1) {
                                //管理员登录
                                location.replace($url + '/manager/backstage_index.jsp');
                                return;
                            }
                            //消费者登录
                            location.replace($url + '/person.action?methodName=toPreURL');
                        }, 1500);

                    },
                    error: function () {
                        layer.msg('登录失败！', {
                            icon: 2,
                            time: 1000,
                        })
                    }
                });
            });
        }
    );


});