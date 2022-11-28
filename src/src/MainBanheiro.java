package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainBanheiro {

	public static void main(String[] args) {
		try {
			Integer.valueOf(args[0]);
		} catch (Exception e) {
			System.out.println("Valores inválidos, digite somente números!");
			System.out.println("Exemplo válido: 4 15");
			System.exit(0);
		}
		
		Random rdm = new Random();
		int qtdVagas = Integer.parseInt(args[0]);
		int qtdPessoas = Integer.parseInt(args[1]);
		Banheiro banheiro = new Banheiro(qtdVagas);
		List<Thread> persons = new ArrayList<>();
		for(int i=0;i< qtdPessoas; i++) {
			persons.add(new Pessoa("Pessoa "+i, rdm.nextBoolean(), banheiro));
		}
		for(Thread t: persons) {
			t.start();
		}

	}

}
