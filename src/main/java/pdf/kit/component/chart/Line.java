package pdf.kit.component.chart;

import lombok.Data;

/**
 * Created by fgm on 2017/4/7.
 */
@Data
public class Line {
    private double yValue;
    private String  xValue;
    private String groupName;
    public Line(){

    }
    public Line(double yValue, String xValue, String groupName){
        this.yValue=yValue;
        this.xValue=xValue;
        this.groupName=groupName;
    }



}
