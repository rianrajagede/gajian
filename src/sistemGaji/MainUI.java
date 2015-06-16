package sistemGaji;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MainUI { 	
	
	private static String loginState;
	private static Manager manajer;
	private static Employee employee;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
//////DEBUGGING        
//		System.out.println(Jenis.a);
		
		System.out.print("Login : ");
		
		try {
			loginState = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(Integer.parseInt(loginState)%2==0){
			manajer = new Manager(loginState);
			ManajerUI(manajer);
		}else{
			employee = new Employee(loginState);
			EmployeeUI(employee);
		}
		
		br.close();
	}
	
	private static void EmployeeUI(Employee employee) throws IOException {
		int pilihan;
		String idProyek, idProyekRequested;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Project> proyList = new ArrayList<Project>();
		
		System.out.println("Selamat datang "+employee.getName());
		System.out.println("Mau apa hari ini?");
		System.out.println("[1] Daftar Proyek");
		System.out.println("[2] Klaim Gaji");
		pilihan = Integer.parseInt(br.readLine());
		
		if(pilihan==1){
			BufferedReader brPL = new BufferedReader(new FileReader("ProyekList.txt"));
			
			brPL.readLine();
			while((idProyek = brPL.readLine()) != null){
				Project proyek = new Project(idProyek);
				if(proyek.getProgress()==0)
					proyList.add(proyek);
			}
			
			for(Project i: proyList){
				System.out.println();
				System.out.println("["+i.getId()+"]" + " " + i.getName());
				System.out.println(i.getDescription());
				for(Requirement j: i.getReqList()){
					System.out.println(j.getJenis()+" sebanyak "+j.getAmmount()+" minimal level "+j.getLevel());
				}
				System.out.println("Duration : "+i.getDurationOpen());
			}
			
			System.out.println();
			System.out.println("Pilih ID proyek yang mau diikuti : ");
			idProyekRequested = br.readLine();
			
			Project proyek = new Project(idProyekRequested);
			employee.ChooseProject(proyek);
		}
		if(pilihan==2){
			System.out.println("Gaji Anda saat ini : " + employee.getAmmountSalary());
			System.out.println("Masukkan jumlah nominal yang ingin Anda klaim : ");
			int nominal = Integer.parseInt(br.readLine());
			System.out.println("Masukkan nomor rekening tujuan : ");
			br.readLine();
			employee.ClaimSalary(nominal);
		}
		
	}

	public static void ManajerUI(Manager manajer) throws IOException{
		int pilihan, durasi, peoples, n ;
		String nama, deskripsi,startProyek, akhirProyek;
		ArrayList<Requirement> reqList= new ArrayList<Requirement>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String inputReq;
		String[] partReq;
		
		System.out.println("Selamat datang "+manajer.getName());
		System.out.println("Mau apa hari ini?");
		System.out.println("[1] Buat Proyek");
		System.out.println("[2] Tutup Proyek");
		System.out.println("[3] Konfirmasi Proyek");
		System.out.println("[0] Keluar");
		System.out.print("Pilih : ");
		
		
		pilihan = Integer.parseInt(br.readLine());
		
		if(pilihan==1){
			System.out.println("Nama Proyek : ");
			nama = br.readLine();
			
			System.out.println("Deskripsi Proyek : ");
			deskripsi = br.readLine();

			System.out.println("Durasi Open : ");
			durasi = Integer.parseInt(br.readLine());

			System.out.println("Waktu Mulai Pengerjaan [DDMMYYYY] : ");
			startProyek = br.readLine();
			
			System.out.println("Waktu Selesai Pengerjaan [DDMMYYYY] : ");
			akhirProyek = br.readLine();
			
			System.out.println("Jumlah anggota tim : ");
			peoples = Integer.parseInt(br.readLine());
			
			System.out.println("Tuliskan spesifikasi kebutuhan tim dengan format \"tipe(spasi)jumlah(spasi)levelMinimal(spasi)gaji(spasi)exp\" : ");
			System.out.println("Tipe [programmer, designer, analyst, tester]");
			
			n=peoples;
			while(n>0){
				System.out.print("> ");
				inputReq = br.readLine();
				partReq = inputReq.split(" ");
				Requirement requirement = new Requirement(partReq[0], Integer.parseInt(partReq[1]), Integer.parseInt(partReq[2]),Integer.parseInt(partReq[3]),Integer.parseInt(partReq[4]));
				reqList.add(requirement);
				n-=Integer.parseInt(partReq[1]);
			}
			
			Project project = new Project(nama, deskripsi, durasi, startProyek, akhirProyek, peoples, reqList, manajer);
		}
		
		if(pilihan==2){
			System.out.println("Id proyek yang ingin dihapus : ");
			String id = br.readLine();
			
			System.out.println("Apakah proyek berhasil? (y/n)");
			String in = br.readLine();
			if(in.equals("y")){
				Project proyek = new Project(id);
				
				BufferedReader brMember = new BufferedReader(new FileReader("ProyekMember"+id+".txt"));
				String idMember;
				while((idMember=brMember.readLine())!=null){
						Employee employee = new Employee(idMember);
						employee.levelCalculation(proyek);
				}
				
				proyek.deleteProyek();
			}else{
				Project proyek = new Project(id);
				proyek.deleteProyek();
			}
		}
		
		if(pilihan==3){
			System.out.println("id proyek yang ingin dikonfirmasi : ");
			String id = br.readLine();
			
			Project project = new Project(id);
			project.printMemberRequest();
			
			System.out.println("Tentukan tim ? (y/n) ");
			String in = br.readLine();
			
			if(in.equals("y")){
				ArrayList<String> idMemberAccepted = new ArrayList<String>();
				System.out.println("Ketikkan nomor id member yang diterima (dipisah dengan enter) : ");
				
				for(int i=0;i<project.getTotalMember();i++){
					idMemberAccepted.add(br.readLine());
				}
				
				project.konfirmRequest(idMemberAccepted);
				project.setProgress(1);
				project.saveProyek(false);
			}else{
				System.out.println("Proyek diteruskan ? (y/n)");
				in = br.readLine();
				if(in.equals("y")){
					System.out.println("Durasi tambahan : ");
					in = br.readLine();
					project.updateProyek(in);
				}else{
					project.deleteProyek();
				}
			}
		}
	}
}
