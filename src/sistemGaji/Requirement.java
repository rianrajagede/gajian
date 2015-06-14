package sistemGaji;

public class Requirement {
	private Jenis jenis;
	private int level;
	private int salary;
	private int ammount;
	
	public Requirement(){
		
	}
	
	public Requirement(String jenis, int jumlah, int gaji, int exp) {
		if(jenis.equals("programmer")) setJenis(Jenis.Programmer);
		if(jenis.equals("designer")) setJenis(Jenis.Designer);
		if(jenis.equals("analyst")) setJenis(Jenis.Analyst);
		if(jenis.equals("tester")) setJenis(Jenis.Tester);
		setAmmount(jumlah);
		setSalary(gaji);
		setLevel(exp);
	}


	public String getJenis() {
		if(this.jenis==Jenis.Programmer) return "programmer";
		else if(this.jenis==Jenis.Analyst) return "analyst";
		else if(this.jenis==Jenis.Designer) return "designer";
		else return "tester";
	}
	public void setJenis(Jenis jenis) {
		this.jenis = jenis;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getAmmount() {
		return ammount;
	}
	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}
	
	
}
