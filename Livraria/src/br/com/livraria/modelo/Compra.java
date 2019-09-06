package br.com.livraria.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.com.livraria.DAO.JPAUtil;

@Entity
@Table(name = "COMPRAS", uniqueConstraints = { @UniqueConstraint(columnNames = { "CODIGO", "CLIENTE_ID" }) })
public class Compra implements Serializable {

	private static final long serialVersionUID = -6280393187322821142L;

	@Deprecated
	public Compra() {
	}

	public Compra(Cliente cliente, List<Livro> livros) {
		this.cliente = cliente;
		this.data = Calendar.getInstance();
		this.livros = livros;
		this.valorTotal = new BigDecimal("0.0");
		for (Livro livro : livros) {
			this.valorTotal = this.valorTotal.add(livro.getPreco());
		}
		this.codigo = new JPAUtil().getCodigo();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Integer id;

	// Gerado pelo metodo getCodigo() da classe JPAUtil;
	// Formato MMdd + número aleátorio de 0-9999;
	// Foi adicionada uma constraint que chaveia o codigo com o cliente, tornando
	// possível 10000 compras por cliente.
	@Column(name = "CODIGO", nullable = false, length = 8)
	private String codigo;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_ID", nullable = false)
	private Cliente cliente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA", nullable = false)
	private Calendar data;

	@ManyToMany
	@JoinTable(name = "COMPRAS_LIVROS", joinColumns = { @JoinColumn(name = "COMPRA_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "LIVRO_ID") })
	private List<Livro> livros;

	@Column(name = "VALOR_TOTAL", nullable = false)
	private BigDecimal valorTotal;

	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Calendar getData() {
		return data;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	// O que identifica uma compra unicamente é o seu código
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Compra other = (Compra) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Compra [codigo=" + codigo + "]";
	}
}
