package br.com.livraria.modelo;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQuery(name = "Editora.getByCadastro", query = "SELECT e FROM Editora e WHERE e.cadastro = :pCadastro")
@Entity
@Table(name = "EDITORAS")
public class Editora extends Pessoa {

	private static final long serialVersionUID = -8779452205586604792L;

	@Deprecated
	public Editora() {
		super();
	}

	public Editora(String cadastro, String nome, Endereco endereco, Contato contato, Gerente gerente) {
		super(cadastro, nome, endereco, contato);
		this.gerente = gerente;
	}

	@Embedded
	private Gerente gerente;

	@OneToMany(mappedBy = "editora", cascade = CascadeType.ALL)
	private List<Livro> livros;

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

	// Lado dominado. Sendo assim só terá permissão de retornar uma lista imutável
	public List<Livro> getLivros() {
		List<Livro> listaLivrosSegura = Collections.unmodifiableList(this.livros);
		return listaLivrosSegura;
	}
}
