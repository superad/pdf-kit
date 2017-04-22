package pdf.kit.component.chart.impl;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import pdf.kit.component.chart.LineChart;

import java.awt.*;

/**
 * Created by fgm on 2017/4/7.
 */
@Slf4j
public class TemperatureLineChart extends LineChart {


    public TemperatureLineChart(){
        this.setFileName("temperature.png");
    }

    protected  void initPlot(JFreeChart chart, DefaultCategoryDataset dataSet){
        CategoryPlot plot = chart.getCategoryPlot();
        super.initDefaultXYPlot(plot);
        //设置节点的值显示
        LineAndShapeRenderer lineRender = new LineAndShapeRenderer();
        lineRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineRender.setBaseItemLabelsVisible(true);
        lineRender.setBasePositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        plot.setRenderer(lineRender);

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        BasicStroke realLine = new BasicStroke(1.8f); // 设置实线
        for (int i = 0; i < dataSet.getRowCount(); i++) {
            //分组线条的名称
            String rowKey=(String)dataSet.getRowKey(i);
            renderer.setSeriesStroke(i, realLine); // 利用实线绘制
            if(i%2==0){
                Color companyLineColor=new Color(0,88,162);
                renderer.setSeriesPaint(i, companyLineColor);
            }else{
                Color selfLineColor=Color.RED;
                renderer.setSeriesPaint(i, selfLineColor);
            }


        }


    }




}
