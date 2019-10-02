package br.com.livraria.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOImp<TypeOfClass extends DAOUser> {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("livraria");

	// Isolar a criação de gerente das entidades
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public TypeOfClass findById(Class<TypeOfClass> entityClass, Integer id) {
		return this.getEntityManager().find(entityClass, id);
	}

	public void saveOrUpdate(TypeOfClass obj) {

		EntityManager em = this.getEntityManager();

		try {
			em.getTransaction().begin();

			if (obj.getId() == null) {
				em.persist(obj);
			} else {
				em.merge(obj);
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			System.out.println(e);
		}
	}

}
