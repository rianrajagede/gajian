package sistemGaji;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class Project {
	private String id;
	private String name;
	private String description;
	private int progress;
	private int durationOpen;
	private String startProject;
	private String endProject;
	private ArrayList<Requirement> reqList;
	
	public Project(){
		
	}

	public void createProject(String nama, String deskripsi, int durasi,
			String startProyek, String akhirProyek, int peoples,
			ArrayList<Requirement> reqList) {
		setName(nama);
		setDescription(deskripsi);
		setDurationOpen(durasi);
		setReqList(reqList);
		setStartProject(startProyek);
		setEndProject(akhirProyek);
		saveProyek();
	}
	
	private void saveProyek(){
		try {
			PrintWriter outp = new PrintWriter(new FileOutputStream("ProyekList.txt", true));
			outp.println(getName()+"\n"+getDescription()+"\n"+getDurationOpen()+" "+getStartProject()+" "+getEndProject());
			outp.println(reqList.size());
			for(int i=0;i<reqList.size();i++){
				outp.println(reqList.get(i).getJenis()+" "+reqList.get(i).getAmmount()+" "+reqList.get(i).getSalary()+" "+reqList.get(i).getLevel());
			}
			outp.println();
			outp.close();
			System.out.println("Project Saved!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
