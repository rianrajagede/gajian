package sistemGaji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
//		Project p = new Project();
//		p.getProyekData("1");
//		System.out.println(p.getDescription());
//		System.out.println(p.getEndProject());
//		for(int i=0;i<p.getReqList().size();i++){
//			System.out.println(p.getReqList().get(i).getJenis());
//		}
		
		System.out.print("Login : ");
		
		try {
			loginState = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(Integer.parseInt(loginState)%2==0){
			manajer = new Manager(Integer.parseInt(loginState));
			ManajerUI(manajer);
		}else{
			System.out.println("1");
		}
		
		br.close();
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
			
			System.out.println("Tuliskan spesifikasi kebutuhan tim dengan format \"tipe(spasi)jumlah(spasi)gaji(spasi)exp\" : ");
			System.out.println("Tipe [programmer, designer, analyst, tester]");
			
			n=peoples;
			while(n>0){
				System.out.print("> ");
				inputReq = br.readLine();
				partReq = inputReq.split(" ");
				Requirement requirement = new Requirement(partReq[0], Integer.parseInt(partReq[1]), Integer.parseInt(partReq[2]),Integer.parseInt(partReq[3]));
				reqList.add(requirement);
				n-=Integer.parseInt(partReq[1]);
			}
			
			Project project = new Project();
			project.createProject(nama, deskripsi, durasi, startProyek, akhirProyek, peoples, reqList, manajer);
		}
		
		if(pilihan==2){
			System.out.println("Id proyek yang ingin dihapus : ");
			String id = br.readLine();
			
			System.out.println("Apakah proyek berhasil? (y/n)");
			String in = br.readLine();
			if(in.equals("y")){
				Employee employee = new Employee();
				employee.updateFinish(id);
				
				Project project = new Project();
				project.deleteProyek(id);
			}else{
				Project project = new Project();
				project.deleteProyek(id);
			}
		}
		
		if(pilihan==3){
			System.out.println("id proyek yang ingin dikonfirmasi : ");
			String id = br.readLine();
			
			Project project = new Project();
			project.printMemberRequest(id);
			
			System.out.println("Tentukan tim ? (y/n) ");
			String in = br.readLine();
			
			if(in.equals("y")){
				ArrayList<Integer> idMemberAccepted = new ArrayList<Integer>();
				System.out.println("Ketikkan nomor id member yang diterima (dipisah dengan enter) : ");
				
				for(int i=0;i<project.getTotalMember();i++){
					idMemberAccepted.add(Integer.parseInt(br.readLine()));
				}
				
				project.konfirmMember(id, idMemberAccepted);
				project.deleteProyek(id);
			}else{
				System.out.println("Proyek diteruskan ? (y/n)");
				in = br.readLine();
				if(in.equals("y")){
					System.out.println("Durasi tambahan : ");
					in = br.readLine();
					project.updateProyek(id,in);
				}else{
					project.deleteProyek(id);
				}
			}
		}
	}
}
