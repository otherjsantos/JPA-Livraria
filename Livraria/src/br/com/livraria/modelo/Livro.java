package br.com.livraria.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import br.com.livraria.DAO.DAOUser;
import br.com.livraria.DAO.JPAUtil;

@NamedQuery(name = "Livro.getClientes", query = "SELECT c.cliente FROM Compra c join c.livros l  WHERE l = :pLivro")
@Entity
@Table(name = "LIVROS")
public class Livro implements Serializable, DAOUser{

	private static final long serialVersionUID = 3352799489280441981L;

	@Deprecated
	public Livro() {
		
	}
	
	public Livro(String nome, Editora editora, String isbn, BigDecimal preco, Long quantidade) {
		this.nome = nome;
		this.editora = editora;
		this.isbn = isbn;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "NOME", nullable = false, length = 32)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "EDITORA_ID", nullable = false)
	private Editora editora;

	@Column(name = "ISBN", nullable = false, length = 13)
	private String isbn;

	@Column(name = "PRECO", nullable = false)
	private BigDecimal preco;

	@Column(name = "QUANTIDADE", nullable = false)
	private Long quantidade;

	@ManyToMany(mappedBy = "livros", cascade = CascadeType.ALL)
	private List<Compra> compras;

	@Transient
	private List<Cliente> clientes;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public String getIsbn() {
		return isbn;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	/*
	 * RELACIONAMENTO BIDIRECIONAL Com o intuito de manter a concistência será
	 * devolvido uma lista imutável dos objetos. Sendo assim não será possível
	 * alterar(add ou remove) nossa lista, pois queremos manter essa
	 * responsabilidade no lado dominante do relacionamento
	 */
	public List<Cliente> getClientes() {
		
		EntityManager em = new JPAUtil().getLivrariaEntityManager();
		
		em.getTransaction().begin();
		
		TypedQuery<Cliente> typedQuery = em.createNamedQuery("Livro.getClientes", Cliente.class);
		typedQuery.setParameter("pLivro", this);
		
		this.clientes = typedQuery.getResultList();
		
		em.getTransaction().commit();
		em.close();
		
		return Collections.unmodifiableList(this.clientes);
	}

	// O que identifica um livro unicamente é o seu ISBN
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
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
		Livro other = (Livro) obj;
		if (isbn == null) {
			if (other.isbn != null) {
				return false;
			}
		} else if (!isbn.equals(other.isbn)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Livro [nome=" + nome + ", isbn=" + isbn + "]";
	}
}
