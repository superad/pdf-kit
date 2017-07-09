package pdf.kit.component.chart;

import com.google.common.collect.Lists;
import freemarker.template.utility.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYBoxAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import pdf.kit.component.chart.model.XYScatter;
import pdf.kit.util.FontUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by fgm on 2017/7/4.
 * 散点图生成
 */
@Slf4j
public class ScatterPlotChart extends ChartFactory {

    private static BufferedImage backgroundImage;

    private static String fileName="scatterChart.png";

    private static int width=700;
    private static int height=600;

    //上方垂直
    private  static double turnTop=4.712389;


    public static String draw(List<XYScatter> list,int picId,String xLabel,String yLabel){
        return draw(list,picId,"",xLabel,yLabel);
    }

    public static  String draw(List<XYScatter>  list,int picId,String title,String xLabel,String yLabel){

        XYDataset xyDataset= ScatterPlotChart.createXYDataSet(list,"");
        String path=LineChart.class.getClassLoader().getResource("").getPath();
        String filePath=path+"/images/"+picId+"/"+fileName;
        File scatterChartFile = new File(filePath);
        if(!scatterChartFile.getParentFile().exists()){
            scatterChartFile.getParentFile().mkdirs();
        }
        try {
            JFreeChart jFreeChart = createChart(xyDataset,list,title,xLabel,yLabel);

            ChartUtilities.saveChartAsJPEG(scatterChartFile ,jFreeChart, width ,height);
        } catch (IOException ex) {
            log.error("散点图生成异常:{}", ExceptionUtils.getFullStackTrace(ex));
            return "";
        }
        return scatterChartFile.getAbsolutePath();
    }



    public static JFreeChart createScatterPlot(String title, String xAxisLabel, String yAxisLabel, XYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls) {
        if(orientation == null) {
            throw new IllegalArgumentException("Null \'orientation\' argument.");
        } else {
            NumberAxis xAxis = new NumberAxis(xAxisLabel);
            xAxis.setAutoRangeIncludesZero(false);
            NumberAxis yAxis = new NumberAxis(yAxisLabel);
            yAxis.setAutoRangeIncludesZero(false);
            XYItemRenderer render=new StandardXYItemRenderer();
            render.setBaseItemLabelsVisible(false);
            XYPlot plot = new XYPlot(dataset, xAxis, yAxis, render);

            StandardXYToolTipGenerator toolTipGenerator = null;
            if(tooltips) {
                toolTipGenerator = new StandardXYToolTipGenerator();
            }

            StandardXYURLGenerator urlGenerator = null;
            if(urls) {
                urlGenerator = new StandardXYURLGenerator();
            }

            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
            renderer.setSeriesItemLabelFont(0,FontUtil.getFont(Font.PLAIN,14));
            renderer.setBaseItemLabelsVisible(false);
            renderer.setBaseToolTipGenerator(toolTipGenerator);
            renderer.setURLGenerator(urlGenerator);
            plot.setRenderer(renderer);
            plot.setOrientation(orientation);
            JFreeChart chart = new JFreeChart(title, FontUtil.getFont(Font.PLAIN,18), plot, legend);
            return chart;
        }
    }



