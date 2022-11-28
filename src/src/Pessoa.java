package src;

import java.util.Random;

public class Pessoa extends Thread{
	private String nome;
	private String sexo;
	private boolean gender;
	private Banheiro banheiro;
	private int tempoNoBanheiro;
	
	public Pessoa(String nome, boolean gender, Banheiro banheiro) {
		
		this.nome=nome;
		this.gender=gender;
		this.banheiro = banheiro;
		this.tempoNoBanheiro = new Random().nextInt(7)+1;
		this.sexo = gender ? "Homem":"Mulher";
	}
	
	@Override
	public void run() {
		this.banheiro.entrarBanheiro(this);
		System.out.println(this.info()+" entrou no banheiro, e ficará por "+this.tempoNoBanheiro+" segundos;");
		try {
			Thread.sleep(tempoNoBanheiro*1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		this.banheiro.sairBanheiro(this);
		System.out.println(this.info()+" saiu do banheiro!");
	}
	
	public String info() {
		return this.nome+": "+this.sexo;
	}
	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Banheiro getBanheiro() {
		return banheiro;
	}

	public void setBanheiro(Banheiro banheiro) {
		this.banheiro = banheiro;
	}

	public int getTempoNoBanheiro() {
		return tempoNoBanheiro;
	}

	public void setTempoNoBanheiro(int tempoNoBanheiro) {
		this.tempoNoBanheiro = tempoNoBanheiro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
