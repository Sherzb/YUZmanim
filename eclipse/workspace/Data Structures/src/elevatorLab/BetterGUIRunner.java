package elevatorLab;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.*;

class BetterGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 698;

	public BetterGUI() {
		setTitle("Elevator GUI");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		BetterDrawing component = new BetterDrawing();
		add(component);
	}
}

class BetterDrawing extends JComponent
{
	private static final long serialVersionUID = -2680389751495842998L;
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 695;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		int NUM_FLOORS = Simulator.NUM_FLOORS;
		int NUM_ELEVATORS = Simulator.NUM_ELEVATORS;

		int topBorder = 30;
		int bottomBorder = 660;
		int leftBorder = 160;
		int rightBorder = 685;
		int totalHeight = bottomBorder - topBorder;
		int totalWidth = rightBorder - leftBorder;
		double floorHeight = (double) totalHeight / (double) NUM_FLOORS;

		//Draw border lines
		//Left
		g2.setStroke(new BasicStroke(10));
		g2.draw(new Line2D.Double(leftBorder - 5, 0, leftBorder - 5, 655));	
		//Leftmost
		g2.setStroke(new BasicStroke(1));
		g2.draw(new Line2D.Double(leftBorder - 95, 0, leftBorder - 95, 660));
		//Right
		g2.draw(new Line2D.Double(rightBorder, 0, rightBorder, 660));
		//Top
		g2.draw(new Line2D.Double(0, topBorder, 685, topBorder));
		//Bottom
		g2.draw(new Line2D.Double(0, bottomBorder, 685, bottomBorder));

		//Draw information things
		g2.drawString("Floor", 7, 12);
		g2.drawString("Number", 7, 27);
		g2.drawString("Number of", 70, 12);
		g2.drawString("People", 75, 27);

		//Draw floor dividers, from the top down
		for(int i = 1; i <= NUM_FLOORS; i++) {
			g2.draw(new Line2D.Double(0, topBorder + i * floorHeight, 685, topBorder + i * floorHeight));
		}
		//Draw floor numbers
		for(int i = 1; i <= NUM_FLOORS; i++) {
			String floorNumber = (NUM_FLOORS - i + 1) + "";
			g2.drawString(floorNumber, 20,(int) (topBorder + i * floorHeight - (3 * floorHeight / 10)));
		}
		
		//Fill in occupants per floor
		for(int i = 1; i <= NUM_FLOORS; i++) {
			String peopleOnFloor = Simulator.getAllFloors()[NUM_FLOORS - i].peopleOnFloor() + "";
			//int inverseNum = NUM_FLOORS - i + 2;
			g2.drawString(peopleOnFloor, 90, (int) (topBorder + i * floorHeight - (3 * floorHeight / 10)));
		}

		//Draw total time passed
		g2.drawString("Time passed:", 165, 20);
		String timePassed = "" + Simulator.getTotalTime();
		g2.drawString(timePassed + "", 245, 20);
		
		//Draw people dropped off
		g2.drawString("People dropped off:", 295, 20);
		String totalPeople = "" + Simulator.getTotalPeople();
		g2.drawString(totalPeople, 407, 20);

		//Draw average wait time
		g2.drawString("Average Wait Time (rounded):", 460, 20);
		DecimalFormat df = new DecimalFormat("#.##");
		String averageWaitString = df.format(Simulator.getAverageWait());
		g2.drawString(averageWaitString, 630, 20);

		//Draw elevators
		int elevatorWidth;
		if(NUM_ELEVATORS <= 40) {
			elevatorWidth = 50 - NUM_ELEVATORS;
		}
		else {
			elevatorWidth = 10;
		}
		double elevatorOffset = (double)((totalWidth - (NUM_ELEVATORS * elevatorWidth))) / (double)(NUM_ELEVATORS + 1);
		for(int i = 1; i <= NUM_ELEVATORS; i++) {
			Elevator elevator = Simulator.getAllElevators()[i - 1];
			double leftPosition = leftBorder + i * elevatorOffset + ((i - 1) * elevatorWidth);
			double bottomPosition = topBorder + ((NUM_FLOORS) - elevator.getFloor()) * floorHeight;
			Rectangle2D elevatorDrawing = new Rectangle2D.Double(leftPosition, bottomPosition, elevatorWidth, floorHeight);
			g2.setPaint(Color.GREEN);
			g2.fill(elevatorDrawing);
			g2.setPaint(Color.BLACK);

			//And the number of occupants
			String peopleOnFloor = elevator.getTotalPeople() + "";
			g2.drawString(peopleOnFloor, (int) (leftPosition + (2 * elevatorWidth / 5 - 2)),(int) (bottomPosition + (2 * floorHeight / 5)));
		}
	}
}

