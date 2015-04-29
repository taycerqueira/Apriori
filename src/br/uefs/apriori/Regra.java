package br.uefs.apriori;

public class Regra {

	private String causa; 
	private String consequencia; 
	private double confianca;
	private int nivel;
	
	public Regra(String causa, String consequencia, double confianca, int nivel) {
		super();
		this.causa = causa;
		this.consequencia = consequencia;
		this.confianca = confianca;
		this.nivel =  nivel;
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
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Regra){
			Regra c = (Regra) o; 
			if(this.causa.equals(c.getCausa()) && this.consequencia.equals(c.getConsequencia())){
				return true;
			}
		}
		return false;
	}
	
	
}
