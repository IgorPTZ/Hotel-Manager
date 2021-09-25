package hotel.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.manager.model.Hospede;
import hotel.manager.service.HospedeService;

@RestController
@RequestMapping(value = "/hospede")
public class HospedeController {
	
	@Autowired
	private HospedeService hospedeService;
	
	@GetMapping(value = "/{pagina}/{tipo}", produces = "application/json")
	public ResponseEntity<Page<Hospede>> obterHospedes(@PathVariable(value = "pagina") Long pagina, @PathVariable(value = "tipo") Long tipoHospede) {
		
		return new ResponseEntity<Page<Hospede>>(hospedeService.obterHospedes(pagina, tipoHospede), HttpStatus.OK);
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Hospede> inserirHospede(@RequestBody Hospede hospede) {
		
		return new ResponseEntity<Hospede>(hospedeService.inserir(hospede), HttpStatus.OK);
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Hospede> alterarHospede(@RequestBody Hospede hospede) {
		
		return new ResponseEntity<Hospede>(hospedeService.alterar(hospede), HttpStatus.OK);
	}
}
