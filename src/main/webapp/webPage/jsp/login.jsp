<%--
  Created by IntelliJ IDEA.
  User: zhiyu
  Date: 2018/6/7
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <span>给自己取一个拉风的昵称吧!</span><br/>
    <input type="text" id="nickName"/><br/>
    <input type="button" id="submit" onclick="submit()" value="登录"/><br/>
    <span id="msg"></span>
</body>
<script src="js/jquery-1.10.2.min.js"></script>
<script>
    function submit() {
        var nickName = $('#nickName').val().trim();
        if (nickName == ''){
            $('#msg').text('请输入一个昵称');
            return;
        }
        $.post('ajax/signIn',{
            nickName: nickName
        }, function (data) {
            if (data.status) {
                window.location.href = '../index';
            } else {
                $('#msg').text(data.msg);
            }
        }, 'json');
    }
</script>
</html>
