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
	
    Point pointStart = new Point();
    Point pointEnd   = new Point();
    Point prevPoint = new Point();
    Point newPoint = null;
    
    private JLabel displayInstruction1 = new JLabel();
    private JLabel displayInstruction = new JLabel();
    
    ArrayList<Point> intersectionPoints = new ArrayList<Point>();
    ArrayList<Line> lines = new ArrayList<Line>();
    
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
			
			for (int i = 0; i<lines.size(); i++) 
			{
				for(int j = i+1; j < lines.size(); j++)
				{
					tmp = checkGetIntersection(lines.get(i), lines.get(j));
					if(tmp != null && !intersectionPoints.contains(tmp))
					{
						intersectionPoints.add(tmp);
					}
				}
			}
			
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

	public Point checkGetIntersection(Line l1, Line l2)
	{
		double A1, A2, B1, B2, C1, C2, x = 0.0, y = 0.0, determinant;
		
		A1 = l1.getEnd().getY() - l1.getStart().getY();
		B1 = l1.getStart().getX() - l1.getEnd().getX();
		C1 = (A1 * l1.getStart().getX()) + (B1 * l1.getStart().getY());
		
		A2 = l2.getEnd().getY() - l2.getStart().getY();
		B2 = l2.getStart().getX() - l2.getEnd().getX();
		C2 = (A2 * l2.getStart().getX()) + (B2 * l2.getStart().getY());
		
		determinant = (A1 * B2) - (A2 * B1);
		
		if(determinant != 0)
		{
			x = ((B2 * C1) - (B1 * C2))/determinant;
			y = ((A1 * C2) - (A2 * C1))/determinant;
		}
		
		if(x <= l1.getEnd().getX() 
				&& x <= l2.getEnd().getX() 
				&& x >= l1.getStart().getX() 
				&& x >= l2.getStart().getX())
		{
			newPoint = new Point((int)x, (int)y);
			
			return newPoint;
		}
		else
		{
			return null;
		}
			
	}
	
}
