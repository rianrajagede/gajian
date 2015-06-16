package sistemGaji;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Employee {
	private String employeeID;
	private String name;
	private String address;
	private String phoneNumber;
	private Jenis job;
	private int level;
	private int exp;
	private int ammountSalary;
	private int totalProyek;
	private ArrayList<Project> proyList;
	
	public Employee(){
		proyList = new ArrayList<Project>();
	}
	
	public Employee(String id) throws IOException {
		this();
		BufferedReader br = new BufferedReader(new FileReader("Employee"+id+".txt"));
		this.setEmployeeID(br.readLine());
		this.setName(br.readLine());
		this.setAddress(br.readLine());
		this.setPhoneNumber(br.readLine());
		String jenis = br.readLine();
		if(jenis.equals("Programmer")) this.setJob(Jenis.Programmer);
		if(jenis.equals("Analyst")) this.setJob(Jenis.Analyst);
		if(jenis.equals("Designer")) this.setJob(Jenis.Designer);
		if(jenis.equals("Tester")) this.setJob(Jenis.Tester);
		this.setLevel(Integer.parseInt(br.readLine()));
		this.setExp(Integer.parseInt(br.readLine()));
		this.setAmmountSalary(Integer.parseInt(br.readLine()));
		this.setTotalProyek(Integer.parseInt(br.readLine()));
		for(int i=0;i<this.totalProyek;i++){
			Project p = new Project(br.readLine());
			proyList.add(p);
		}
	}
	
	// nerima proyek yg Accepted
	public void AddProject(Project proyek) throws IOException{
		this.proyList.add(proyek);
		SaveEmployee();
	}
	
	public void SaveEmployee() throws IOException{
		PrintWriter outp = new PrintWriter(new FileOutputStream("Employee"+this.employeeID+".txt", false));
		outp.println(this.employeeID);
		outp.println(this.name);
		outp.println(this.address);
		outp.println(this.phoneNumber);
		outp.println(this.job);
		outp.println(this.level);
		outp.println(this.exp);
		outp.println(this.ammountSalary);
		outp.println(this.proyList.size());
		for(int i=0;i < proyList.size();i++){
			outp.println(proyList.get(i).getId());
		}
		outp.close();
	}

	public void ChooseProject(Project proyek) throws IOException{
		if(proyek.getMinLevelOfJob().get(this.getJob()) <= this.getLevel()){
			proyek.AddEmployee(new Employee(this.employeeID));
		}else{
			System.out.println("Kamu masih cupu!");
		}
	}
	
	public void ClaimSalary(int nominal) throws IOException{
		if(nominal > this.ammountSalary){
			System.out.println("Ngimpi!");
		}else{
			this.ammountSalary-=nominal;
			SaveEmployee();
			System.out.println("Gaji berhasil ditransfer, gajimu saat ini : " + this.ammountSalary);
		}
	}
	
	public void LevelCalculation(){
		this.level += this.exp / 100;
		this.exp %= 100;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Jenis getJob() {
		return job;
	}

	public void setJob(Jenis job) {
		this.job = job;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getAmmountSalary() {
		return ammountSalary;
	}

	public void setAmmountSalary(int ammountSalary) {
		this.ammountSalary = ammountSalary;
	}

	public int getTotalProyek() {
		return totalProyek;
	}

	public void setTotalProyek(int totalProyek) {
		this.totalProyek = totalProyek;
	}

	public ArrayList<Project> getProyList() {
		return proyList;
	}

	public void setProyList(ArrayList<Project> proyList) {
		this.proyList = proyList;
	}

	public void updateFinish(Project proyek) throws IOException {
		this.ammountSalary += proyek.getSalaryOfJob().get(this.job);
		this.exp += proyek.getExpOfJob().get(this.job);
		SaveEmployee();
	}
	
	
}
