package com.banco.apirest.resources;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.banco.apirest.models.Conta;
import com.banco.apirest.repository.ContaRepository;
import com.banco.apirest.vo.ContaVO;
import com.sun.xml.fastinfoset.stax.events.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Simulador de Banco")
@CrossOrigin(origins = "*")
public class ContaResource {

	@Autowired
	ContaRepository contaRepository;

	@PostMapping("/conta/saldo")
	@ApiOperation(value = "Retorna o saldo de uma conta")
	public ResponseEntity<Object> getSaldoConta(@RequestBody ContaVO contaVO) {
		try {
			List<String> listaErros = validaCampos(contaVO, "saldo");
			if(listaErros.size() > 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(listaErros.toString());
			}
			
			Conta conta = contaRepository.findByNumeroContaAndSenha(contaVO.getNumeroConta(), criptografaSenha(contaVO.getSenha()));
			return ResponseEntity.status(HttpStatus.OK).body(conta.getSaldo());
		} catch (ResourceAccessException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro inesperado, favor conferir o log para maiores detalhes." + e.getMessage());
		}
	}

	@PutMapping("/conta/deposito")
	@ApiOperation(value = "Realiza deposita em uma conta")
	public ResponseEntity<Object> getDepositoConta(@RequestBody ContaVO contaVO) {
		try {
			List<String> listaErros = validaCampos(contaVO, "deposito");
			if(listaErros.size() > 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(listaErros.toString());
			}
			Conta conta = contaRepository.findByNumeroConta(contaVO.getNumeroConta());
			conta.setSaldo(conta.getSaldo() + contaVO.getValorPassado());
			contaRepository.save(conta);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (ResourceAccessException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro inesperado, favor conferir o log para maiores detalhes." + e.getMessage());
		}
	}

	@PutMapping("/conta/saque")
	@ApiOperation(value = "Realiza saque em uma conta")
	public ResponseEntity<Object> getSaqueConta(@RequestBody ContaVO contaVO) {
		try {
			List<String> listaErros = validaCampos(contaVO, "saque");
			if(listaErros.size() > 0) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(listaErros.toString());
			}
			Conta conta = contaRepository.findByNumeroContaAndSenha(contaVO.getNumeroConta(), criptografaSenha(contaVO.getSenha()));

			conta.setSaldo(conta.getSaldo() - contaVO.getValorPassado());
			contaRepository.save(conta);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} catch (ResourceAccessException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro inesperado, favor conferir o log para maiores detalhes." + e.getMessage());
		}
	}
	
	public String criptografaSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(senha.getBytes("UTF-8"));

		byte[] passwordDigest = digest.digest();

		
		return passwordDigest.toString();
	}
	
	private List<String> validaCampos(ContaVO contaVO, String funcao) {
		List<String> listaErros = new ArrayList<String>();
		
		if(contaVO == null || (contaVO.getNumeroConta() == null)) {
			listaErros.add("Número da conta é obrigatório.");
		}
		if(contaVO == null || (Util.isEmptyString(contaVO.getSenha()) && !funcao.equals("deposito"))) {
			listaErros.add("Senha da conta é obrigatório.");
		}
		if(contaVO == null || (contaVO.getValorPassado() == 0 && !funcao.equals("saldo"))) {
			listaErros.add("Valor passado deve ser diferente de zero.");
		}
		
		if(contaVO.getValorPassado() < 0 && !funcao.equals("saldo")) {
			listaErros.add("Valor passado não deve ser menor zero.");
		}
		return listaErros;
	}
}
