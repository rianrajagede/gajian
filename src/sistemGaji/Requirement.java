package sistemGaji;

public class Requirement {
	private Jenis jenis;
	private int level;
	private int salary;
	private int ammount;
	private int exp;
	
	public Requirement(){
		
	}
	
	public Requirement(String jenis, int jumlah, int level, int gaji, int exp) {
		if(jenis.equals("programmer")) setJenis(Jenis.Programmer);
		if(jenis.equals("designer")) setJenis(Jenis.Designer);
		if(jenis.equals("analyst")) setJenis(Jenis.Analyst);
		if(jenis.equals("tester")) setJenis(Jenis.Tester);
		setAmmount(jumlah);
		setSalary(gaji);
		setLevel(level);
		setExp(exp);
	}


	public Jenis getJenis() {
		return this.jenis;
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	
	
}
