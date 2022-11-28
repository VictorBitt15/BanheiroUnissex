package src;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banheiro {

	private int vagasMax;
	private List<Pessoa> pessoasUsando;
	private String sexoAtual= "none";
	private Condition sameSex;
	private Condition isFull;
	private Condition isEmpty;
	private Lock atual;

	public Banheiro(int vagas) {
		this.vagasMax = vagas;
		this.pessoasUsando = new ArrayList<>();
		this.atual = new ReentrantLock(true);
		this.sameSex = atual.newCondition();
		this.isFull = atual.newCondition();
		this.isEmpty = atual.newCondition();
	}
	
	public Condition getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(Condition isEmpty) {
		this.isEmpty = isEmpty;
	}
	/*
	 * Verifica a primeira interação, para saber se um sexo já foi identificado para usar o banheiro,
	 * se ainda nenhum sexo foi identificado o fluxo segue normal e será identificado após o segundo while.
	 */
	public boolean verifySexoBanheiro() {
		return !this.getSexoAtual().contains("none");
	}
	/*
	 * Neste etapa se faz um lock para entrar no banheiro, o primeiro WHILE é para verificar se já tem algum sexo estabelecido 
	 * para o banheiro e se a proxima pessoa que entrar é do mesmo sexo.
	 * 
	 * Já no segundo WHILE verifica se as vagas maximas dentro do banheiro já chegaram no limite.
	 * 
	 * Após isso a pessoa é adicionada no banheiro e da o UNLOCK para a próxima pessoa tentar entrar.
	 */
	public void entrarBanheiro(Pessoa person) {
		atual.lock();
		try {
			while( this.verifySexoBanheiro() && !this.sexoAtual.equals(person.getSexo())) {
				System.out.println(person.getNome()+": "+person.getSexo()+" tentou entrar no banheiro.");
				System.out.println("Esperando as pessoas do mesmo sexo acabarem. \n");
				sameSex.await();
			}
			while(this.vagasMax == pessoasUsando.size()) {
				System.out.println("O banheiro está cheio!");
				System.out.println(person.getNome()+": "+person.getSexo()+" está na fila esperando alguém sair!.\n");
				isFull.await();
				
			}
			pessoasUsando.add(person);
			if(pessoasUsando.size() == 1) {
				this.sexoAtual = person.getSexo();
			}
			isEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			atual.unlock();
		}
	}
	/*
	 * Este método faz o ação da pessoa sair do banheiro, primeiro se dá um Lock para ter exclusividade da região crítica
	 * 
	 * Faço duas verificações, se estão cheio e sinalizar as pessoas do mesmo sexo que podem entrar e 
	 * se está vazio e pode sinalizar ao outro sexo que pode entrar no banheiro.
	 */
	public void sairBanheiro(Pessoa person) {
		atual.lock();
		try {
			this.pessoasUsando.remove(person);
			if(this.pessoasUsando.size() == vagasMax-1) {
				isFull.signalAll();
			}
			
			if(this.pessoasUsando.isEmpty()) {
				this.setSexoAtual("none");
				this.sameSex.signalAll();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			atual.unlock();
		}
	}
	public String getSexoAtual() {
		return sexoAtual;
	}

	public void setSexoAtual(String sexoAtual) {
		this.sexoAtual = sexoAtual;
	}

	
	public int getVagasMax() {
		return vagasMax;
	}
	public void setVagasMax(int vagasMax) {
		this.vagasMax = vagasMax;
	}
	public List<Pessoa> getPessoasUsando() {
		return pessoasUsando;
	}
	public void setPessoasUsando(List<Pessoa> pessoas) {
		this.pessoasUsando = pessoas;
	}
	public Condition getSameSex() {
		return sameSex;
	}
	public void setSameSex(Condition sameSex) {
		this.sameSex = sameSex;
	}
	public Condition getIsFull() {
		return isFull;
	}
	public void setIsFull(Condition isFull) {
		this.isFull = isFull;
	}
	public Lock getAtual() {
		return atual;
	}
	public void setAtual(Lock atual) {
		this.atual = atual;
	}
	
	
	
	
}
