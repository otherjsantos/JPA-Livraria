package br.com.livraria.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.livraria.DAO.DAOUser;

@Entity
@Table(name = "CONTATOS")
public class Contato implements Serializable {

	private static final long serialVersionUID = -9031951163167385071L;

	@Deprecated
	public Contato() {
	}

	public Contato(String telefone, String email) {
		this.email = email;
		this.telefone = telefone;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// (xx)xxxxx-xxxx
	@Column(name = "TELEFONE", nullable = true, length = 14, unique = true)
	private String telefone;

	// name@email.com.br
	@Column(name = "EMAIL", nullable = false, length = 256, unique = true)
	private String email;

	public Integer getId() {
		return id;
	}

	public String getTelefoneFixo() {
		return telefone;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefone = telefoneFixo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// O que identifica um contato unicamente é seu e-mail
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Contato other = (Contato) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Contato [email=" + email + "]";
	}

}
