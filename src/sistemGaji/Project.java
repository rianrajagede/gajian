package sistemGaji;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
	private String id;
	private String name;
	private String description;
	private int progress;
	private int totalMember;
	private int durationOpen, totalProyek;
	private String[] allActiveID;
	private String startProject;
	private String endProject;
	private List<Requirement> reqList;
	private List<Employee> employListRQ;
	private Map<Jenis, Integer> salaryOfJob;
	private Map<Jenis, Integer> expOfJob;
	private Manager manajer;
	
	
	public Project(){
		employListRQ = new ArrayList<Employee>();
		salaryOfJob = new HashMap<Jenis, Integer>();
		expOfJob = new HashMap<Jenis, Integer>();
	}
	
	public Project(String id) throws IOException{
		this();
		setId(id);
		BufferedReader br = new BufferedReader(new FileReader("DetailProyek"+id+".txt"));
		setProgress(Integer.parseInt(br.readLine()));
		setName(br.readLine());
		setDescription(br.readLine());
		setDurationOpen(Integer.parseInt(br.readLine()));
		setStartProject(br.readLine());
		setEndProject(br.readLine());
		setManajer(new Manager(br.readLine()));
		int n=Integer.parseInt(br.readLine());
		ArrayList<Requirement> reqList = new ArrayList<Requirement>();
		
		int total=0;
		for(int i=0;i<n;i++){
			Requirement requirement = new Requirement(br.readLine(),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
			reqList.add(requirement);
			salaryOfJob.put(requirement.getJenis(),requirement.getSalary());
			expOfJob.put(requirement.getJenis(),requirement.getExp());
			total+=requirement.getAmmount();
		}
		setTotalMember(total);
		setReqList(reqList);
	}
	
	public void AddEmployee(Employee employee) throws IOException{
		PrintWriter outp = new PrintWriter(new FileOutputStream("ProyekMember"+this.id+".txt", true));
		outp.println(employee.getEmployeeID());
		System.out.println("Kamu telah terdaftar");
		outp.close();
	}

	public Project(String nama, String deskripsi, int durasi,
			String startProyek, String akhirProyek, int peoples,
			ArrayList<Requirement> reqList, Manager manajer)  throws IOException {
		setName(nama);
		setDescription(deskripsi);
		setDurationOpen(durasi);
		setReqList(reqList);
		setStartProject(startProyek);
		setEndProject(akhirProyek);
		setManajer(manajer);
		setProgress(0);
		BufferedReader br = new BufferedReader(new FileReader("ProyekList.txt"));
		
		totalProyek = Integer.parseInt(br.readLine());
		
		totalProyek++;
		setId(""+totalProyek);
		saveProyek(true);
		
		br.close();
	}
	
	public void saveProyek(boolean isNew){
		
		try {
			PrintWriter outp = new PrintWriter(new FileOutputStream("DetailProyek"+this.id+".txt", false));
			outp.println(getProgress());
			outp.println(getName()+"\n"+getDescription()+"\n"+getDurationOpen()+"\n"+getStartProject()+"\n"+getEndProject());
			outp.println(getManager().getId());
			outp.println(reqList.size());
			for(int i=0;i<reqList.size();i++){
				outp.println(reqList.get(i).getJenis()+"\n"+
						reqList.get(i).getAmmount()+"\n"+
						reqList.get(i).getLevel()+"\n"+
						reqList.get(i).getSalary()+"\n"+
						reqList.get(i).getExp()
						);
			}
			outp.close();
			
			if(isNew){
				outp = new PrintWriter(new FileOutputStream("ProyekList.txt", true));
				outp.println(totalProyek);
				outp.close();
			}
			
			System.out.println("Project saved!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteProyek() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("ProyekList.txt"));
		
		String tmp, tmp2 = "";
		int totalNow = Integer.parseInt(br.readLine());
		while((tmp=br.readLine())!=null){
			tmp2+=tmp+" ";
		}
		allActiveID=tmp2.split(" ");
		
		br.close();
		
		PrintWriter outp = new PrintWriter(new FileOutputStream("ProyekList.txt", false));
		outp.println(totalNow-1);
		for(String i : allActiveID){
			if(!i.equals(this.id))
				outp.println(i);
		}
		outp.close();
		
		System.out.println("Project deleted from board!");
	}
	
	public void getMemberRequest(String id) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("ProyekMember"+id+".txt"));
		
		String tmp;
		while((tmp=br.readLine())!=null){
			employListRQ.add(new Employee(tmp));
		}
	}

	public void printMemberRequest() throws IOException {
		System.out.println("Request member dari proyek nomor "+getId());
		
		getMemberRequest(getId());
		for(int i=0;i<employListRQ.size();i++){
			Employee e = employListRQ.get(i);
			System.out.println("["+e.getEmployeeID()+"] "+e.getName()+" "+e.getJob()+" "+e.getLevel());
		}
		
		System.out.println("Requirement yang dibutuhkan : ");
		
		for(int i=0;i<this.reqList.size();i++){
			System.out.println(this.reqList.get(i).getJenis()+" sebanyak "+this.reqList.get(i).getAmmount());
		}System.out.println("Total = "+totalMember+" orang");
	}

	/**
	 * @param idMemberAccepted
	 * @throws IOException
	 * 
	 * id-id yang diterima
	 */
	public void konfirmMember(ArrayList<String> idMemberAccepted) throws IOException {
		getMemberRequest(this.id);
		PrintWriter outp = new PrintWriter(new FileOutputStream("ProyekMember"+this.id+".txt", false));
		
		System.out.println("Member Terpilih : ");
		
		for(int i=0;i<idMemberAccepted.size();i++){
			Employee e  = new Employee(idMemberAccepted.get(i));
			outp.println(e.getEmployeeID());
			System.out.println("["+e.getEmployeeID()+"] "+e.getName());
			e.AddProject(new Project(this.id));
		}		
		
		outp.close();
	}

	public void updateProyek(String duration) throws IOException {
		setDurationOpen(Integer.parseInt(duration));
		saveProyek(false);
	}
	
	public void setManajer(Manager manajer){
		this.manajer = manajer;
	}
	
	public Manager getManager(){
		return this.manajer;
	}
	
	public void setTotalMember(int total) {
		this.totalMember = total;
	}
	
	public int getTotalMember(){
		return this.totalMember;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getDurationOpen() {
		return durationOpen;
	}

	public void setDurationOpen(int durationOpen) {
		this.durationOpen = durationOpen;
	}

	public String getStartProject() {
		return startProject;
	}

	public void setStartProject(String startProject) {
		this.startProject = startProject;
	}

	public String getEndProject() {
		return endProject;
	}

	public void setEndProject(String endProject) {
		this.endProject = endProject;
	}
	
	public List<Requirement> getReqList(){
		return reqList;
	}
	
	public void setReqList(ArrayList<Requirement> reqList){
		this.reqList = reqList;
	}

	public Map<Jenis, Integer> getSalaryOfJob() {
		return salaryOfJob;
	}

	public void setSalaryOfJob(HashMap<Jenis, Integer> salaryOfJob) {
		this.salaryOfJob = salaryOfJob;
	}

	public Map<Jenis, Integer> getExpOfJob() {
		return expOfJob;
	}

	public void setExpOfJob(HashMap<Jenis, Integer> expOfJob) {
		this.expOfJob = expOfJob;
	}


	
	
	
}
