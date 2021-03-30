package pro2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pro2.moduls.DataFile;
import pro2.moduls.FilterData;
import pro2.moduls.LoadFile;
import pro2.moduls.ReadFile;

public class Main extends JFrame 
	implements ActionListener {

	static JButton go;
	static JLabel labelFolder;
	static JLabel labelCountFile;
	static JLabel labelCountFileRead;
	static JLabel labelStatus;
	static int CountReadyFiles = 0;
	
	public Main() {
		go = new JButton("SELECT PATH");
	    go.addActionListener((ActionListener) this);
	    add(go);
	    
	    labelFolder = new JLabel("Folder path: ");
	    add(labelFolder);
	    
	    labelCountFile = new JLabel("Count file: 0");
	    add(labelCountFile);
	    
	    labelCountFileRead = new JLabel("Number of uploaded files: 0");
	    add(labelCountFileRead);
	    
	    labelStatus = new JLabel("Status: choose correct path to files");
	    add(labelStatus);
	}
	
	public void actionPerformed(ActionEvent e) {
		LoadFile loadFile = new LoadFile();
		labelFolder.setText("Folder path: " + loadFile.getFolder());
		labelCountFile.setText("Count file: " + loadFile.getCountFile());
		CountReadyFiles = 0;
		if(loadFile.getCountFile() > 0) {
			labelStatus.setText("Status: wait. Uploading files is in progress");
			ArrayList <ReadFile> readFile = new ArrayList<ReadFile>();
			for(int i = 0; i < loadFile.getCountFile(); i++) {
				readFile.add(new ReadFile(loadFile.getPathFile(i), String.valueOf(i)));
				readFile.get(i).start();
			}
			
		 	for(int i = 0; i < loadFile.getCountFile(); i++)
		 	{
		 		try {	
		 			readFile.get(i).join();
		 			CountReadyFiles++;
		 			labelCountFileRead.setText("Number of uploaded files: " + CountReadyFiles);
		 			labelStatus.setText("Status: wait. The file is being processed");
		 			
		 		}
		 		catch(InterruptedException  er)
				{
					 System.out.println("has been interrupted");
				}
		 	}
			System.out.println("finish all");
			labelStatus.setText("Status: wait. The file is being processed");
			
			ArrayList <FilterData> filterData = new ArrayList<FilterData>();
			
			for(int i = 0; i < readFile.size(); i++)
			{
				filterData.add(new FilterData(readFile.get(i).GetListDataFile(), loadFile.getPathFile(i).toString(), loadFile.getFolder()));
				filterData.get(i).start();
			}
			for(int i = 0; i < filterData.size(); i++)
		 	{
		 		try {
		 			filterData.get(i).join();
		 			filterData.get(i).createExcelFile();
		 		}
		 		catch(InterruptedException  er)
				{
					 System.out.println("has been interrupted");
				}
		 	}
			labelStatus.setText("Status: finish");
		}
		else {
			JFrame frame = new JFrame("");
			JOptionPane.showMessageDialog(frame,
				    "No CSV files have been found.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Dimension getPreferredSize(){
	    return new Dimension(350, 200);
    }
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("");
		Main panel = new Main();
	    frame.addWindowListener(
	      new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          System.exit(0);
	          }
	        }
	      );
	    
	    JPanel panels = new JPanel();
        panels.setLayout(new GridLayout(6, 2, 2, 2));
	    panels.add(labelFolder);
	    panels.add(labelCountFile);
	    panels.add(labelCountFileRead);
	    panels.add(labelStatus);
	    frame.getContentPane().add(go, BorderLayout.PAGE_END);
	    frame.getContentPane().add(panels);
	    frame.setSize(panel.getPreferredSize());
	    frame.setVisible(true);
	}
}