    public static XYDataset createXYDataSet(List<XYScatter> dataList,
                                            String dataName) {
        DefaultXYDataset dataSet = new DefaultXYDataset();
        if(dataList==null ||dataList.size()==0){
            return dataSet;
        }
        int size = dataList.size();
        double[][] data = new double[2][size];
        for (int i = 0; i < size; i++) {
            XYScatter behaviorBO = dataList.get(i);
            double x = behaviorBO.getX();
            double y = behaviorBO.getY();
            data[0][i] = x;
            data[1][i] = y;
        }
        dataSet.addSeries(dataName,data);
        return dataSet;
    }
    public static JFreeChart createChart(XYDataset xydataset, List<XYScatter> dataList,
                                         String title,String xLabel,String yLabel) throws IOException {


        JFreeChart jfreechart = createScatterPlot(title,xLabel,yLabel,
                xydataset, PlotOrientation.VERTICAL, true, true, false);

        jfreechart.getLegend().setItemFont(FontUtil.getFont(Font.PLAIN,14));
        //去掉图例
        jfreechart.getLegend().setBorder(0,0,0,0);

        //画每个坐标点
        Shape shape =  new Ellipse2D.Double(-4,-4,8,8);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setItemLabelFont(FontUtil.getFont(Font.PLAIN, 14));
        for(int i=0;i<xydataset.getSeriesCount();i++){
            renderer.setSeriesShape(i, shape);
            renderer.setSeriesPaint(i, Color.WHITE);
        }
        //初始化背景色和坐标轴
        initBackGroundAndAxis(jfreechart);

        //区间划分
        initRegionPartition(jfreechart);

        //数据标签
        initDataLabels(jfreechart,dataList);

        return jfreechart;
    }

    /**
     * @description 初始化数据标签
     */
    private static void initDataLabels(JFreeChart jfreechart, List<XYScatter> dataList) {
        //数据录入

        if(dataList==null ||dataList.size()==0){
            return;
        }
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        for(XYScatter xyScatter:dataList){
            XYPointerFrameAnnotation pointerAnnotation=new XYPointerFrameAnnotation(
                    xyScatter.getLabel(),
                    xyScatter.getX(),
                    xyScatter.getY(),
                    turnTop
                    );
            pointerAnnotation.setFont(FontUtil.getFont(Font.PLAIN, 13));
            xyplot.addAnnotation(pointerAnnotation);

        }

    }



    /**
     * @description 初始化背景色
     */
    private static void initBackGroundAndAxis(JFreeChart jfreechart) throws IOException {
        jfreechart.setBackgroundPaint(Color.white);
        jfreechart.setBorderPaint(Color.black);
        jfreechart.setBorderStroke(new BasicStroke(1.5f));
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        //无数据显示
        xyplot.setNoDataMessage("没有数据");
        xyplot.setNoDataMessageFont(FontUtil.getFont(Font.PLAIN, 18));
        //背景色
        if(backgroundImage==null){
            String classpath= ScatterPlotChart.class.getClassLoader().getResource("").getPath();
            String backgroundImg=classpath+"/background/back.png";
            backgroundImage = ImageIO.read(new FileInputStream(backgroundImg));

        }
        xyplot.setBackgroundImage(backgroundImage);
        xyplot.setBackgroundImageAlpha(1.0F);

        xyplot.setOutlineStroke(new BasicStroke(1.5f)); // 边框粗细
        ValueAxis vaaxis = xyplot.getDomainAxis();
        vaaxis.setAxisLineStroke(new BasicStroke(1.5f));
        vaaxis.setLabelFont(FontUtil.getFont(Font.PLAIN,14));

        //坐标轴和背景色
        ValueAxis va = xyplot.getDomainAxis(0);
        va.setAxisLineStroke(new BasicStroke(1.5f));
        va.setAxisLineStroke(new BasicStroke(1.5f)); // 坐标轴粗细
        va.setAxisLinePaint(Color.black); // 坐标轴颜色
        va.setLabelPaint(Color.black); // 坐标轴标题颜色
        va.setTickLabelPaint(Color.black); // 坐标轴标尺值颜色
        va.setAxisLineVisible(false);
        va.setLabelFont(FontUtil.getFont(Font.PLAIN, 14));

        ValueAxis axis = xyplot.getRangeAxis();
        axis.setAxisLineStroke(new BasicStroke(1.5f));
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
                .getRenderer();
        xylineandshaperenderer.setSeriesOutlinePaint(0, Color.WHITE);
        xylineandshaperenderer.setUseOutlinePaint(false);
        //x 轴
        NumberAxis xAxis = (NumberAxis) xyplot.getDomainAxis();
        xAxis.setAutoRangeIncludesZero(false);
        xAxis.setAutoRangeStickyZero(false);
        xAxis.setTickMarkInsideLength(2.0F);
        xAxis.setTickMarkOutsideLength(0.0F);
        xAxis.setAxisLineStroke(new BasicStroke(1.5f));
        xAxis.setTickUnit(new NumberTickUnit(25));
        xAxis.setLabelFont(FontUtil.getFont(Font.PLAIN, 14));

        xAxis.setUpperBound(101);//最大值
        xAxis.setLowerBound(0);//最小值

        //y 轴
        NumberAxis yAxis = (NumberAxis) xyplot.getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false);
        yAxis.setAutoRangeStickyZero(false);
        yAxis.setTickMarkInsideLength(2.0F);
        yAxis.setTickMarkOutsideLength(0.0F);
        yAxis.setTickUnit(new NumberTickUnit(25));
        yAxis.setAxisLineStroke(new BasicStroke(1.5f));
        yAxis.setLabelFont(FontUtil.getFont(Font.PLAIN, 14));

