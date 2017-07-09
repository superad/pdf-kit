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
        .center{
            text-align: center;
            width: 100%;
        }
    </style>
</head>
<body>
<!--第一页开始-->
<div class="page" >
    <div class="center"><p>${templateName}</p></div>
    <div><p>iText官网:${ITEXTUrl}</p></div>
    <div><p>FreeMarker官网:${freeMarkerUrl}</p></div>
    <div><p>JFreeChart教程:${JFreeChartUrl}</p></div>
    <!--外部链接-->
    <p>静态logo图</p>
    <div>
        <img src="${imageUrl}" alt="美团点评" width="110" height="20.8"/>
    </div>
    <!--动态生成的图片-->
    <p>气温变化对比图</p>
    <div>
        <img src="${picUrl}" alt="我的图片" width="500" height="270"/>
    </div>
    <!--jfreechart 散点图-->
    <p>发展建议分布图</p>
    <div>
        <img src="${scatterUrl}" alt="散点图" width="700" height="600"/>
    </div>
</div>
<!--第一页结束-->
<!---分页标记-->
<span style="page-break-after:always;"></span>
<!--第二页开始-->
<div class="page">
    <div>第二页开始了</div>
    <div>列表值:</div>
    <div>
    <#list scores as item>
        <div><p>${item}</p></div>
    </#list>
    </div>

</div>


<!--第二页结束-->
</body>
</html>