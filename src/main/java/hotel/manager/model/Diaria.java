package hotel.manager.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="diaria")
public class Diaria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "periodo")
	private LocalDate periodo;
	
	@Column(name = "hospede_id")
	private Long hospedeId;
	
	@Column(name = "contabilizado")
	private Boolean contabilizado;

	public Diaria() {
		super();
	}
	
	public Diaria(LocalDate periodo, Long hospedeId, Boolean contabilizado) {
		super();
		this.periodo = periodo;
		this.hospedeId = hospedeId;
		this.contabilizado = contabilizado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getPeriodo() {
		return periodo;
	}

	public void setPeriodo(LocalDate periodo) {
		this.periodo = periodo;
	}

	public Long getHospedeId() {
		return hospedeId;
	}

	public void setHospedeId(Long hospedeId) {
		this.hospedeId = hospedeId;
	}

	public Boolean getContabilizado() {
		return contabilizado;
	}

	public void setContabilizado(Boolean contabilizado) {
		this.contabilizado = contabilizado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diaria other = (Diaria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
