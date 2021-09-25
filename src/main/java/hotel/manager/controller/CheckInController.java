package hotel.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.manager.model.CheckIn;
import hotel.manager.service.CheckInService;

@RestController
@RequestMapping(value = "/checkin")
public class CheckInController {
	
	@Autowired
	private CheckInService checkInService;
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<CheckIn>> obterChecksIn() {
		
		return new ResponseEntity<List<CheckIn>>(checkInService.obterChecksIn(), HttpStatus.OK);
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<?> inserirCheckIn(@RequestBody CheckIn checkIn) {
		
		if(checkIn.getHospede() == null || checkIn.getHospede().getId() == null || checkIn.getHospede().getId() < 1) {
			
			return new ResponseEntity<String>("O campo hospede nao pode ser nulo ou um valor negativo", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<CheckIn>(checkInService.inserir(checkIn), HttpStatus.OK);
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<?> alterarCheckIn(@RequestBody CheckIn checkIn) {
		
		if(checkIn.getHospede() == null || checkIn.getHospede().getId() == null || checkIn.getHospede().getId() < 1) {
			
			return new ResponseEntity<String>("O campo hospede nao pode ser nulo ou um valor negativo", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<CheckIn>(checkInService.alterar(checkIn), HttpStatus.OK);
	}
}
