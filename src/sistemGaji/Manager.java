package sistemGaji;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Manager {
	
	private int id;
	private String name;
	private String phoneNum;
	
	public Manager(){
		
	}
	
	public Manager(int id) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("Manajer"+id+".txt"));
		setId(Integer.parseInt(br.readLine()));
		setName(br.readLine());
		setPhoneNum(br.readLine());
		br.close();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
