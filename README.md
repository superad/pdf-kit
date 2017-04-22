# pdf-kit
java根据模板动态生成PDF文件

一、需求说明：

     根据业务需要，需要在服务器端生成可动态配置的PDF文档，方便数据可视化查看。


二、解决方案：

      iText+FreeMarker+JFreeChart生成可动态配置的PDF文档
      
      iText有很强大的PDF处理能力，但是样式和排版不好控制，直接写PDF文档，数据的动态渲染很麻烦。
      
      FreeMarker能配置动态的html模板，正好解决了样式、动态渲染和排版问题。
      
      JFreeChart有这方便的画图API，能画出简单的折线、柱状和饼图，基本能满足需要。

三、实现功能：

       1、能动态配置PDF文档内容
       
       2、能动态配置中文字体显示
       
       3、设置自定义的页眉页脚信息
       
       4、能动态生成业务图片
       
       5、完成PDF的分页和图片的嵌入
       
四、主要代码结构说明：

      1、component包：PDF生成的组件 对外提供的是PDFKit工具类和HeaderFooterBuilder接口， 其中PDFKit负责PDF的生成，HeaderFooterBuilder负责自定义页眉页脚信息。
      
      2、builder包：负责PDF模板之外的额外信息填写，这里主要是页眉页脚的定制。
      
      3、chart包：JFreeChart的画图工具包，目前只有一个线形图。
      
      4、test包：测试工具类
      
      5、util包：FreeMarker等工具类。  
      
       
       
       
       
       
