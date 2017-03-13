<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网络安全威胁监控日志</title>
<link href="bootstrap.css" rel="stylesheet">
<style type="text/css">
#console {
			height: 400px;
			overflow: auto;
		}
</style>
<script src="js/socket.io/socket.io.js"></script>
        <script src="js/moment.min.js"></script>
        <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <script>
        var socket =  io.connect('http://localhost:9092');
        /**订阅攻击详情*/
		 function subscibe() {
			 socket.on('showEvent', function(data) {
					output( data);
				});
    };
    
    /**解析和输出其他客户端或服务端发送的消息*/
	function output(message) {
   var currentTime = "<span class='time'>" +  moment().format('HH:mm:ss.SSS') + "</span>";
   var element = $("<div>"+ message + "</div>");
		$('#console').prepend(element);
	}
    $(function(){
    	subscibe();
    });
        </script>
       
</head>
<body>
<div style="font-size: 30px;font-weight: 800;margin-top:20px;margin-left:30px;height:34px;">网络安全威胁监控日志显示</div>
<div id="console" class="well"></div>
</body>
</html>