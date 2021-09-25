package hotel.manager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hotel.manager.model.CheckIn;
import hotel.manager.model.Diaria;
import hotel.manager.model.Hospede;
import hotel.manager.repository.CheckInRepository;
import hotel.manager.repository.DiariaRepository;
import hotel.manager.repository.HospedeRepository;
import hotel.manager.util.Constantes;

@Component
public class CobrancaDiaria {

	private static final Logger log = LoggerFactory.getLogger(CobrancaDiaria.class);
	
	@Autowired
	private DiariaRepository diariaRepository;

	@Autowired
	private HospedeRepository hospedeRepository;

	@Autowired
	private CheckInRepository checkInRepository;
	
	// Executa uma vez por dia (86400000 = 24h em milisegundos)
	@Scheduled(fixedDelay = 86400000)
	public void contabilizarDiarias() {
		
		// Obtem apenas os hospedes que estao no hotel (Data de saida como null)
		List<Hospede> hospedes = hospedeRepository.obterHospedes();
		
		for(Hospede hospede : hospedes) {
			
			// Verifica se houve alguma cobrança do hospede na data atual
			List<Diaria> diarias = diariaRepository.obterDiariasContabilizadas(LocalDate.now(), hospede.getId());
			
			// Se nao houve nenhuma cobrança ainda, sera contabilizado a cobrança da diaria do hospede
			if(diarias.isEmpty()) {
				
				CheckIn checkIn = checkInRepository.obterCheckIn(hospede.getId());
				
				LocalDateTime dataAtual = LocalDateTime.now();
				
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
				
				checkInRepository.save(checkIn);
				
				// REGISTRA QUE O HOSPEDE JA FOI COBRADO NO DIA, EVITA DUPLICIDADE NAS COBRANÇAS
				diariaRepository.save(new Diaria(LocalDate.now(), hospede.getId(), true));
				
				log.info("DIARIA DO HOSPEDE ID = " + hospede.getId() + "FOI CONTABILIZADA!");
			}
		}
	}
}
