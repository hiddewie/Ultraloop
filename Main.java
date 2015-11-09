import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		GUI window = new GUI();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(300, 100, 600, 400);
		window.setResizable(false);
		window.setIconImage(new ImageIcon("iCal.png").getImage());
		window.setVisible(true);
		
	}
}
