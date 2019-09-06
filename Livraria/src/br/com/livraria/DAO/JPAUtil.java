package br.com.livraria.DAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("livraria");

	public EntityManager getLivrariaEntityManager() {
		return emf.createEntityManager();
	}

	public String getCodigo() {

		Date dataAtual = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("MMdd");
		String codigo = dateFormat.format(dataAtual) + new Random().nextInt(9999);

		return codigo;
	}

}
