package sistemGaji;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Manager {
	
	private String id;
	private String name;
	private String phoneNum;
	
	public Manager(){
		
	}
	
	public Manager(String string) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("Manajer"+string+".txt"));
		setId(br.readLine());
		setName(br.readLine());
		setPhoneNum(br.readLine());
		br.close();
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
