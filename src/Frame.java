import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Frame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	JButton[] label;
	ArrayList<Integer> index = new ArrayList<Integer>();
	JLabel sFinished = new JLabel("السور المنجزة: "+index.size());
	JLabel sRemaining = new JLabel("السور المتبقية: 62");
	JLabel todayS = new JLabel("سورة اليوم: غير محدد");
	JLabel titleF = new JLabel();
	final JLabel credit = new JLabel("تصميم و برمجة محمد عبدالله");
	JProgressBar progress = new JProgressBar();
	StringBuffer sb;
	
	Frame(){
		//Frame
		this.setTitle("ختمية قرآن كريم");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1300, 800);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Main.lightC);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
		
		//Panel
		new Data();
		JPanel panel = new JPanel();
		label = new JButton[Data.sowar.size()];
		panel.setBounds(10, 120, 1260, 500);
		panel.setLayout(new GridLayout(9, 7));
		panel.setBorder(Main.darkB);
		JButton emptyB = new JButton();
		emptyB.setEnabled(false);
		emptyB.setForeground(Main.textC);
		emptyB.setBackground(Main.darkC);
		emptyB.setBorder(Main.lightB);
		for(int i=0;i<Data.sowar.size();i++) {
			label[i] = new JButton(Data.sowar.get(i));
			label[i].addActionListener(this);
			label[i].setFont(Main.myFont);
			label[i].setFocusable(false);
			label[i].setForeground(Main.textC);
			label[i].setBackground(Main.darkC);
			label[i].setBorder(Main.lightB);
			label[i].setHorizontalAlignment(0);
			panel.add(label[i]);
			if(i==55)
				panel.add(emptyB);
		}
		
		//ProgressBar
		openProgress();
		progress.setBounds(10, 100, 1260, 20);
		progress.setValue(100*index.size()/Data.sowar.size());
		progress.setForeground(Main.textC);
		progress.setBackground(Main.lightC);
		progress.setBorder(Main.darkB);
		progress.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		//Label
		titleF.setText(this.getTitle());
		titleF.setBounds(500, 20, 300, 70);
		titleF.setHorizontalAlignment(0);
		titleF.setFont(Main.titleF);
		titleF.setForeground(Main.darkC);
		titleF.setBackground(Main.textC);
		titleF.setBorder(Main.boldB);
		titleF.setOpaque(true);
		credit.setBounds(0, 720, 1300, 50);
		credit.setHorizontalAlignment(0);
		credit.setFont(Main.myFont);
		credit.setForeground(Main.textC);
		sFinished.setBounds(50, 650, 150, 50);
		sFinished.setHorizontalAlignment(0);
		sFinished.setFont(Main.myFont);
		sFinished.setForeground(Main.textC);
		sFinished.setBackground(Main.lightC);
		sFinished.setBorder(Main.darkB);
		sFinished.setOpaque(true);
		sRemaining.setBounds(250, 650, 150, 50);
		sRemaining.setHorizontalAlignment(0);
		sRemaining.setForeground(Main.textC);
		sRemaining.setBackground(Main.lightC);
		sRemaining.setBorder(Main.darkB);
		sRemaining.setOpaque(true);
		sRemaining.setFont(Main.myFont);
		todayS.setBounds(490, 645, 320, 70);
		todayS.setHorizontalAlignment(0);
		todayS.setForeground(Main.darkC);
		todayS.setBackground(Main.textC);
		todayS.setBorder(Main.boldB);
		todayS.setOpaque(true);
		todayS.setFont(Main.myFontB);
		
		//Button
		JButton undoB = new JButton("تراجع");
		JButton randBtn = new JButton("اختيار عشوائي لصورة اليوم");
		JButton clearB = new JButton("مسح");
		undoB.setBounds(1100, 640, 150, 50);
		undoB.addActionListener( e -> undonSelection());
		clearB.addActionListener( e -> clearBtn());
		clearB.setBounds(900, 640, 150, 50);
		randBtn.setBounds(950, 700, 250, 50);
		randBtn.addActionListener( e -> randomSelection());
		btnStyle(undoB);
		btnStyle(clearB);
		btnStyle(randBtn);
		
		//Add to frame
		this.add(progress);
		this.add(randBtn);
		this.add(credit);
		this.add(titleF);
		this.add(todayS);
		this.add(clearB);
		this.add(undoB);
		this.add(sFinished);
		this.add(sRemaining);
		this.add(panel);
		this.setVisible(true);
	}

	private void openProgress() {
		try {
			Scanner opennedF = new Scanner(new File("saved.txt"));
			if(opennedF.hasNext()) {
				int j=0;
				while(opennedF.hasNext()) {
					j=opennedF.nextInt();
					label[j].setBackground(Main.lightC);
					label[j].setBorder(Main.darkB);
					label[j].setText(label[j].getText()+"√");
					index.add(j);
					}
				sb = new StringBuffer(label[index.get(index.size()-1)].getText());
				sFinished.setText("السور المنجزة: "+index.size());
				sRemaining.setText("السور المتبقية: "+(Data.sowar.size()-index.size()));
				todayS.setText("سورة اليوم: "+sb.deleteCharAt(sb.length()-1));
				}
			opennedF.close();
			} catch (FileNotFoundException e) {}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<Data.sowar.size();i++)
			if(e.getSource() == label[i] && !(label[i].getBorder()==Main.darkB)) {
				label[i].setBackground(Main.lightC);
				label[i].setBorder(Main.darkB);
				label[i].setText(label[i].getText()+"√");
				index.add(i);
				sb = new StringBuffer(label[index.get(index.size()-1)].getText());
				sFinished.setText("السور المنجزة: "+index.size());
				sRemaining.setText("السور المتبقية: "+(Data.sowar.size()-index.size()));
				todayS.setText("سورة اليوم: "+sb.deleteCharAt(sb.length()-1));
				}
		progress.setValue(100*index.size()/Data.sowar.size());
		saveProgress();
		endMessage();
	}

	private void endMessage() {
		String[] optionS = {"خروج","دعاء ختم القرآن","ختمية جديدة؟"};
		JTextPane endK = new JTextPane ();
		SimpleAttributeSet attribs = new SimpleAttributeSet();
		StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setFontSize(attribs, 20);
		StyleConstants.setFontFamily(attribs, "Arial");
		endK.setParagraphAttributes(attribs, true);
		endK.setText(Data.endK);
		endK.setEditable(false);
		int actionO;
		if(index.size() == Data.sowar.size()) {
			actionO = JOptionPane.showOptionDialog(this, "أحسنت, لقد ختمت القرآن الكريم كاملاً", "ختمية القرآن الكريم", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, optionS, 1);
			if(actionO == 2)
				clearBtn();
			else
				if(actionO ==1)
					JOptionPane.showMessageDialog(this, endK, "دعاء ختم القرآن", JOptionPane.PLAIN_MESSAGE);
				else
					System.exit(0);
			}
	}
	
	private void randomSelection() {
		if(index.size()<Data.sowar.size()) {
			Random random = new Random();
			int randomN = random.nextInt(Data.sowar.size());
			while(index.contains(randomN))
				randomN = random.nextInt(Data.sowar.size());
			label[randomN].setBackground(Main.lightC);
			label[randomN].setBorder(Main.darkB);
			label[randomN].setText(label[randomN].getText()+"√");
			index.add(randomN);		
			sb = new StringBuffer(label[index.get(index.size()-1)].getText());
			sFinished.setText("السور المنجزة: "+index.size());
			sRemaining.setText("السور المتبقية: "+(Data.sowar.size()-index.size()));
			todayS.setText("سورة اليوم: "+sb.deleteCharAt(sb.length()-1));
			progress.setValue(100*index.size()/Data.sowar.size());
			saveProgress();
			}
		endMessage();
	}
	
	private void undonSelection() {
		if(index.size()>0) {
			sb= new StringBuffer(label[index.get(index.size()-1)].getText());
			label[index.get(index.size()-1)].setBackground(Main.darkC);
			label[index.get(index.size()-1)].setBorder(Main.lightB);
			label[index.get(index.size()-1)].setText(sb.deleteCharAt(sb.length()-1)+"");
			index.remove(index.size()-1);
			sFinished.setText("السور المنجزة: "+index.size());
			sRemaining.setText("السور المتبقية: "+(Data.sowar.size()-index.size()));
			if(index.size()>0) {
				sb= new StringBuffer(label[index.get(index.size()-1)].getText());
				todayS.setText("سورة اليوم: "+sb.deleteCharAt(sb.length()-1));
			}
			else
				todayS.setText("سورة اليوم: غير محدد");
			}
		progress.setValue(100*index.size()/Data.sowar.size());
		saveProgress();
	}

	private void saveProgress() {
		try {
			FileWriter savedF = new FileWriter("saved.txt");
			for(int i=0;i<index.size();i++)
				savedF.write(index.get(i)+"\n");
			savedF.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	private void clearBtn() {
		while(index.size()>0) {
			StringBuffer sb= new StringBuffer(label[index.get(index.size()-1)].getText());
			label[index.get(index.size()-1)].setBackground(Main.darkC);
			label[index.get(index.size()-1)].setBorder(Main.lightB);
			label[index.get(index.size()-1)].setText(sb.deleteCharAt(sb.length()-1)+"");
			index.remove(index.size()-1);
			sFinished.setText("السور المنجزة: "+index.size());
			sRemaining.setText("السور المتبقية: "+(Data.sowar.size()-index.size()));
			todayS.setText("سورة اليوم: غير محدد");
			}
		progress.setValue(100*index.size()/Data.sowar.size());
		try {
			FileWriter savedF = new FileWriter("saved.txt");
			savedF.nullWriter();
			savedF.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void btnStyle(JButton btn) {
		btn.setHorizontalAlignment(0);
		btn.setBackground(Main.darkC);
		btn.setFocusable(false);
		btn.setForeground(Main.textC);
		btn.setFont(Main.myFont);
		btn.setBorder(Main.whiteB);
	}
}