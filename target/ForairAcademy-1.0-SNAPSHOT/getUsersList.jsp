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

        $("#getUsersList").click(function ( ){
            var user={
                "currentPage":1,
            };
            var jsonValue=JSON.stringify(user);
            $.ajax({
                url:"${pageContext.request.contextPath}/user/getUsersList",
                method:"post",
                data:jsonValue,
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message
                    );
                },
                error:function (result) {
                    alert("error xsi"+result.code+result.message);
                }

            })
        })
        //    ==================================================================

        $("#getUserNameLike").click(function ( ){
            var user={
                "searchUserIdOrNameLike":"1444",
            };
            var jsonValue=JSON.stringify(user);
            $.ajax({
                url:"${pageContext.request.contextPath}/user/getUserIdOrNameLike",
                method:"post",
                data:jsonValue,
                contentType:"application/json",
                dataType:"json",
                success:function (result){
                    alert( result.code+result.message
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
<br>
<button id="getUserNameLike">getUserNameLike</button>
<button id="getUsersList">getUsersList</button>
<form action="user/b" method="get">
    <input type="submit" value="提交" />
</form>

</body>

</html>
