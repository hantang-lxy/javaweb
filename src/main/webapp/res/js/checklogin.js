$(function () {
    function checklogin() {
        let nickname = localStorage.getItem("nickname");
        window.console.log(nickname);
        //没有存值的时候，取出来的是null
        if (nickname != null) {
            nav1 = "<a href=\"/javaweb01/userInfo.action?methodName=getUserInfo\">" + nickname + "</a>";
            $('#shownickname').html(nav1);
            nav2 = "<a href=\"/javaweb01/userInfo.action?methodName=getUserInfo\">在线</a>";
            $('#online').html(nav2);
            nav3 = "<a href=\"/javaweb01/person.action?methodName=logout\">退出</a>";
            $('#quitlogin').html(nav3);
            $('#quitlogin').on('click', function () {
                localStorage.removeItem("person");
                localStorage.removeItem("nickname");
            })
        } else {
            nav1 = "<a href=\"register.html\">登录</a>";
            $('#shownickname').html(nav1);
            nav2 = "<a href=\"register.html\"></a>";
            $('#online').html(nav2);
            nav3 = "<a href=\"register.html\">注册</a>";
            $('#quitlogin').html(nav3);
        }
    }

    checklogin();
})