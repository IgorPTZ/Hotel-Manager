package hotel.manager.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hotel.manager.model.CheckIn;
import hotel.manager.model.Diaria;
import hotel.manager.model.Hospede;
import hotel.manager.repository.CheckInRepository;
import hotel.manager.repository.DiariaRepository;
import hotel.manager.repository.HospedeRepository;
import hotel.manager.util.Constantes;

@Service
public class CheckInService {

	@Autowired
	private CheckInRepository checkInRepository;
	
	@Autowired
	private HospedeRepository hospedeRepository;
	
	@Autowired
	private DiariaRepository diariaRepository;
	
	
	public List<CheckIn> obterChecksIn() {
		
		return checkInRepository.findAll();
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	public CheckIn inserir(CheckIn checkIn) {
		
		LocalDateTime dataAtual = LocalDateTime.now();
		
		Hospede hospede = hospedeRepository.getById(checkIn.getHospede().getId());
		
		if(dataAtual.getDayOfWeek() == DayOfWeek.SATURDAY || dataAtual.getDayOfWeek() == DayOfWeek.SUNDAY) { // SABADO OU DOMINGO
			
			if(checkIn.getAdicionalVeiculo()) {
				
				checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_FINAL_SEMANA + Constantes.ADICIONAL_VEICULO_FINAL_SEMANA);
				
				hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_FINAL_SEMANA + Constantes.ADICIONAL_VEICULO_FINAL_SEMANA);
			}
			else {
				
				checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_FINAL_SEMANA);
				
				hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_FINAL_SEMANA);
			}
		}
		else { // ALGUM DIA DA SEMANA
			
			if(checkIn.getAdicionalVeiculo()) {
				
				checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_SEMANA + Constantes.ADICIONAL_VEICULO_SEMANA);
				
				hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_SEMANA + Constantes.ADICIONAL_VEICULO_SEMANA);
			}
			else {
				
				checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_SEMANA);
				
				hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_SEMANA);
			}
		}
		
		diariaRepository.save(new Diaria(LocalDate.now(), hospede.getId(), true));
		
		hospedeRepository.save(hospede);
		
		return checkInRepository.save(checkIn);
	}
	
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	public CheckIn alterar(CheckIn parametros) {
		
		LocalDateTime dataAtual = LocalDateTime.now();
		
		CheckIn checkIn = checkInRepository.findById(parametros.getId()).get();
		
		checkIn.setDataSaida(dataAtual);
		
		// Caso tenha passado das 16:30 é cobrado uma nova diaria
		if(LocalTime.now().isAfter(LocalTime.of(16, 30))) {
			
			Hospede hospede = hospedeRepository.getById(checkIn.getHospede().getId());
			
			if(dataAtual.getDayOfWeek() == DayOfWeek.SATURDAY || dataAtual.getDayOfWeek() == DayOfWeek.SUNDAY) { // SABADO OU DOMINGO
				
				if(checkIn.getAdicionalVeiculo()) {
					
					checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_FINAL_SEMANA + Constantes.ADICIONAL_VEICULO_FINAL_SEMANA);
					
					hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_FINAL_SEMANA + Constantes.ADICIONAL_VEICULO_FINAL_SEMANA);
				}
				else {
					
					checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_FINAL_SEMANA);
					
					hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_FINAL_SEMANA);
				}
			}
			else { // ALGUM DIA DA SEMANA
				
				if(checkIn.getAdicionalVeiculo()) {
					
					checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_SEMANA + Constantes.ADICIONAL_VEICULO_SEMANA);
					
					hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_SEMANA + Constantes.ADICIONAL_VEICULO_SEMANA);
				}
				else {
					
					checkIn.setTotal(checkIn.getTotal() + Constantes.DIARIA_SEMANA);
					
					hospede.setValorGasto(hospede.getValorGasto() + Constantes.DIARIA_SEMANA);
				}
			}
			
			hospedeRepository.save(hospede);
		}
		
		return checkInRepository.save(checkIn);
	}
}
