import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI extends JFrame {
	
	/* PRIVATE */
	private Team [] team = new Team [100];
	private String [][] aFull = new String [5][500];
	private String [][] aList = new String [5][15];
	private int pop = 0;
	private Icon green = new ImageIcon (getClass().getResource("images/Green.png"));
	private Timer timer = new Timer(1000, new ActionListener () {
		public void actionPerformed (ActionEvent e){
			time++;
			dispTime (); }}
	);
	
	/* JCOMPONENTS */
	private JPanel panel = new JPanel();
	private JTextField tfNr = new JTextField ("");
	private JButton bStart = new JButton ("Start      ");
	private JButton bPause = new JButton ("Pause   ");
	private JButton bStop = new JButton ("Stop       ");
	private JList lList1 = new JList (aList[0]);
	private JList lList2 = new JList (aList[1]);
	private JList lList3 = new JList (aList[2]);
	private JList lList4 = new JList (aList[3]);
	private JList lList5 = new JList (aList[4]);
	private JButton bOK = new JButton ("OK");
	private JButton bRemove = new JButton ("Remove last item");
	private JButton bFile = new JButton ("to File");
	private JTextArea taTijd = new JTextArea ("00:00:00", 10, 4);
	
	final JTextArea taStrtNr = new JTextArea ("Voer startnummer in:");
	
	/* STATIC */
	static int time = 0;
	static int pTime;
	
	/* CONSTANTS */
	public final String NEWLINE = System.getProperty("line.separator");
	
	/* INIT */
	GUI () {
		// Set title //
		super ("Ultraloop");
		addToWindow ();
		setPos ();	
		setProperties ();
		initTeams ();
		
		// Add ActionListeners //
		handler HO = new handler ();
		bStart.addActionListener(HO);
		bPause.addActionListener(HO);
		bStop.addActionListener(HO);
		bOK.addActionListener(HO);
		bRemove.addActionListener(HO);
		bFile.addActionListener(HO);
		tfNr.addActionListener(HO);
		
		// Final add //
		add(panel);
	}
	private void addToWindow (){
		add(tfNr);
		add(bStart);
		add(bPause);
		add(bStop);
		add(lList1);
		add(lList2);
		add(lList3);
		add(lList4);
		add(lList5);
		add(bOK);
		add(bRemove);
		add(bFile);
		add(taTijd);
		add(taStrtNr);
	}
	private void setPos () {
		tfNr.setBounds(20,25, 75,25);
		bStart.setBounds(450,155, 100,25);
		bPause.setBounds(450,190, 100,25);
		bStop.setBounds(450,225, 100,25);
		lList1.setBounds(20,75, 45,272);
		lList2.setBounds(65,75, 40,272);
		lList3.setBounds(105,75, 25,272);
		lList4.setBounds(130,75, 150,272);
		lList5.setBounds(280,75, 150,272);
		bOK.setBounds(110, 25, 75, 25);
		bRemove.setBounds(220,28, 150,20);
		bFile.setBounds(450,322, 100,25);
		taTijd.setBounds(445,10, 150,50);
		taStrtNr.setBounds(20, 5, 150, 25);
	}
	private void setProperties () {
		// Set Editable //
		taTijd.setEditable(false);
		taStrtNr.setEditable(false);
		
		// Set Font //
		taTijd.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 28));
		bRemove.setFont (new Font("Arial", Font.PLAIN, 12));
		
		// Set List properties //
		lList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lList4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lList5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lList1.setVisibleRowCount(15);
		lList2.setVisibleRowCount(15);
		lList3.setVisibleRowCount(15);
		lList4.setVisibleRowCount(15);
		lList5.setVisibleRowCount(15);
		lList1.setBorder(BorderFactory.createLineBorder(Color.black));
		lList2.setBorder(BorderFactory.createLineBorder(Color.black));
		lList3.setBorder(BorderFactory.createLineBorder(Color.black));
		lList4.setBorder(BorderFactory.createLineBorder(Color.black));
		lList5.setBorder(BorderFactory.createLineBorder(Color.black));
		lList1.setBackground(new Color(180,180,180));
		lList2.setBackground(new Color(180,180,180));
		lList3.setBackground(new Color(180,180,180));
		lList4.setBackground(new Color(180,180,180));
		lList5.setBackground(new Color(180,180,180));
	
		panel.setBackground(Color.WHITE);
		tfNr.setBackground(new Color(230,230,230));
		tfNr.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		bStop.setIcon(green);
		bStart.setHorizontalTextPosition(SwingConstants.LEFT);
		bStart.setHorizontalAlignment(SwingConstants.LEADING);
		bPause.setHorizontalTextPosition(SwingConstants.LEFT);
		bPause.setHorizontalAlignment(SwingConstants.LEADING);
		bStop.setHorizontalTextPosition(SwingConstants.LEFT);
		bStop.setHorizontalAlignment(SwingConstants.LEADING);
	}
	private void initTeams (){
		for (int x=0; x<team.length; x++){
			team[x] = new Team();
		}
	}
	
	/* METHODS */
	private void addOne (String nr) {
		int number = Integer.parseInt(nr);
		int tm = number/10;
		this.team [tm].addLap(time);
		
		aFull[0][pop] = nr;
		aFull[1][pop] = String.format("%d", number/10);
		aFull[2][pop] = String.format("%d",team[tm].getLap());
		aFull[3][pop] = getTime(team[tm].getLastLap());
		aFull[4][pop] = getTime(team[tm].getLastTTime());
		
		if (pop < 14){
			for (int x=0; x <5; x++)
				aList[x] = aFull[x].clone();
		}
		else {
			for (int x=0; x <5; x++)
				aList[x] = copyArray(aFull[x],pop);
		}
		
		lList1.setListData(aList[0]);
		lList2.setListData(aList[1]);
		lList3.setListData(aList[2]);
		lList4.setListData(aList[3]);
		lList5.setListData(aList[4]);
		
		pop++;
		
		tfNr.setText("");
	}
	private void removeOne (){
		
		pop--;
		if (pop > 0){
			int number = Integer.parseInt(aFull[0][pop-1]);
			int tm = number/10;
			this.team [tm].removeLap();
			
			for (int x=0; x<5; x++)
				aFull[x][pop] = null;
			
			if (pop < 15){
				for (int x=0; x <5; x++)
					aList[x] = aFull[x].clone();
			}
			else {
				for (int x=0; x <5; x++)
					aList[x] = copyArrayBack(aFull[x],pop);
			}
		}
		else {
			for (int x=0; x<5; x++)
				aFull[x][pop] = null;
			for (int x=0; x <5; x++)
				aList[x] = aFull[x].clone();
		}
		
		lList1.setListData(aList[0]);
		lList2.setListData(aList[1]);
		lList3.setListData(aList[2]);
		lList4.setListData(aList[3]);
		lList5.setListData(aList[4]);
	}
	private String getTime (){
		int h, m, s;
		h = time/3600;
		m = (time%3600)/60;
		s = ((time%3600)%60);
		return String.format("%02d:%02d:%02d",h,m,s);
	}
	private String getTime (int t){
		int h, m, s;
		h = t/3600;
		m = (t%3600)/60;
		s = ((t%3600)%60);
		return String.format("%02d:%02d:%02d",h,m,s);
	}
	private void dispTime () {
		taTijd.setText(getTime());
	}
	private String[] copyArray (String[] array, int pop) {
		String[] newarray = new String[15];
		
		for (int x=pop-14, y=0; x<=pop; x++,y++){
			newarray[y] = array[x];
		}
		return newarray;
	}
	private String[] copyArrayBack (String[] array, int pop){
		String[] newarray = new String[15];
		
		for (int x=pop-14, y=0; x<=pop; x++,y++){
			newarray[y] = array[x];
		}
		return newarray;
	}
	
	private void toFile () {
		try {
			final Formatter x = new Formatter ("output.txt");
			if (new File("output.txt").exists()){
				x.format("Nr\t\tTeam\tLaps\tL-Time\t\tT-Time"+NEWLINE+NEWLINE);
				for (int i=0; i<pop; i++){
					x.format("%s\t\t%s\t\t%s\t\t%s\t%s"+NEWLINE,aFull[0][i],aFull[1][i],aFull[2][i],aFull[3][i],aFull[4][i]);
				}
			}
			x.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
	/* IMPLEMENTED */
	private class handler implements ActionListener {
		public void actionPerformed (ActionEvent e){
			if (e.getSource()==bStart){
				if (!timer.isRunning()){
					bStart.setIcon(green);
					bPause.setIcon(null);
					bStop.setIcon(null);
					
					timer.start();
				}
			}
			else if (e.getSource () == bPause){
				if (timer.isRunning()){
					bStart.setIcon(null);
					bPause.setIcon(green);
					bStop.setIcon(null);
					
					pTime = time;
					timer.stop();
				}
				else if (pTime != 0){
					bStart.setIcon(green);
					bPause.setIcon(null);
					bStop.setIcon(null);
					
					timer.start();
					time = pTime;
				}
			}
			else if (e.getSource() == bStop){
				if (timer.isRunning()){
					bStart.setIcon(null);
					bPause.setIcon(null);
					bStop.setIcon(green);
					
					timer.stop();
					time = 0;
					dispTime();
				}
			}
			else if (e.getSource() == bOK){
				if (timer.isRunning()){
					if (tfNr.getText() != "")
						addOne(tfNr.getText());
					else
						JOptionPane.showMessageDialog(null, "Type a number into the box.");
				}
				else {
					JOptionPane.showMessageDialog(null, "Start the timer before you add numbers to the list.");
				}
			}
			else if (e.getSource() == tfNr){
				if (timer.isRunning()){
					if (tfNr.getText() != "")
						addOne(tfNr.getText());
					else
						JOptionPane.showMessageDialog(null, "Type a number into the box.");
				}
				else {
					JOptionPane.showMessageDialog(null, "Start the timer before you add numbers to the list.");
				}
			}
			else if (e.getSource() == bRemove){
				if (pop >= 1)
					removeOne();
			}
			else if (e.getSource() == bFile){
				if (pop >= 1)
					toFile ();
			}
		}
	}
}
