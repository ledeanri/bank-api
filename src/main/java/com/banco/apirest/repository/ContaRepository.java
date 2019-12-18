package com.banco.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.apirest.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	Conta findByNumeroContaAndSenha(Long numeroConta, String senha);
	
	Conta findByNumeroConta(Long numeroConta);
	
}
