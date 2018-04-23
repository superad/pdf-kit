package pdf.kit;

import com.google.common.collect.Lists;
import pdf.kit.component.chart.ScatterPlotChart;
import pdf.kit.component.chart.model.XYScatter;

import java.util.List;

/**
 * Created by fgm on 2017/7/9.
 */
public class ScatterPlotChartTest {




    public static void main(String[] args) {
        List<XYScatter> list=getData();
        String path= ScatterPlotChart.draw(list,-1,"他评得分(%)","自评得分(%)");
        System.out.println(path);
    }

    public static List<XYScatter> getData() {
        List<XYScatter> list= Lists.newArrayList();
        XYScatter xy=new XYScatter();
        xy.setX(45);
        xy.setY(55);
        xy.setLabel("主见");
        list.add(xy);
        XYScatter xy1=new XYScatter();
        xy1.setX(75);
        xy1.setY(80);
        xy1.setLabel("定战略");
        list.add(xy1);
        XYScatter xy2=new XYScatter();
        xy2.setX(80);
        xy2.setY(80);
        xy2.setLabel("拿结果");
        list.add(xy2);
        XYScatter xy3=new XYScatter();
        xy3.setX(90);
        xy3.setY(25);
        xy3.setLabel("带团队");
        list.add(xy3);

        return list;
    }
}
