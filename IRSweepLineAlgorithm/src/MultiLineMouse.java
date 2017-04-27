import java.io.*;
import java.lang.*;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.color.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
//import java.awt.Point;
import java.awt.event.MouseMotionListener;

public class MultiLineMouse extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	
	// List of all abscissae
	ArrayList<Abscissae> initialPoints_E = new ArrayList<Abscissae>();
	ArrayList<Double> removed_E = new ArrayList<Double>();
	
	// List to store lines
	ArrayList<Line> lineQueue_S = new ArrayList<Line>();
	ArrayList<Double> sortedIntersections_L = new ArrayList<Double>();
	ArrayList<Double> sortedIntersections_L_Y = new ArrayList<Double>();

	ArrayList<SLPoint> allPoints = new ArrayList<SLPoint>();
	ArrayList<Line> allLines = new ArrayList<Line>();
	
	public final int LEFT_END_POINT = 0;
	public final int RIGHT_END_POINT = 1;
	public final int INTERSECTION_POINT = 2;
	public static int pointID = 200;
	public static int pointIdIntersect = 5000;
	public static int lineID = 800;
	public final int NULL_ID = -100000;
	int iteration = 0;
    
    private JLabel displayInstruction1 = new JLabel();
    private JLabel displayInstruction = new JLabel();
    
	private boolean mouseActionEnabled = true;
	private boolean mouseDragged = false;
	private boolean sectionFlag = false;
	private boolean initialFlag = true;
	
	private static final long serialVersionUID = 1L;
	private static final Dimension PREFERRED_SIZE = new Dimension(1750, 1024);
	
	public MultiLineMouse() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	public static void main(String args[]) throws Exception {
		
		JFrame frame = new JFrame("Sweep Line Algorithm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(PREFERRED_SIZE);
		frame.setContentPane(new MultiLineMouse());
		frame.pack();
	    frame.setVisible(true);    
	}
	
	//request focus to the key listener
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() == 'n') 
		{
			Point tmp;
			mouseActionEnabled = false;
			
//TODO begin sweep line
			
			displayInstruction.setText("Press 'i' to see the intersection points!!");	
		}
		else if(e.getKeyChar() == 'i')
		{
			sectionFlag = true;
			repaint();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseDragged = true;
		prevPoint = e.getPoint();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// For new start point
		pointStart = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		pointStart = new Point();
		pointStart = e.getPoint();	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		pointEnd = e.getPoint();
		lines.add(new Line(pointStart, pointEnd));
		mouseDragged = false;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	//display GUI
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g.create();

		displayInstruction.setFont(new Font("Verdana",1,20));
		displayInstruction.setBounds(0, 0, 1750, 100);
		add(displayInstruction);
		
		if(initialFlag)
		{
			displayInstruction.setText("Draw intersecting lines using the mouse. When done press 'n'");
			initialFlag = false;
		}
		
		displayInstruction1.setFont(new Font("Verdana",1,20));
		displayInstruction1.setBounds(0, 30, 1750, 100);
		add(displayInstruction1);
		
		if(mouseDragged)
		{
			draw(g2d, pointStart, prevPoint);
		}
		
    	for(final Line r : lines)
    	{
    		//draw((int)r.getStart().getX(), (int)r.getStart().getY(), (int)r.getEnd().getX(), (int)r.getEnd().getY());
    		draw(g2d,r.getStart(), r.getEnd());	
    	}
    	
    	if(sectionFlag)
    	{
    		displayInstruction1.setText("Intersection Point:" + intersectionPoints.toString());
    		
    		for(Point p : intersectionPoints)
    		{
    			draw(g2d, null, p);
    		}
    		//sectionFlag = false;
    	}
		repaint();

	}
	
	private void draw(Graphics2D g, Point p1, Point p2)
	{
		if(p1 == null) {
			if(sectionFlag)
			{
				g.setColor(Color.red);
				g.fillOval(p2.x, p2.y, 8, 8);
			}
			else
			{
				g.setColor(Color.black);
				g.fillOval(p2.x, p2.y, 8, 8);
			}
		} else {
			int x1 = p1.x;
			int y1 = p1.y;

			int x2 = p2.x;
			int y2 = p2.y;

			//g.setColor(Color.green.darker());
			g.setColor(Color.black);
			g.drawLine(x1 + 3, y1 + 3, x2 + 3, y2 + 3);
			//g.drawLine(x1, y1, x2, y2);
			//g.drawLine(x1 + 4, y1 + 4, x2 + 4, y2 + 4);
			//g.drawLine(x1 + 5, y1 + 5, x2 + 5, y2 + 5);

			//g.setColor(Color.red);
			g.setColor(Color.blue);
			g.fillOval(x1, y1, 8, 8);

			//g.setColor(Color.blue);
			g.fillOval(x2, y2, 8, 8);
		}
	}
	
}
