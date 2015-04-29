package br.uefs.apriori;

public class Combinacao {

	private String causa; 
	private String consequencia; 
	private double confianca;
	
	public Combinacao(String causa, String consequencia, double confianca) {
		super();
		this.causa = causa;
		this.consequencia = consequencia;
		this.confianca = confianca;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	public String getConsequencia() {
		return consequencia;
	}
	public void setConsequencia(String consequencia) {
		this.consequencia = consequencia;
	}
	public double getConfianca() {
		return confianca;
	}
	public void setConfianca(double confianca) {
		this.confianca = confianca;
	} 
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Combinacao){
			Combinacao c = (Combinacao) o; 
			if(this.causa.equals(c.getCausa()) && this.consequencia.equals(c.getConsequencia())){
				return true;
			}
		}
		return false;
	}
	
	
}
