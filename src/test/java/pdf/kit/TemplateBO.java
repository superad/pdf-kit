package pdf.kit;

import lombok.Data;

import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 */
@Data
public class TemplateBO {

    private String templateName;

    private String freeMarkerUrl;

    private String ITEXTUrl;

    private String JFreeChartUrl;

    private List<String> scores;

    private String imageUrl;

    private String picUrl;

    private String scatterUrl;


}
