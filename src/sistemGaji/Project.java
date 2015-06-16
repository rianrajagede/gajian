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

public class Project {
	private String id;
	private String name;
	private String description;
	private int progress, totalMember;
	private int durationOpen, totalProyek;
	private String[] allActiveID;
	private String startProject;
	private String endProject;
	private ArrayList<Requirement> reqList;
	private ArrayList<String> allActiveMember, allActiveMemberType;
	private Manager manajer;
	
	
	public Project(){
		
	}
	

	public void createProject(String nama, String deskripsi, int durasi,
			String startProyek, String akhirProyek, int peoples,
			ArrayList<Requirement> reqList, Manager manajer)  throws IOException {
		setName(nama);
		setDescription(deskripsi);
		setDurationOpen(durasi);
		setReqList(reqList);
		setStartProject(startProyek);
		setEndProject(akhirProyek);
		setManajer(manajer);
		
		BufferedReader br = new BufferedReader(new FileReader("ProyekList.txt"));
		
		String tmp;
		while((tmp=br.readLine())!=null){
			totalProyek = Integer.parseInt(tmp);
		}
		
		totalProyek++;
		saveProyek(totalProyek, true);
		
		br.close();
	}
	
	private void saveProyek(int id, boolean isNew){
		
		try {
			PrintWriter outp = new PrintWriter(new FileOutputStream("DetailProyek"+id+".txt", false));
			outp.println(getName()+"\n"+getDescription()+"\n"+getDurationOpen()+"\n"+getStartProject()+"\n"+getEndProject());
			outp.println(getManager().getId());
			outp.println(reqList.size());
			for(int i=0;i<reqList.size();i++){
				outp.println(reqList.get(i).getJenis()+"\n"+reqList.get(i).getAmmount()+"\n"+reqList.get(i).getSalary()+"\n"+reqList.get(i).getLevel());
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
	
	public void deleteProyek(String id) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("ProyekList.txt"));
		
		String tmp, tmp2 = "";
		while((tmp=br.readLine())!=null){
			tmp2+=tmp+" ";
		}
		allActiveID=tmp2.split(" ");
		
		br.close();
		
		PrintWriter outp = new PrintWriter(new FileOutputStream("ProyekList.txt", false));
		for(String i : allActiveID){
			if(!i.equals(id))
				outp.println(i);
		}
		outp.close();
		
		System.out.println("Project deleted from board!");
	}
	
	public void getMemberRequest(String id) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("ProyekMember"+id+".txt"));
		allActiveMember = new ArrayList<String>();
		allActiveMemberType = new ArrayList<String>();
		
		String tmp;
		String[] tmp2;
		while((tmp=br.readLine())!=null){
			tmp2=tmp.split(" ");
			allActiveMember.add(tmp2[0]);
			allActiveMemberType.add(tmp2[1]);
		}
	}
	
	public void getProyekData(String id) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("DetailProyek"+id+".txt"));
		setName(br.readLine());
		setDescription(br.readLine());
		setDurationOpen(Integer.parseInt(br.readLine()));
		setStartProject(br.readLine());
		setEndProject(br.readLine());
		
		int n=Integer.parseInt(br.readLine());
		ArrayList<Requirement> reqList = new ArrayList<Requirement>();
		
		int total=0;
		for(int i=0;i<n;i++){
			Requirement requirement = new Requirement(br.readLine(),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
			reqList.add(requirement);
			total+=requirement.getAmmount();
		}
		setTotalMember(total);
		setReqList(reqList);
	}


	public void printMemberRequest(String id) throws IOException {
		System.out.println("Request member dari proyek nomor "+id);
		
		getMemberRequest(id);
		for(int i=0;i<allActiveMember.size();i++){
			System.out.println((i+1)+" "+allActiveMember.get(i)+" "+allActiveMemberType.get(i));
		}
		
		System.out.println("Requirement yang dibutuhkan : ");
		getProyekData(id);
		for(int i=0;i<this.reqList.size();i++){
			System.out.println(this.reqList.get(i).getJenis()+" sebanyak "+this.reqList.get(i).getAmmount());
		}System.out.println("Total = "+totalMember+" orang");
	}

	public void konfirmMember(String id, ArrayList<Integer> idMemberAccepted) throws IOException {
		getMemberRequest(id);
		PrintWriter outp = new PrintWriter(new FileOutputStream("ProyekMember"+id+".txt", false));
		
		System.out.println("Member Terpilih : ");
		
		for(int i=0;i<idMemberAccepted.size();i++){
			outp.println(allActiveMember.get(idMemberAccepted.get(i)-1)+" "+allActiveMemberType.get(idMemberAccepted.get(i)-1));
			System.out.println((i+1) + " " +allActiveMember.get(idMemberAccepted.get(i)-1)+" "+allActiveMemberType.get(idMemberAccepted.get(i)-1));
		}		
		
		outp.close();
	}

	public void updateProyek(String id, String duration) throws IOException {
		getProyekData(id);
		setDurationOpen(Integer.parseInt(duration));
		saveProyek(Integer.parseInt(id), false);
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
	
	public ArrayList<Requirement> getReqList(){
		return reqList;
	}
	
	public void setReqList(ArrayList<Requirement> reqList){
		this.reqList = reqList;
	}


	
	
	
}
