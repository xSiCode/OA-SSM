<%--
  Created by IntelliJ IDEA.
  User: xSi
  Date: 2021/3/31
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>index</title>
</head>
<script src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
    $(function (){
        $.ajax({
            url:"${pageContext.request.contextPath}/adc/loginJson",
            method:"post",
            data:"杨天发",
            contentType:"application/json",
            dataType:"json",
            success:function (result){
                alert( result.code+result.message+result.data.n);
            },
            error:function () {
                alert("error xsi");
            }

        })

        $("#btn_json_1").click(function ( ){

            $.ajax({
                url:"${pageContext.request.contextPath}/adc/loginJson",
                method:"post",
                data:"杨天发",
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message+result.data.n);
                },
                error:function () {
                    alert("error xsi");
                }

            })
        })
    ////////////////////////////////////
        $("#btn_json_2").click(function ( ){
            var user={
                "userId":1000,
                "userPassword":"123456",
                "userRole":"A",
            };
            var jsonValue=JSON.stringify(user);
            $.ajax({
                url:"${pageContext.request.contextPath}/adc/aObject",
                method:"post",
                data:jsonValue,
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message+
                            result.data.aUser.userName+
                            result.data.aUser.userBirth
                    );
                },
                error:function (result) {
                    alert("error xsi"+result.code+result.message);
                }

            })
        })
        ////////////////////////////////////////
        //
        $("#btn_json_3").click(function ( ){
            var user={
                "userId":1234,
                "userPassword":"123456789",
                "userRole":"t",
                "userName":"杨天发xsi",
                "userTel":"151181859392",
                "userEmail":"kjk32@qq.com",
                "userSex":"男",
                "userBirth":"1997-11-18",
                "userIdCard":"536797413689643",
            };
            var jsonValue=JSON.stringify(user);
            $.ajax({
                url:"${pageContext.request.contextPath}/adc/aMap",
                method:"post",
                data:jsonValue,
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message+
                        result.data.aMap.userName+
                        result.data.aMap.userBirth
                    );
                },
                error:function (result) {
                    alert("error xsi"+result.code+result.message);
                }

            })
        })
        ///////////////////////////////////////
        ////////////////////////////////////////
        //
        $("#btn_json_4").click(function ( ){
            var user1={
                "userId":1234,
                "userPassword":"123456789",
                "userRole":"t",
                "userName":"杨天发xsi",
                "userTel":"151181859392",
                "userEmail":"kjk32@qq.com",
                "userSex":"男",
                "userBirth":"1997-11-18",
                "userIdCard":"536797413689643",
            };
            var user2={
                "userId":5678,
                "userPassword":"123456789",
                "userRole":"t",
                "userName":"杨天发xsi",
                "userTel":"151181859392",
                "userEmail":"kjk32@qq.com",
                "userSex":"男",
                "userBirth":"1997-11-18",
                "userIdCard":"536797413689643",
            };
            var user3={
                "userId":9098,
                "userPassword":"123456789",
                "userRole":"t",
                "userName":"杨天发xsi",
                "userTel":"151181859392",
                "userEmail":"kjk32@qq.com",
                "userSex":"男",
                "userBirth":"1997-11-18",
                "userIdCard":"536797413689643",
            };
            var user=[user1,user2,user3];
            var jsonValue=JSON.stringify(user);
            $.ajax({
                url:"${pageContext.request.contextPath}/adc/aList",
                method:"post",
                data:jsonValue,
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message+
                        result.data.aList[0].userId+
                        result.data.aList[2].userId
                    );
                },
                error:function (result) {
                    alert("error xsi"+result.code+result.message);
                }

            })
        })
        ////////////////////////////////////////
        //
        $("#btn_json_login_5").click(function ( ){
            var user={
                "userId":2110,
                "userPassword":"897544",
                "userRole":"T",
            };
            var jsonValue=JSON.stringify(user);
            $.ajax({
                url:"${pageContext.request.contextPath}/user/login",
              //  url:"http://192.168.1.103:8081/ForairAcademy/user/login",
                method:"post",
                data:jsonValue,
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message+
                        result.data.user.userId+
                        result.data.user.userPassword+
                        result.data.user.userRole
                    );
                },
                error:function (result) {
                    alert("error xsi"+result.code+result.message);
                }

            })
        })
    })

</script>
<body>
index 2021-3-31
<%--        开头如果加了斜杆，路径就不对了
--%>
<br>
<a href="user/loginJsonVoid" >loginJsonVoid</a>
<br>
<input type="button" value="传单个参数" id="btn_json_1" >
<input type="button" value="传对象参数json" id="btn_json_2" >
<input type="button" value="传map对象参数json" id="btn_json_3" >
<input type="button" value="传list对象参数json" id="btn_json_4" >
<input type="button" value="登录" id="btn_json_login_5" >
</body>

</html>
