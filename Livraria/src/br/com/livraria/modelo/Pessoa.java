package br.com.livraria.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import br.com.livraria.DAO.DAOUser;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa implements Serializable, DAOUser{

	private static final long serialVersionUID = -5008927798279262520L;

	public Pessoa() {

	}

	public Pessoa(String cadastro, String nome, Endereco endereco, Contato contato) {
		this.cadastro = cadastro;
		this.nome = nome;
		this.endereco = endereco;
		this.contato = contato;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "CADASTRO", nullable = false, length = 14)
	private String cadastro;

	@Column(name = "NOME", nullable = false, length = 32)
	private String nome;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECO_ID", nullable = false, unique = true)
	private Endereco endereco;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTATO_ID", nullable = false, unique = true)
	private Contato contato;

	public Integer getId() {
		return id;
	}

	public String getCadastro() {
		return cadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	// O que identifica uma pessoa unicamente é o seu cadastro
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cadastro == null) ? 0 : cadastro.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (cadastro == null) {
			if (other.cadastro != null) {
				return false;
			}
		} else if (!cadastro.equals(other.cadastro)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [cadastro=" + cadastro + ", nome=" + nome + "]";
	}

}
