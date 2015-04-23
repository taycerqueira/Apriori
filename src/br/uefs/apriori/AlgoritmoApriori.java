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
	private ArrayList<String> items; //guarda todos os itens de todas as transa��es, incluindo as repeti��es
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
	}
	
	//Gera o conjunto C1 a partir do arquivo .txt
	private void geraPrimeiroItemset(){
		System.out.println("=> Gerando itemsets de n�vel 1");
		
		try {
			BufferedReader dados = new BufferedReader(new FileReader(arquivoTransacoes));
			List<String> itensTransacao = new ArrayList<String>();
			this.items = new ArrayList<String>();
			while (dados.ready()) { 
				String linha = dados.readLine();
				
				if (linha.matches("\\s*")){
					continue; 
				}
				
				quantTransacoes++;
				StringTokenizer token = new StringTokenizer(linha, " ");

				while (token.hasMoreTokens()) {
					
					String item = new String(token.nextToken());
					this.items.add(item);
					
					if(!itensTransacao.contains(item)){
						itensTransacao.add(item);
						System.out.println("C1: " + item);
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
		if(nivel == 1){
			
			ArrayList<String> lista = new ArrayList<String>();
			for (int i = 0; i < frequentItemset.size(); i++) {
				
				String aux1 = frequentItemset.get(i);
				
				for(int j = i+1; j < frequentItemset.size(); j++){
					String aux2 = frequentItemset.get(j);
					if(!aux1.equals(aux2)){
						String conj = aux1.concat(" " + aux2);
						lista.add(conj);
						System.out.println("C" + (nivel+1) + ": " + conj);
						
					}
					
				}
				
			}
			
			this.itemset.put(2, lista);
			
		}
		else{
			
			ArrayList<String> lista = new ArrayList<String>();
			
			for (int i = 0; i < frequentItemset.size(); i++) {
				System.out.println("Conjunto " + i);
				String aux = frequentItemset.get(i); //� necess�rio agora quebrar essa string em itens
				StringTokenizer token = new StringTokenizer(aux, " ");

				while (token.hasMoreTokens()) {
					
					String str1 = new String(token.nextToken());
					System.out.println(str1);
					
				}
				
				
				/*for(int j = i+1; j < frequentItemset.size(); j++){
					String aux2 = frequentItemset.get(j);
					if(!aux1.equals(aux2)){
						String conj = aux1.concat(" " + aux2);
						lista.add(conj);
						System.out.println("C2: " + conj);
						
					}
					
				}*/
				
			}
			
		}
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
			
			ArrayList<String> conjunto = new ArrayList<String>();
			
			for (String item : itemset) {//Cada item do array � um conjunto de items
				
				int cont = 0;
				for(int i = 0; i < items.size(); i++){
					
					if(item.equals(items.get(i))){
						cont++;
					}
					
				}
				
				double suporteEncontrado = (double)cont/(double)quantTransacoes;
				if(suporteEncontrado >= suporte){
					System.out.println("O item: '" + item + "' possui o suporte necess�rio");
					conjunto.add(item);
				}
				
			}
			
			this.frequentItemset.put(1, conjunto);
			
		}
		else{
			
			ArrayList<String> conjunto = new ArrayList<String>();
			ArrayList<String> lista = new ArrayList<String>();
			
			for (int i = 0; i < itemset.size(); i++) {
				System.out.println(" - Conjunto " + i);
				String conj = itemset.get(i); //� necess�rio agora quebrar essa string em itens
				StringTokenizer token1 = new StringTokenizer(conj, " ");

				while (token1.hasMoreTokens()) {
					String str = new String(token1.nextToken());
					conjunto.add(str);
					System.out.println(str);	
				}
				
				//Verifica se o conjunto � frequente
				//FAZERR!!
				
			}
			
		}
		
	}
	

}
