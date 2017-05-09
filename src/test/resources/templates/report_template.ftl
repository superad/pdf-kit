<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title></title>
    <style type="text/css">
        body {
            font-family: pingfang sc light;
        }
    </style>
</head>
<body>
<!--第一页开始-->
<div class="page" >
    <div>report_template</div>
    <div>姓名:${name}</div>
    <div>年龄:${age}</div>
    <div>成绩:</div>
    <#list scores as item>
        <div>${item}</div>
    </#list>

</div>
<!--第一页结束-->
<!---分页标记-->
<span style="page-break-after:always;"></span>
<!--第二页开始-->
<div class="page">
    <div>第二页开始了</div>
    <img src="${imageUrl}" alt="百度图标" width="270" height="129"/>
</div>


<!--第二页结束-->
</body>
</html>