package pro2.moduls;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class LoadFile extends JPanel {

	private int countFile;
	private ArrayList<String> listFile = new ArrayList<String>(); 
	private String chooserTitle = "Выберите путь к папке с CSV файлами";
	private String chooserPath;
	
	public LoadFile() {
		ShowDialogFolder();
		LoadListFile();
	} 
	
	public String getFolder() {
		return chooserPath;
	}
	public int getCountFile() {
		return countFile;
	}
	public String getPathFile(int id)
	{
		return listFile.get(id);
	}
	
	private void ShowDialogFolder() {
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(chooserTitle);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	 
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	    	chooserPath = chooser.getSelectedFile().toString();
	    	/*System.out.println("getCurrentDirectory(): " 
	         +  chooser.getCurrentDirectory());
	    	System.out.println("getSelectedFile() : " 
	         +  chooser.getSelectedFile());*/
	      }
	    else {
	      System.out.println("No Selection ");
	    }
	}
	
	private void LoadListFile() {
		File dir = new File(chooserPath);
		File[] arrFiles = dir.listFiles();
		List<File> lst = Arrays.asList(arrFiles);
		if(lst.size() > 0) {
			listFile.clear();
			countFile = 0;
		}
		for(int i = 0; i < lst.size(); i++) {
			if(getFileExtension(lst.get(i)).equals("csv")) {
				countFile++;
				listFile.add(lst.get(i).toString());
				System.out.println(lst.get(i).toString());
			}
		}
	}
	
	private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)   
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
