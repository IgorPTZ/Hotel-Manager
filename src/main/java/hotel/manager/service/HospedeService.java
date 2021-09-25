package hotel.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hotel.manager.model.Hospede;
import hotel.manager.repository.HospedeRepository;
import hotel.manager.util.Constantes;

@Service
public class HospedeService {
	
	@Autowired
	private HospedeRepository hospedeRepository;
	
	public Page<Hospede> obterHospedes(Long pagina, Long tipoHospede) {
		
		if(tipoHospede == Constantes.HOSPEDE) {
			
			return hospedeRepository.obterHospedes(PageRequest.of(pagina.intValue(), 10, Sort.by("nome")));
		}
		else if(tipoHospede == Constantes.EX_HOSPEDE) {
			
			return hospedeRepository.obterExHospedes(PageRequest.of(pagina.intValue(), 10, Sort.by("nome")));
		}
		
		return null;
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	public Hospede inserir(Hospede hospede) {
		
		if(hospede.getValorGasto() == null) {
			
			hospede.setValorGasto(0.0);
		}
		
		return hospedeRepository.save(hospede);
	}
	
	/*
	 * Verificar a classe CobrancaDiaria no pacote hotel.manager, nela esta declarada uma rotina (scheduled task) que realiza a cobrança das diarias do hospede
	 * 
	 */
	public Hospede alterar(Hospede parametros) {
		
		Hospede hospede = hospedeRepository.findById(parametros.getId()).get();
		
		hospede.setNome(parametros.getNome());
		
		hospede.setDocumento(parametros.getDocumento());
		
		hospede.setTelefone(parametros.getTelefone());
		
		return hospedeRepository.save(hospede);
	}
}
