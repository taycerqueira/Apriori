package br.uefs.apriori;

public class Item {

	private String combinacao; 
	private int frequencia; 
	private double suporte;
	
	public Item(String combinacao, int frequencia, double suporte) {
		super();
		this.combinacao = combinacao;
		this.frequencia = frequencia;
		this.suporte = suporte;
	}
	
	public String getCombinacao() {
		return combinacao;
	}
	public void setCombinacao(String combinacao) {
		this.combinacao = combinacao;
	}
	public int getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
	public double getSuporte() {
		return suporte;
	}
	public void setSuporte(double suporte) {
		this.suporte = suporte;
	}
	
	@Override
	public String toString(){
		return "combinacao => ["+this.combinacao+"] ; frequencia => ["+this.frequencia+"]; suporte => ["+this.suporte+"]";
	}
	
	
}
