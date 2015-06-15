package sistemGaji;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MainUI { 	
	
	private static String loginState;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// TODO Auto-generated method stub
		System.out.print("Login : ");
		
		try {
			loginState = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(loginState.equals("manajer")){
			ManajerUI();
		}else{
			System.out.println("1");
		}
	}
	
	public static void ManajerUI() throws IOException{
		int pilihan, durasi, peoples, n ;
		String nama, deskripsi,startProyek, akhirProyek;
		ArrayList<Requirement> reqList= new ArrayList<Requirement>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String inputReq;
		String[] partReq;
		
		System.out.println("Selamat datang Manajer!");
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
			project.createProject(nama, deskripsi, durasi, startProyek, akhirProyek, peoples, reqList);
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
			
		}
	}
}
