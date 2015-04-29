package br.uefs.apriori;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class AlgoritmoApriori {
	
	private double suporte = 0.3;
	private double confianca = 0.5;
	String arquivoTransacoes = "transacoes.txt";
	private int quantTransacoes;
	private ArrayList<String> transacoes; //guarda as transa��es. cada item do array � uma linda da transa��o. para pegar os itens separadamente � necess�rio quebrar a string
	private HashMap<Integer, ArrayList<String>> itemset = new HashMap<Integer, ArrayList<String>>(); //conjuntos poss�veis
	private HashMap<Integer, ArrayList<String>> frequentItemset = new HashMap<Integer, ArrayList<String>>(); //conjuntos com o suporte m�nimo
	
	
	//Seleciona os itens contidos nas transa��es (itemset 1) ok
	
	/*Gera itemset - conjuntos poss�veis gerados a partir de um frequent itemset anterior
	 *  => Se o n�mero do conjunto for 1, o itemset s�o todos os items presentes nas transa��es ok
	 */
	
	
	//Gera frequentItemset - seleciona os conjuntos que possuem o suporte m�nimo
	
	//Gera as regras para um itemset frequente e calcular sua confian�a
	
	public AlgoritmoApriori(){
		
		geraPrimeiroItemset(); //Gera o conjunto C1
		//exibeItemset(this.itemset);
		//System.out.println("Quantidade de Transa��es: " + quantTransacoes);
		geraItemsetFrequente(this.itemset.get(1), 1); //Gera o conjunto L1
		geraItemset(this.frequentItemset.get(1), 1); //Gera o conjunto C2
		geraItemsetFrequente(this.itemset.get(2), 2); //Gera o conjunto L2
		geraItemset(this.frequentItemset.get(2), 2); //Gera o conjunto C3

	}
	
	//Gera o conjunto C1 a partir do arquivo .txt
	private void geraPrimeiroItemset(){
	
		System.out.println("=> Gerando itemsets de n�vel 1");
		
		try {
			BufferedReader dados = new BufferedReader(new FileReader(arquivoTransacoes));
			List<String> itensTransacao = new ArrayList<String>();
			this.transacoes = new ArrayList<String>();
			while (dados.ready()) { 
				String linha = dados.readLine();
				
				if (linha.matches("\\s*")){
					continue; 
				}
		
				this.transacoes.add(linha);
				quantTransacoes++;
				StringTokenizer token = new StringTokenizer(linha, " ");

				while (token.hasMoreTokens()) {
					
					String item = new String(token.nextToken());
					//this.items.add(item);
					
					if(!itensTransacao.contains(item)){
						itensTransacao.add(item);
						//System.out.println("C1: " + item);
						System.out.println("{" + item + "}");
					}
					
				}
				
			}
			for (String string : itensTransacao) {
				this.itemset.put(1, (ArrayList<String>) itensTransacao);
			}
		} catch (FileNotFoundException e) {
			
			System.out.println(e);
			
		} catch (IOException e) {
			
			System.out.println(e);
			
		} 
    	
	}
	
	private void exibeItemset(HashMap<Integer, ArrayList<String>> itemset){
		
		for (Integer key : itemset.keySet()) {  
			ArrayList<String> value = itemset.get(key); 
			for (String item : value) {
				System.out.println(item);
			} 
		}
		
	}
	
	private void geraItemset(ArrayList<String> frequentItemset, int nivel){
	
		System.out.println("=> Gerando itemsets de n�vel " + (nivel+1));
		
		//if(frequentItemset != null){
			if(nivel == 1){
			
				ArrayList<String> lista = new ArrayList<String>();
			
				for (int i = 0; i < frequentItemset.size(); i++) {
				
					String aux1 = frequentItemset.get(i);
				
					for(int j = i+1; j < frequentItemset.size(); j++){
						String aux2 = frequentItemset.get(j);
						if(!aux1.equals(aux2)){
							String conj = aux1.concat(" " + aux2);
							lista.add(conj);
							//System.out.println("C" + (nivel+1) + ": " + conj);
							System.out.println("{" + conj + "}");
						}
					}
				}
				this.itemset.put(nivel+1, lista);
			}
			//Gera��o dos conjuntos de tamanho maior que 1
			else{
			
				ArrayList<String> lista = new ArrayList<String>(); 
				List<String> data1 = new ArrayList<String>(); // conjunto de dados do item i 
				List<String> data2 = new ArrayList<String>(); // conjunto de dados do item i+1
			
				for(int i = 0; i < frequentItemset.size(); i++){
				
					String conjunto1 = frequentItemset.get(i); //uma combinacao i ja existente
					StringTokenizer token = new StringTokenizer(conjunto1, " ");
				
					while(token.hasMoreTokens()){
						data1.add(token.nextToken());
					}
				
					for(int j = i + 1; j < frequentItemset.size(); j++){
					
						String conjunto2 = frequentItemset.get(j); //uma combinacao i+1 existente
						token = new StringTokenizer(conjunto2, " ");
					
						while(token.hasMoreTokens()){
							data2.add(token.nextToken());
						}
					
						String diferentes = new String(); 
						String iguais = new String();
					
						for(String item2 : data2){
							if(!data1.contains(item2)){
								diferentes += item2 + " " ;
							}else{
								if(iguais.indexOf(item2) == -1){
									iguais += item2 + " ";
								}
							}
						}
						
						for(String item1 : data1){
							if(!data2.contains(item1)){
								diferentes += " " + item1;
							}
						}
					
						String novaCombinacao = iguais + diferentes;
						if(!verificarCombinacao(lista, novaCombinacao)){
							lista.add(novaCombinacao);
							System.out.println("{" + novaCombinacao + "}");
						}
					
						data2.clear();
					}
					data1.clear();
				}
				this.itemset.put(nivel + 1, lista);
			}
		//}
	}
	
	/***
	 * Esse método retorna true se a combinação já existe e false se não. 
	 * @param combinacoes as combinações já existentes
	 * @param novaCombinacao a nova proposta de combinação
	 * @return
	 */
	private boolean verificarCombinacao(ArrayList<String> combinacoes,
			String novaCombinacao) {
		
		StringTokenizer token = new StringTokenizer(novaCombinacao, " ");
		List<String> conjunto = new ArrayList<String>(); 
		
		while(token.hasMoreTokens()){
			conjunto.add(token.nextToken()); 
		}
		
		int count = 0;
		
		for(String combinacao : combinacoes){
			for(String item : conjunto){
				if(combinacao.indexOf(item) != -1){
					count++;
				}
			}
			if(count == conjunto.size()){
				return true; 
			}
			count = 0;
		}
		
		return false;
	}

	/* Esse m�todo recebe um ArrayList onde cada posi��o da lista corresponde a um conjunto de itens
	 * itemset == conjunto de conjuntos. Cada item � um subconjunto
	 * O objetivo �, para cada posi��o do Array (conjunto), extrair os itens de cada conjunto
	 * E calcular o suporte do conjunto
	 * Se o suporte do conjunto for maior ou igual ao suporte m�nimo, adicionar ele ao HashMap itemsetFrequente
	 * */
	private void geraItemsetFrequente(ArrayList<String> itemset, int nivel){
		System.out.println("=> Gerando itemsets frequentes de n�vel " + nivel);
		
		if(nivel == 1){
			
			ArrayList<String> conjuntoFrequente = new ArrayList<String>();
			
			for (String item : itemset) {//Cada item do array � um conjunto de items
				
				int cont = 0;
				
				for (String transacao : this.transacoes) {//Varre o array de transa��es - cada posi��o do array � uma linha
					
					StringTokenizer token = new StringTokenizer(transacao, " "); //cada token � um item

				    while (token.hasMoreTokens()) {//Enquanto houver items na transa��o...
						String t = new String(token.nextToken());
						if(item.equals(t)){						
							cont++;
							break; // ele continua a procurar de qualquer jeito.
						}
					}
				}
				
				double suporteEncontrado = (double)cont/(double)quantTransacoes;
				if(suporteEncontrado >= suporte){
					//System.out.println("O item: '" + item + "' possui o suporte necess�rio");
					System.out.println("{" + item + "}");
					conjuntoFrequente.add(item);
				}
				
			}
			
			this.frequentItemset.put(1, conjuntoFrequente);
			
		}
		else{
			
			ArrayList<String> conjuntoFrequente = new ArrayList<String>();
			
			for (int i = 0; i < itemset.size(); i++) {
				
				ArrayList<String> conjunto = new ArrayList<String>();
				String conj = itemset.get(i); //� necess�rio agora quebrar essa string em itens
				//System.out.println("- Conjunto {" + conj + "}");
				StringTokenizer token1 = new StringTokenizer(conj, " ");

				while (token1.hasMoreTokens()) {
					String str1 = new String(token1.nextToken());
					conjunto.add(str1);
				}
				
				int contTrans = 0; //Contador que ir� contar em quantas transi��es o conjunto aparece
				
				//Verifica em quantas transa��es os itens do conjunto aparecem juntos
				for (String transacao : this.transacoes) {//...verifico se ele est� na transa��o
					
					//System.out.println("Transa��o: {" + transacao + "}");
					StringTokenizer token2 = new StringTokenizer(transacao, " ");
					int cont = 0;
					while (token2.hasMoreTokens()) {
						String itemTrans = new String(token2.nextToken()); //pego o item da transacao...
						
						//System.out.println("Varrendo conjunto...");
						
						for (String itemConjunto : conjunto) {//Pego um item do conjunto...
							//System.out.println("Item do conjunto: " + itemConjunto);
							if(itemTrans.equals(itemConjunto)){//Se o item da transacao � igual a um dos itens do conjunto...
								//System.out.println(itemTrans + " == " + itemConjunto);
								cont++;
								break;
							}
						}

					}
					if(cont == nivel){
						//System.out.println(" * Conjunto encontrado na transa��o {" + transacao + "}");
						contTrans++;
					}

				}
				
				double suporteEncontrado = (double)contTrans/(double)quantTransacoes;
				if(suporteEncontrado >= suporte){
					//System.out.println("O conjunto {" + conj + "} possui o suporte necess�rio");
					System.out.println("{" + conj + "}");
					conjuntoFrequente.add(conj);
				}
				
			}
			
			this.frequentItemset.put(nivel, conjuntoFrequente);
			
		}	
	}
}
