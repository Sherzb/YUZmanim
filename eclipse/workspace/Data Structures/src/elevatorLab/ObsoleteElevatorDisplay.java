package elevatorLab;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.*;

class ObsoleteElevatorDisplay extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 700;

	public ObsoleteElevatorDisplay() {
		setTitle("Elevator GUI");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		ElevatorDrawing component = new ElevatorDrawing();
		add(component);
	}
}

class ElevatorDrawing extends JComponent
{
	private static final long serialVersionUID = -2680389751495842988L;
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 700;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		double leftX = 150;
		double topY = 100;
		double floorHeight = (.9 * DEFAULT_HEIGHT) / (Simulator.NUM_FLOORS);

		//Draw  dividing line2
		g2.setStroke(new BasicStroke(10));
		g2.draw(new Line2D.Double(leftX, topY - 100, leftX, topY + 600));	
		g2.setStroke(new BasicStroke(1));
		g2.draw(new Line2D.Double(leftX - 90, topY - 100, leftX - 90, topY + 600));
		
		//Draw information tabs
		g2.drawString("Floor", 7, 12);
		g2.drawString("Number", 7, 27);
		g2.drawString("Number of", 70, 12);
		g2.drawString("People", 75, 27);
		g2.drawString("Elevators", 160, 20);
		
		//Fill in Floor Number
		for(int i = 1; i <= Simulator.NUM_FLOORS; i++) {
			int inverseNum = Simulator.NUM_FLOORS - i + 2;
			String floorNumber = "" + i;
			g2.drawString(floorNumber, 15, (int) (((inverseNum) * floorHeight) - 15));
		}
		
		//Fill in occupants per floor
		for(int i = 1; i <= Simulator.NUM_FLOORS; i++) {
			int peopleOnFloor = Simulator.getAllFloors()[i-1].peopleOnFloor();
			int inverseNum = Simulator.NUM_FLOORS - i + 2;
			String peopleString = "" + peopleOnFloor;
			g2.drawString(peopleString, 90, (int) ((inverseNum) * floorHeight) - 15);
		}
		
		//Draw how long it's been running, and the average wait time
		g2.drawString("Time passed:", 250, 20);
		String timePassed = "" + Simulator.getTotalTime();
		g2.drawString(timePassed + "", 330, 20);
		g2.drawString("Average Wait Time (rounded):", 430, 20);
		DecimalFormat df = new DecimalFormat("#.##");
		String averageWaitString = df.format(Simulator.getAverageWait());
		g2.drawString(averageWaitString, 600, 20);

		//Draw the floors lines. Each line will be (0.9 * DEFAULT_HEIGHT) / (Simulator.NUM_FLOORS - 1) from the bottom, times a constant
		for(double i = 1; i <= Simulator.NUM_FLOORS; i++) {
			g2.draw(new Line2D.Double(0, i * floorHeight, 700, i * floorHeight));
		}

		//Draw the Elevators on their correct floors
		double elevatorOffset = 550 / (Simulator.NUM_ELEVATORS + 1);
		for(int i = 1; i <= Simulator.NUM_ELEVATORS; i++) {
			Elevator elevator = Simulator.getAllElevators()[i - 1];
			if(elevator != null) {
				int floor = elevator.getFloor();
				Rectangle2D elevatorDrawing = new Rectangle2D.Double(150 + i * elevatorOffset, floorHeight * (Simulator.NUM_FLOORS - floor + 1), 30, floorHeight);
				g2.setPaint(Color.GREEN);
				g2.draw(elevatorDrawing);
				g2.fill(elevatorDrawing);
				g2.setPaint(Color.BLACK);	
				int stringHeight = (int) floorHeight * (Simulator.NUM_FLOORS - floor + 1) + 15;
				String info = elevator.getTotalPeople() + "";
				g2.drawString("   " + info, (int) (150 + i * elevatorOffset), stringHeight);
			}
			
		}
	}
}