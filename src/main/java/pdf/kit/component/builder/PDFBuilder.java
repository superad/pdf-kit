package pdf.kit.component.builder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pdf.kit.exception.PDFException;

import java.io.IOException;

/**
 * 设置页面附加属性
 *
 */
public class PDFBuilder extends PdfPageEventHelper {

   private static Logger log= LoggerFactory.getLogger(PDFBuilder.class);

    //字体文件名
    private String fontFileName;
    // 基础字体对象
    private  BaseFont bf;
    // 利用基础字体生成的字体对象，一般用于生成中文文字
    private  Font fontDetail;
    //文档字体大小
    private  int fontSize=12;
    //模板
    private  PdfTemplate template;
    //数据实体
    private  Object data;
    //页眉页脚定制接口
    private HeaderFooterBuilder headerFooterBuilder;

    //不允许空的构造方法
    private PDFBuilder() {

    }
    public PDFBuilder(HeaderFooterBuilder headerFooterBuilder) {
        this(headerFooterBuilder,null);
    }

    public PDFBuilder(HeaderFooterBuilder headerFooterBuilder, Object data) {
       this(headerFooterBuilder,data,"ping_fang_light.ttf");
    }
    public PDFBuilder(HeaderFooterBuilder headerFooterBuilder, Object data, String fontFileName) {
       this(headerFooterBuilder,data,fontFileName,12);
    }

    public PDFBuilder(HeaderFooterBuilder headerFooterBuilder, Object data, String fontFileName, int fontSize) {
        this.headerFooterBuilder = headerFooterBuilder;
        this.data=data;
        this.fontFileName=fontFileName;
        this.fontSize=fontSize;
    }


    public void onOpenDocument(PdfWriter writer, Document document) {
        template = writer.getDirectContent().createTemplate(50, 50);
    }
    /**
     *
     * 关闭每页的时候，写入页眉,页脚等
     *
     */
    public void onEndPage(PdfWriter writer, Document document) {
        this.addPage(writer, document);
    }

    //加分页
    private void addPage(PdfWriter writer, Document document){
        if(headerFooterBuilder !=null){
            //1.初始化字体
            initFront();
            //2.写入页眉
            headerFooterBuilder.writeHeader(writer,document,data,fontDetail,template);
            //3.写入前半部分页脚
            headerFooterBuilder.writeFooter(writer,document,data,fontDetail,template);
        }
    }
    /**
     *
     * 关闭文档时，替换模板，完成整个页眉页脚组件
     *
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        if(headerFooterBuilder !=null){
            template.beginText();
            template.setFontAndSize(bf,fontSize);
            String replace= headerFooterBuilder.getReplaceOfTemplate(writer,document,data);
            template.showText(replace);
            template.endText();
            template.closePath();
        }
    }

    /**
     * @description 初始化字体
     */
    private void initFront(){
        if(StringUtils.isEmpty(fontFileName)){
            throw new PDFException("PDF文档字体未设置!");
        }
        try {
            if (bf == null) {
                //添加字体，以支持中文
                String classpath=PDFBuilder.class.getClassLoader().getResource("").getPath();
                String fontPath =classpath+"fonts/"+fontFileName;
                //创建基础字体
                bf = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            }
            if (fontDetail == null) {
                fontDetail = new Font(bf, fontSize, Font.NORMAL);// 数据体字体
                log.info("PDF文档字体初始化完成!");
            }
        } catch (DocumentException e) {
            log.error("字体初始化失败{}", ExceptionUtils.getFullStackTrace(e));
            throw new PDFException("字体初始化失败",e);
        } catch (IOException e) {
            log.error("字体初始化失败{}", ExceptionUtils.getFullStackTrace(e));
            throw new PDFException("字体初始化失败",e);
        }
    }

    public int getPresentFontSize() {
        return fontSize;
    }

    public void setPresentFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFileName() {
        return fontFileName;
    }

    public void setFontFileName(String fontFileName) {
        this.fontFileName = fontFileName;
    }
}