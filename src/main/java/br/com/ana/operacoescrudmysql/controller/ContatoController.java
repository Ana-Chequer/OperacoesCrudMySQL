package br.com.ana.operacoescrudmysql.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ana.operacoescrudmysql.domain.Contato;
import br.com.ana.operacoescrudmysql.exception.ContatoInvalidoException;
import br.com.ana.operacoescrudmysql.exception.ContatoNaoEncontradoException;
import br.com.ana.operacoescrudmysql.repository.ContatoRepository;
import br.com.ana.operacoescrudmysql.service.ContatoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/contacts")
public class ContatoController {
	
	private static Logger logger = LoggerFactory.getLogger(ContatoController.class);

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private ContatoService contatoService;

	@GetMapping
	public List<Contato> listarContatos() {
		logger.info("Passou por aqui na lista de contatos");
		return contatoRepository.findAll();
	}

	@GetMapping("/{contatoId}")
	public ResponseEntity<Contato> buscarContato(@PathVariable Long contatoId) {
		
		Optional<Contato> contato = contatoRepository.findById(contatoId);

		if (contato.isPresent()) {
			return ResponseEntity.ok(contato.get());

		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<?> adicionarContato(@RequestBody Contato contato) {
		logger.info("Contato no adicionar " + contato.getEmailPrincipal());

		try {
			contatoService.salvar(contato);
			return ResponseEntity.status(HttpStatus.CREATED).body(contato);

		} catch (ContatoInvalidoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{contatoId}")
	public ResponseEntity<Contato> atualizarContato(@PathVariable Long contatoId, @RequestBody Contato contato) {

		Optional<Contato> contatoCadastrado = contatoRepository.findById(contatoId);

		if (contatoCadastrado.isPresent()) {
			
			logger.info("contatoId " + contatoId);
			logger.info("nome proposto " + contato.getNome());
			logger.info("nome original " + contatoCadastrado.get().getNome());
			
			BeanUtils.copyProperties(contato, contatoCadastrado.get(), "id");
			logger.info("nome alterado " + contatoCadastrado.get().getNome());
			Contato contatoAtualizado = contatoService.atualizar(contatoCadastrado.get());
			return ResponseEntity.ok(contatoAtualizado);

		}
		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{contatoId}")
	public ResponseEntity<?> excluirContato(@PathVariable Long contatoId) {

		try {
			contatoService.excluir(contatoId);
			return ResponseEntity.noContent().build();

		} catch (ContatoNaoEncontradoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

}
