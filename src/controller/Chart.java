package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ChartColor;

import java.text.DecimalFormat;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;

import java.awt.Font;

/**
 * Servlet implementation class Chart
 */
@WebServlet("/Chart")
public class Chart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private float a,b,c,d,sum;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			 String result=request.getParameter("data");
			 String[] data=result.split(";");
			 request.setCharacterEncoding("UTF-8");
			 response.setCharacterEncoding("UTF-8");
			 request.getCharacterEncoding();
			 response.getCharacterEncoding();
			 response.setContentType("text/html;charset=UTF-8");
			 
			 // get values
			 sum=Float.parseFloat(data[0])+Float.parseFloat(data[1])+Float.parseFloat(data[2])+Float.parseFloat(data[3]);
			 a=Float.parseFloat(data[0])/sum*100;
			 b=Float.parseFloat(data[1])/sum*100;
			 c=Float.parseFloat(data[2])/sum*100;
			 d=Float.parseFloat(data[3])/sum*100;
			
			 
			 // Create dataset
			 DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
			 dataset.setValue(a, "", "A"); 
			 dataset.setValue(b, "", "B"); 
			 dataset.setValue(c, "", "C");  
			 dataset.setValue(d, "", "D");  
			 
			 // Create Chart
			 JFreeChart chart = ChartFactory.createBarChart3D( 
			    "THỐNG KÊ KẾT QUẢ",  // Chart name 
			    "ĐÁP ÁN",                     // X axis label 
			    "TỈ LỆ (%)",                    // Y axis value 
			    dataset,                        // data set 
			    PlotOrientation.VERTICAL, 
			    false, true, false);
			 
			 // Format Chart
			 CategoryPlot plot = chart.getCategoryPlot(); 
			 plot.setNoDataMessage("NO DATA!"); 
			 plot.setBackgroundPaint(ChartColor.white); 
			 plot.setDomainGridlinesVisible(true); 
			 plot.setDomainGridlinePaint(ChartColor.GRAY); 
			 plot.setDomainGridlinesVisible(true); 
			 plot.setRangeGridlinesVisible(true); 
			 plot.setRangeGridlinePaint(ChartColor.GRAY); 
			 
			 BarRenderer bsr = (BarRenderer) plot.getRenderer();
			 bsr.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}%", new DecimalFormat("0.00")));
			 Font itemLabelFont = bsr.getBaseItemLabelFont();
			 bsr.setBaseItemLabelFont(itemLabelFont.deriveFont(1,11f));
			 bsr.setBaseItemLabelsVisible(true);
			 try  { 
				 // Creating an image 
				 File image = File.createTempFile("image", "tmp");
				 ChartUtilities.saveChartAsPNG(image, chart, 450, 300);
				 
				 //Read Image
				 FileInputStream fileInStream = new FileInputStream(image);
				 OutputStream outStream = response.getOutputStream(); 
				 long fileLength;
				 byte[] byteStream;
				 fileLength = image.length();
				 byteStream = new byte[(int)fileLength];
				 fileInStream.read(byteStream, 0, (int)fileLength);
				
				 //Send Image
				 response.setContentType("image/png");
				 response.setContentLength((int)fileLength);
				 response.setHeader("Cache-Control","no-store,no-cache, must-revalidate, post-check=0, pre-check=0");
				 response.setHeader("Pragma", "no-cache");
				 fileInStream.close();
				 outStream.write(byteStream);
				 outStream.flush();
				 outStream.close();
			 }  catch (IOException e){ 
			    System.err.println("Problem occurred creating chart."); 
			 }  
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
