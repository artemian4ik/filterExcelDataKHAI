package pro2.moduls;

public class DataFile {

	String Group = ""; //�����						+ 0
	String FIOStudent = ""; //"ϲ� ��������"		+ 1
	String FIOTeacher = ""; //"ϲ� ���������"		+ 2
	String DISCIPLINE = ""; //"DISCIPLINE"			+ 3
	String LESSONTYPE = ""; //"LESSONTYPE"			+ 4
	String MARK = ""; //"MARK"						+ 5
	String ATTENDANCE = ""; //"ATTENDANCE" 			+ 6
	String NumberLesson = ""; //"�����_����"		+ 7
	String NameLesson = ""; // ����					+ 8
	String NumberChair = ""; //"�������"			+ 9
	String CheatChair = ""; // "���_�������"		+ 10
	String DateInput = ""; // ���� �������� 		+ 11
	String DateAppraisal = ""; // ����_������ 		+ 12
	String DISCIPLINEID = ""; // DISCIPLINEID 		+ 13
	String IDEMPLOYEE = ""; // IDEMPLOYEE			+ 14
	String IDPERSON = ""; // IDPERSON  
	String IDDEPARTMENT = ""; // IDDEPARTMENT
	String IDSTUDENT = ""; // IDSTUDENT
	String ID = ""; //"ID"
	String IDLAB = ""; // "IDLAB"
	String UserName = ""; // "USER_NAME"
	String IdAcadmicYear = ""; // "ID_ACADEMIC_YEAR"
	String IdTerm = ""; // "ID_TERM"
	
	public DataFile() {
		
	}
	
	public DataFile(String Group, String FioStudent, String FioTeacher, String DISCIPLINE, 
			String LESSONTYPE, String MARK, String ATTENDANCE, String NumberLesson, String NameLesson, String NumberChair,
			String CheatChair, String DateInput, String DateAppraisal, String DISCIPLINEID, String IDEMPLOYEE, String IDPERSON,
			String IDDEPARTMENT, String IDSTUDENT, String ID, String IDLAB, String UserName) {
		this.Group = Group;
		this.FIOStudent = FioStudent;
		this.FIOTeacher = FioTeacher;
		this.DISCIPLINE = DISCIPLINE;
		this.LESSONTYPE = LESSONTYPE;
		this.MARK = MARK;
		this.ATTENDANCE = ATTENDANCE;
		this.NumberLesson = NumberLesson;
		this.NameLesson = NameLesson;
		this.NumberChair = NumberChair;
		this.CheatChair = CheatChair;
		this.DateInput = DateInput;
		this.DateAppraisal = DateAppraisal;
		this.DISCIPLINEID = DISCIPLINEID;
		this.IDEMPLOYEE = IDEMPLOYEE;
		this.IDPERSON = IDPERSON;
		this.IDDEPARTMENT = IDDEPARTMENT;
		this.IDSTUDENT = IDSTUDENT;
		this.ID = ID;
		this.IDLAB = IDLAB;
		this.UserName = UserName;
		
	}

}
