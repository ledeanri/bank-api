package com.banco.apirest.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_conta")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1544280082564395116L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long numeroConta;
	
	private Double saldo;
	
	private String senha;
	
	public Long getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Long numeroConta) {
		this.numeroConta = numeroConta;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
