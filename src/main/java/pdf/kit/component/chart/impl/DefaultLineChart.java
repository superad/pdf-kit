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

/**
 * Created by fgm on 2017/5/15.
 * 设置默认的画图工具
 *
 */
@Slf4j
public class DefaultLineChart extends LineChart {
    private int width;
    private int height;
    protected void initPlot(JFreeChart chart, DefaultCategoryDataset dataSet) {

        CategoryPlot plot = chart.getCategoryPlot();
        super.initDefaultXYPlot(plot);
        //设置节点的值显示
        LineAndShapeRenderer lineRender = new LineAndShapeRenderer();
        lineRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineRender.setBaseItemLabelsVisible(true);
        lineRender.setBasePositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        plot.setRenderer(lineRender);

    }
    @Override
    public int getWidth() {
        if(width==0){
            return super.getWidth();
        }
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        if(height==0){
            return super.getHeight();
        }
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}
