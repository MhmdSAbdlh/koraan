import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Main {
	
	static Font myFont = new Font("Arial", Font.BOLD, 20);
	static Font myFontB = new Font("Arial", Font.BOLD, 25);
	static Font titleF = new Font("Arial", Font.BOLD, 35);
	static Color darkC = new Color(0x161616);
	static Color lightC = new Color(0x346751);
	static Color textC = new Color(0xECDBBA);
	static Border whiteB = BorderFactory.createLineBorder(Main.textC, 1);
	static Border lightB = BorderFactory.createLineBorder(Main.lightC, 1);
	static Border darkB = BorderFactory.createLineBorder(Main.darkC, 1);
	static Border boldB = BorderFactory.createLineBorder(Main.darkC, 3);

	public static void main(String[] args) {
		
		new Frame();
		
	}

}
