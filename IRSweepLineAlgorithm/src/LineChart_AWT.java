import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

   public LineChart_AWT( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Intersections","Time",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( ) {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      dataset.addValue( 8 , "Time" , "3" );
      dataset.addValue( 15 , "Time" , "5" );
      dataset.addValue( 36 , "Time" , "11" );
      dataset.addValue( 39 , "Time" ,  "14" );
      dataset.addValue( 42 , "Time" , "26" );
      //dataset.addValue( 300 , "Time" , "2014" );
      return dataset;
   }
   
 /*  public static void main( String[ ] args ) {
      LineChart_AWT chart = new LineChart_AWT(
         "Time Vs Intersection Points" ,
         "Time vs Number of Intersection Points");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }*/
}