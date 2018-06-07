<%--
  Created by IntelliJ IDEA.
  User: zhiyu
  Date: 2018/6/7
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    <title>聊天</title>
    <style>
        .box {
            width: 800px;
            border: 1px solid #e1e1e1;
            margin: 0 auto;
            height: 600px;
            display: flex;
            color: #333;
            box-sizing: border-box;
        }

        .left {
            flex: 1;
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        .left .text {
            height: 60%;
            padding: 10px;
            position: relative;
        }

        .input {
            flex: 1;
            border-top: 1px solid #e1e1e1;
            display: flex;
            flex-direction: column;
        }

        textarea {
            width: 100%;
            height: 60%;
            border: none;
            outline: none;
            padding: 0;
            padding: 10px;
            box-sizing: border-box;
            resize: none;
        }

        .right {
            width: 300px;
            height: 100%;
            border-left: 1px solid #e1e1e1;
        }

        ul {
            padding: 0 40px;
        }

        ul li {
            font-size: 14px;
            line-height: 28px;
            border-bottom: 1px solid #e1e1e1;
            padding: 10px 0;
        }

        ul li.active {
            color: #76b2fb;
        }

        .send-btn {
            flex: 1;
            padding-top: 15px;
        }

        .send-btn .btn {
            width: 354px;
            height: 50px;;
            background-color: #2a95f6;
            margin: 0 auto;
            line-height: 50px;
            color: #fff;
            text-align: center;
            border-radius: 100px;
            font-size: 18px;
            display: block;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="box">
    <div class="left">
        <div class="text">
            <ul id="messageBox" style="position：absolute; bottom:0;height:100%;overflow:auto;">

            </ul>
        </div>
        <div class="input">
            <textarea name="" id="content" cols="30" rows="10"></textarea>
            <div class="send-btn">
                <span class="btn" onclick="sendMsg()" id="sendBtn">发送</span>
            </div>
        </div>
    </div>
    <div class="right">
        <ul id="onlineUser" style="overflow: auto;">
            <%--<li>第一个人</li>
            <li>第一个人</li>
            <li class="active">第一个人</li>--%>
        </ul>
    </div>
</div>
</body>
<script>
    var socket;
    $(function () {
        socket = new WebSocket("ws://" + window.location.host + "/chat");
        socket.onopen = function () {
            alert('欢迎登录！');
        };
        socket.onmessage = function (event) {
            var data = jQuery.parseJSON(event.data);
            var str = "<li>" + data.sender + "（" + data.date + "）：<br/>"
                + data.content + "</li>";
            $('#messageBox').append(str);
            var msgBox = document.getElementById('messageBox');
            msgBox.scrollTop = msgBox.scrollHeight;
            if (data.type.indexOf('broadcast_') > -1) {
                $.post('ajax/queryOnline', function (data) {
                    $('#onlineUser').empty();
                    for (var i = 0; i < data.length; i++) {
                        $('#onlineUser').append('<li>' + data[i] + '</li>');
                    }
                }, 'json');
            }
        };
        socket.onbeforeunload = function () {
            if (socket != null) {
                socket.close();
            }
        };
        $(document).keydown(function (event) {
            if (event.keyCode === 13) {
                sendMsg();
            }
        });
        var ping = setInterval(function () {
            if (socket.readyState == socket.OPEN) {
                socket.send(JSON.stringify({
                    type: 'ping'
                }));
            } else {
                clearInterval(ping);
                alert('连接已断开，请刷新后重试');
            }
        }, 30000);
    });


    function sendMsg() {
        var content = $('#content').val().trim();
        if (content == '' || content == null) {
            alert('消息不能为空！');
            return;
        }
        var message = JSON.stringify({
            content: content,
            type: 'message',
            receiver: 'ALL'
        });
        if (socket.readyState == socket.OPEN) {
            socket.send(message);
            $('#content').val('').focus();
        } else {
            alert('连接已断开，请刷新后重试');
        }
    }
</script>
</html>
