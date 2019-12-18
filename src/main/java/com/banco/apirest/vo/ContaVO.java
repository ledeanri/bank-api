package com.banco.apirest.vo;

public class ContaVO {
	private Long numeroConta;
	
	private String senha;
	
	private Double valorPassado;

	public Long getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Long numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Double getValorPassado() {
		return valorPassado;
	}

	public void setValorPassado(Double valorPassado) {
		this.valorPassado = valorPassado;
	}
}
