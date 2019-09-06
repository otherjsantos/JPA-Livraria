package br.com.livraria.modelo;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import br.com.livraria.DAO.JPAUtil;

@NamedQuery(name = "Cliente.getLivros", query = "SELECT l FROM Compra c join c.livros l WHERE c.cliente = :pCliente")
@Entity
@Table(name = "CLIENTES")
public class Cliente extends Pessoa {

	private static final long serialVersionUID = 4686406886651053428L;

	@Deprecated
	public Cliente() {
		super();
	}

	public Cliente(String cadastro, String nome, Endereco endereco, Contato contato) {
		super(cadastro, nome, endereco, contato);
	}

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Compra> compras;

	@Transient
	private List<Livro> livros;

	/*
	 * RELACIONAMENTO BIDIRECIONAL Com o intuito de manter a concistência será
	 * devolvido uma lista imutável dos objetos. Sendo assim não será possível
	 * alterar(add ou remove) nossa lista, pois queremos manter essa
	 * responsabilidade no lado dominante do relacionamento
	 */
	public List<Compra> getCompras() {
		return Collections.unmodifiableList(this.compras);
	}

	public List<Livro> getLivros() {

		EntityManager em = new JPAUtil().getLivrariaEntityManager();

		em.getTransaction().begin();

		TypedQuery<Livro> typedQuery = em.createNamedQuery("Cliente.getLivros", Livro.class);
		typedQuery.setParameter("pCliente", this);
		this.livros = typedQuery.getResultList();

		em.getTransaction().commit();
		em.close();

		return Collections.unmodifiableList(this.livros);
	}
}
