package pdf.kit.util;

/**
 * Created by fgm on 2017/4/22.
 */
public class FontUtil {


    public static String getFontPath(String fontName){
        String calsspath=FontUtil.class.getClassLoader().getResource("").getPath();
        return calsspath+"/fonts/"+fontName;
    }

}