        yAxis.setUpperBound(101);//最大值
        yAxis.setLowerBound(0);//最小值

    }



    /**
     * @description 初始化区域划分
     * | 00 | 10  | 20 |
     * | 01 | 11  | 21 |
     * | 02 | 21  | 22 |
     *
     *
     */
    private static void initRegionPartition(JFreeChart jfreechart) {
        Stroke stroke=new BasicStroke(0.5F);
        Paint outlinePaint=Color.WHITE;
        //划分区间
        XYBoxAnnotation box00 = new XYBoxAnnotation(0, 0, 50, 50,stroke,outlinePaint);
        XYBoxAnnotation box01 = new XYBoxAnnotation(0, 50, 50, 75,stroke,outlinePaint);
        XYBoxAnnotation box02 = new XYBoxAnnotation(0, 75, 50, 101,stroke,outlinePaint);

        XYBoxAnnotation box10 = new XYBoxAnnotation(50, 0, 75, 50,stroke,outlinePaint);
        XYBoxAnnotation box11 = new XYBoxAnnotation(50, 50, 75, 75,stroke,outlinePaint);
        XYBoxAnnotation box12 = new XYBoxAnnotation(50, 75, 75, 101,stroke,outlinePaint);

        XYBoxAnnotation box20 = new XYBoxAnnotation(75, 0, 101, 50,stroke,outlinePaint);
        XYBoxAnnotation box21 = new XYBoxAnnotation(75, 50, 101, 75,stroke,outlinePaint);
        XYBoxAnnotation box22 = new XYBoxAnnotation(75, 75, 101, 101,stroke,outlinePaint);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();

        xyplot.addAnnotation(box00);
        xyplot.addAnnotation(box01);
        xyplot.addAnnotation(box02);

        xyplot.addAnnotation(box10);
        xyplot.addAnnotation(box11);
        xyplot.addAnnotation(box12);

        xyplot.addAnnotation(box20);
        xyplot.addAnnotation(box21);
        xyplot.addAnnotation(box22);

        //添加区间标识
        XYTextAnnotation text1 = new XYTextAnnotation("盲区", 4, 98);
        text1.setFont(FontUtil.getFont(Font.PLAIN, 18));
        text1.setPaint(new Color(255,165,0));
        XYTextAnnotation text2 = new XYTextAnnotation("待发展共识区", 10, 3);
        text2.setFont(FontUtil.getFont(Font.PLAIN, 18));
        text2.setPaint(new Color(253, 88, 72));
        XYTextAnnotation text3 = new XYTextAnnotation("潜能区", 96, 3);
        text3.setFont(FontUtil.getFont(Font.PLAIN, 18));
        text3.setPaint(new Color(45, 139, 251));
        XYTextAnnotation text4 = new XYTextAnnotation("优势共识区", 93, 98);
        text4.setFont(FontUtil.getFont(Font.PLAIN, 18));
        text4.setPaint(new Color(20, 149, 134));

        xyplot.addAnnotation(text1);
        xyplot.addAnnotation(text2);
        xyplot.addAnnotation(text3);
        xyplot.addAnnotation(text4);





    }
}
