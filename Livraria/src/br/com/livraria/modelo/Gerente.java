package br.com.livraria.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Gerente {

	@Deprecated
	public Gerente() {
	}

	public Gerente(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	@Column(name = "GERENTE_NOME", nullable = false, length = 32)
	private String nome;

	@Column(name = "GERENTE_EMAIL", nullable = false, length = 64)
	private String email;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// O que identifica um gerente unicamente é o seu e-mail
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
		Gerente other = (Gerente) obj;
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
		return "Gerente [nome=" + nome + ", email=" + email + "]";
	}
}
