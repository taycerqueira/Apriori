package br.uefs.apriori;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class AlgoritmoApriori {
	
	private double suporte;
	private double confianca;
	String arquivoTransacoes = "transacoes.txt";
	private int quantTransacoes;
	private HashMap<Integer, ArrayList<String>> itemset = new HashMap<Integer, ArrayList<String>>(); //conjuntos possíveis
	private HashMap<Integer, ArrayList<String>> frequentItemset = new HashMap<Integer, ArrayList<String>>(); //conjuntos com o suporte mínimo
	
	
	//Seleciona os itens contidos nas transações (itemset 1)
	
	/*Gera itemset - conjuntos possíveis gerados a partir de um frequent itemset anterior
	 *  => Se o número do conjunto for 1, o itemset são todos os items presentes nas transações
	 */
	
	
	//Gera frequentItemset - seleciona os conjuntos que possuem o suporte mínimo
	
	//Gera as regras para um itemset frequente e calcular sua confiança
	
	public AlgoritmoApriori(){
		geraPrimeiroConjunto();
		System.out.println("C1: ");
		exibeItemset(this.itemset);
		System.out.println("Quantidade de Transações: " + quantTransacoes);
	}
	
	//Gera o conjunto C1 a partir do arquivo .txt
	private void geraPrimeiroConjunto(){
		
		try {
			BufferedReader dados = new BufferedReader(new FileReader(arquivoTransacoes));
			List<String> itensTransacao = new ArrayList<String>();
			while (dados.ready()) { 
				String linha = dados.readLine();
				
				if (linha.matches("\\s*")){
					continue; 
				}
				
				quantTransacoes++;
				StringTokenizer token = new StringTokenizer(linha, " ");

				while (token.hasMoreTokens()) {
					
					String item = new String(token.nextToken());
					
					if(!itensTransacao.contains(item)){
						itensTransacao.add(item);	
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

}
