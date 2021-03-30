package pro2.moduls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class FilterData extends Thread {

	ArrayList <DataFile> dataFile = new ArrayList<DataFile>();
	ArrayList <Facultets> dataFacultet = new ArrayList<Facultets>();
	ArrayList <DataFile> test = new ArrayList<DataFile>();
	
	HashMap<String, Facultets> listFacultets = new HashMap<>();
	String pathName = "";
	String pathFolder = "";
	
	public FilterData(ArrayList <DataFile> tests, String pathName, String pathFolder) {
		this.pathName = pathName;
		this.pathFolder = pathFolder;
		System.out.println(pathFolder);
		
		for(int i = 0; i < tests.size(); i++) {
			test.add(tests.get(i));
		}
	}
	
	public void run()
	{
		HashMap<String, String> listChair = new HashMap<>();
		HashMap<String, String> listGroup = new HashMap<>();
		HashMap<String, String> listNamePred = new HashMap<>();
		
		for(int i = 1; i < test.size(); i++) {
			System.out.println(test.get(i).Group);
			String temp = test.get(i).Group.trim().replaceAll("\"", "");
			int index_char = 0;
			for(int j = 0; j < temp.length(); j++)
				if(Character.isLetter(temp.charAt(index_char))) 
					index_char++;
				else 
					break;
			if(temp.charAt(index_char) == '0') temp = test.get(i).NumberChair;
			listFacultets.put(String.valueOf(temp.charAt(index_char)), new Facultets(String.valueOf(temp.charAt(index_char))));
			listChair.put(test.get(i).NumberChair, test.get(i).NumberChair);
			listGroup.put(test.get(i).Group, test.get(i).NumberChair);
			listNamePred.put(test.get(i).DISCIPLINE, test.get(i).DISCIPLINE);
		} 
		
		System.out.println(listFacultets);
		System.out.println(listChair);
		System.out.println(listGroup);
		System.out.println(listNamePred);
	
		for(Map.Entry<String, Facultets> entry : listFacultets.entrySet()) {
			String key = entry.getKey();
			Facultets facultets = entry.getValue();
		    
			for(Map.Entry<String, String> entryChair : listChair.entrySet()) {
			    String keysChair = entryChair.getKey();
			    String valuesChair = entryChair.getValue();
			    
			    String object = String.valueOf(valuesChair.charAt(0));
			    if(object.equals(facultets.NameFacultets)) {
			    	facultets.AddChair(valuesChair);
			    	for(Map.Entry<String, String> entryGroup : listNamePred.entrySet()) {
			    		String keysNamePred = entryGroup.getKey();
					    String valuesNamePred = entryGroup.getValue();
					    
					    for(int i = 1; i < test.size(); i++) {
					    	if(valuesNamePred.equals(test.get(i).DISCIPLINE) && valuesChair.equals(test.get(i).NumberChair) && key.equals(object)) {
					    		if(facultets.chairs.get(facultets.chairs.size()-1).lesson.get(valuesNamePred) == null)
					    		{
						    		facultets.chairs.get(facultets.chairs.size()-1).AddLesson(valuesNamePred);
						    		facultets.chairs.get(facultets.chairs.size()-1).lesson.get(valuesNamePred).AddGroup(test.get(i).Group); 
						    		facultets.chairs.get(facultets.chairs.size()-1).lesson.get(valuesNamePred).groups.get(test.get(i).Group).AddStudent(test.get(i).FIOStudent, test.get(i));
						    		   
					    		}
					    		else
					    		{
					    			if(facultets.chairs.get(facultets.chairs.size()-1).lesson.get(valuesNamePred).groups.get(test.get(i).Group) == null)
					    			{
					    				facultets.chairs.get(facultets.chairs.size()-1).lesson.get(valuesNamePred).AddGroup(test.get(i).Group);
					    			}
					    			facultets.chairs.get(facultets.chairs.size()-1).lesson.get(valuesNamePred).groups.get(test.get(i).Group).AddStudent(test.get(i).FIOStudent, test.get(i));
					    		}
					    	}
					    }
			    	}
			    }
			}
		}
		System.out.println();
	}
	
	public boolean createExcelFile()
	{
		boolean result = true;
		for(Map.Entry<String, Facultets> entry : listFacultets.entrySet()) {
			String key = entry.getKey();
			Facultets facultets = entry.getValue();
			File dirFacultets = new File(pathFolder + "\\Faculty " + key);
			
			if (!dirFacultets.exists()) {
				try {
					dirFacultets.mkdir();
				}
				catch(SecurityException se){
					return false;
				}
				if(!result) return false;
				for(int i = 0; i < facultets.chairs.size(); i++) {
					File dirChair = new File(pathFolder + "\\Faculty " + key + "\\Chair " + facultets.chairs.get(i).NameChair);
					
					if (!dirChair.exists()) {
						try {
							dirChair.mkdir();
						}
						catch(SecurityException se){
							return false;
						}
						if(!result) return false;
						for(Map.Entry<String, Lesson> entry1 : facultets.chairs.get(i).lesson.entrySet()) {
							String key1 = entry1.getKey();
							Lesson lessons = entry1.getValue();
							String nameLessons = lessons.NameLesson.replaceAll(":", "");
							/*StringBuilder temp = new StringBuilder(nameLessons);
							
							for(int z = temp.length() - 1 ; z >= 0; z--){
							     if(temp.charAt(z) == ' '){
							          temp.deleteCharAt(z);
							     }else{
							          break;
							     }
							}*/
							
							nameLessons = nameLessons.replaceAll("\\s+$", "");
							nameLessons = nameLessons.replaceAll("/","");
							nameLessons = nameLessons.replaceAll("\\\\","");
							
							File dirLesson = new File(pathFolder + "\\Faculty " + key + "\\Chair " + facultets.chairs.get(i).NameChair + "\\" + nameLessons);
							
							if (!dirLesson.exists()) {
								try {
									dirLesson.mkdir();
								}
								catch(SecurityException se){
									return false;
								}
								if(!result) return false;
								for(Map.Entry<String, Group> entry2 : lessons.groups.entrySet()) {
									String key2 = entry2.getKey();
									Group group = entry2.getValue();
									group.NameGroup = group.NameGroup.replaceAll("/", " ");
									
									File dirGroup = new File(pathFolder + "\\Faculty " + key + "\\Chair " + facultets.chairs.get(i).NameChair + "\\" + nameLessons + "\\" + group.NameGroup);
									if (!dirGroup.exists()) {
										try {
											dirGroup.mkdir();
										}
										catch(SecurityException se){
											return false;
										}
										if(!result) return false;
									}
									HashMap <String, String> listStudent = new HashMap<>();
									for(int z = 0; z < group.students.size(); z++) {
										listStudent.put(group.students.get(z).FIOStudent, group.students.get(z).FIOStudent);
									}
									
									for(Map.Entry<String, String> entry3 : listStudent.entrySet()) {
										String key3 = entry3.getKey();
										String vvv3 = entry3.getValue();
										ArrayList <DataFile> dFile = new ArrayList<DataFile>();
										for(int z = 0; z < group.students.size(); z++) {
											if(key3.equals(group.students.get(z).FIOStudent))
												dFile.add(group.students.get(z).dataStudent);
										}
										createExcelFileInPath(dFile, pathFolder + "\\Faculty " + key + "\\Chair " + facultets.chairs.get(i).NameChair + "\\" + nameLessons + "\\" + group.NameGroup);
									}
									
									
								}
							}
						}
					}
				}
			}
			
		}
		return result;
	}
	
	private void createExcelFileInPath(ArrayList<DataFile> dataList, String pathFolder)
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Просто лист");
		
		int rowNum = 0;
		
		Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Група");
        row.createCell(1).setCellValue("ПІБ студента");
        row.createCell(2).setCellValue("ПІБ викладача");
        row.createCell(3).setCellValue("DISCIPLINE");
        
        for (DataFile dataModel : dataList) {
            createSheetHeader(sheet, ++rowNum, dataModel);
            //System.out.println(dataModel.FIOStudent);
        }
        
        dataList.get(0).Group = dataList.get(0).Group.replaceAll("/", " ");
        
        try (FileOutputStream out = new FileOutputStream(new File(pathFolder + "\\" + dataList.get(0).FIOStudent + " " + dataList.get(0).Group + ".xls"))) {
        	Path path = Paths.get(pathFolder);
        	if (!Files.exists(path)) {
        		File dir = new File(pathFolder);
				if (!dir.exists()) {
					try {
						dir.mkdir();
					}
					catch(SecurityException se){
						
					}
				}
        	}
        	workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dataList.get(0).FIOStudent + " " + dataList.get(0).Group + ".xls" + " файл успешно создан!");
        
	}
	
	private static void createSheetHeader(HSSFSheet sheet, int rowNum, DataFile dataModel) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(dataModel.Group);
        row.createCell(1).setCellValue(dataModel.FIOStudent);
        row.createCell(2).setCellValue(dataModel.FIOTeacher);
        row.createCell(3).setCellValue(dataModel.DISCIPLINE);
        row.createCell(4).setCellValue(dataModel.LESSONTYPE);
        row.createCell(5).setCellValue(dataModel.MARK); 
        row.createCell(6).setCellValue(dataModel.ATTENDANCE);
        row.createCell(7).setCellValue(dataModel.NumberLesson);
        row.createCell(8).setCellValue(dataModel.NameLesson);
        row.createCell(9).setCellValue(dataModel.NumberChair);
        row.createCell(10).setCellValue(dataModel.CheatChair);
        row.createCell(11).setCellValue(dataModel.DateInput);
        row.createCell(12).setCellValue(dataModel.DateAppraisal);
		row.createCell(13).setCellValue(dataModel.DISCIPLINEID);
		row.createCell(14).setCellValue(dataModel.IDEMPLOYEE);
		row.createCell(15).setCellValue(dataModel.IDPERSON);
		row.createCell(16).setCellValue(dataModel.IDDEPARTMENT);
		row.createCell(17).setCellValue(dataModel.IDSTUDENT);
		row.createCell(18).setCellValue(dataModel.ID);
		row.createCell(19).setCellValue(dataModel.IDLAB);
		row.createCell(20).setCellValue(dataModel.UserName);
    }

	
	public void debugConsoleShow()
	{
		for(Map.Entry<String, Facultets> entry : listFacultets.entrySet()) {
			String key = entry.getKey();
			Facultets facultets = entry.getValue();
		    
			System.out.println("Facultet: " + facultets.NameFacultets);
			
		    for(int i = 0; i < facultets.chairs.size(); i++) {
		    	System.out.println("Chair: " + facultets.chairs.get(i).NameChair);
		    	
		    	for(Map.Entry<String, Lesson> vGet : facultets.chairs.get(i).lesson.entrySet()) {
		    		String keysNamePred = vGet.getKey();
		    		Lesson valuesNamePred = vGet.getValue();
		    		
		    		System.out.println("NameLesson: " + keysNamePred);
		    		
		    		for(Map.Entry<String, Group> sGet : facultets.chairs.get(i).lesson.get(keysNamePred).groups.entrySet()) {
		    			String keysGroup = sGet.getKey();
			    		Group valuesGroup = sGet.getValue();
		    			System.out.println("Group: " + keysGroup + ". Size: " + facultets.chairs.get(i).lesson.get(keysNamePred).groups.get(keysGroup).students.size());		    			
		    		}
		    	}
		    	
		    	System.out.println();
		    }
		}
		System.out.println();
	}

}

