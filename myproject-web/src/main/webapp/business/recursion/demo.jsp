<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>JOSN演示</title>
<script src="../../system/script/jquery-1.11.1.min.js"></script>
<script>
    $(document).ready(function() {
        var jsonStr = "{'name': 'cxh', 'sex': 'man', 'age': 1}";
        var obj = eval('(' + jsonStr + ')');
        var traverseJson="{ 'name': 'cxh', 'sex': 'man', 'age': 1},解析结果：</br>";
        for(var a in obj) {
            traverseJson += "key: " + a +",value: " + obj[a]+" </br> ";
        }
        $("#traverseJson").html(traverseJson);
        var arrayStr = [{"name":"运维管理","tId":"roleMenu0_1",
        				"children":[{"name":"权限管理","tId":"roleMenu0_2","children":[{"name":"角色管理","tId":"roleMenu0_3"},{"name":"用户管理","tId":"roleMenu0_4"}]},
        				            {"name":"菜单分配","tId":"roleMenu0_5"}]},
        				{"name":"用户管理","tId":"rowMenu1-1"}];
        var str = "";
        f(arrayStr, str);
    });
    
    function f(array, str) {
        str += "--";
        for (var i = 0; i < array.length; i++) {
            var object = array[i];
            if (object.children) {
            	alert(str)
                $("#traverseArray").append(str + object.name +" </br> ");
                f(object.children, str);
            } else {
            	alert(str)
                $("#traverseArray").append(str + object.name +" </br> ");
            }
        }
    }
</script>
</head>
<body>
    <font color="red" id="traverseJson"></font>
    <hr/>
    <font color="red" id="traverseArray"></font>
</body>
</html>