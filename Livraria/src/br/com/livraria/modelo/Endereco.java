package br.com.livraria.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table
(
		name = "ENDERECOS",
		uniqueConstraints={@UniqueConstraint(columnNames={"CEP", "ESTADO"})}
)
public class Endereco implements Serializable{

	private static final long serialVersionUID = 2570710126051037579L;

	@Deprecated
	public Endereco() {
	}

	public Endereco(String cep, Estado estado) {
		this.cep = cep;
		this.estado = estado;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "CEP", nullable = false, length = 9)
	private String cep;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false)
	private Estado estado;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	// O que identifica um endereço unicamente são todos os atributos exceto o id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Endereco other = (Endereco) obj;
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (estado != other.estado) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [cep=" + cep + ", estado=" + estado + "]";
	}

}