/* Факультет -> Кафедра -> Предметы -> Группы -> FIO */

class Facultets {
	public ArrayList<Chair> chairs = new ArrayList<Chair>();
	String NameFacultets = "";
	private String NumberFacultets = "";
	
	public Facultets(String name) {
		NameFacultets = name;
	}
	
	public void AddChair(String nameChar) {
		chairs.add(new Chair(nameChar));
	}
	
}

class Chair {
	public HashMap<String, Lesson> lesson = new HashMap<>(); // Название факультета | Группы
	String NameChair = "";
	private String NumberChair = "";
	
	public Chair(String name) {
		NameChair = name;
	}
	
	public void AddLesson(String name) {
		lesson.put(name, new Lesson(name));
	}
}

class Lesson {
	public HashMap<String, Group> groups = new HashMap<>();
	String NameLesson = "";
	private String NumberLesson = "";
	
	public Lesson(String name) {
		NameLesson = name;
	}
	
	public void AddGroup(String name) {
		groups.put(name, new Group(name));
	}
}


class Group 
{
	public ArrayList<Student> students = new ArrayList<Student>();
	public String NameGroup = "";
	
	public Group(String name) {
		NameGroup = name;
	}
	
	public void AddStudent(String name, DataFile temp) {
		students.add(new Student(name, temp));
	}
}

class Student
{
	String FIOStudent = "";
	DataFile dataStudent;
	
	public Student(String name, DataFile temp)
	{
		FIOStudent = name;
		dataStudent = new DataFile(temp.Group, temp.FIOStudent, temp.FIOTeacher, temp.DISCIPLINE, 
				temp.LESSONTYPE, temp.MARK, temp.ATTENDANCE, temp.NumberLesson, temp.NameLesson, temp.NumberChair,
				temp.CheatChair, temp.DateInput, temp.DateAppraisal, temp.DISCIPLINEID, temp.IDEMPLOYEE, temp.IDPERSON,
				temp.IDDEPARTMENT, temp.IDSTUDENT, temp.ID, temp.IDLAB, temp.UserName);
	}
}