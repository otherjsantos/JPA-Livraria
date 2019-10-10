package br.com.livraria.DAO;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public abstract class DAOImp<TypeOfClass extends DAOUser> {

	protected EntityManager em;

	public DAOImp(EntityManager em) {
		this.em = em;
	}

	public TypeOfClass findById(Class<TypeOfClass> entityClass, Integer id) {
		return this.em.find(entityClass, id);
	}

	public TypeOfClass getReference(Class<TypeOfClass> entityClass, Integer id) {
		return this.em.getReference(entityClass, id);
	}

	public void saveOrUpdate(TypeOfClass obj) {

		try {
			this.em.getTransaction().begin();

			if (obj.getId() == null) {
				this.em.persist(obj);
			} else {
				this.em.merge(obj);
			}
			this.em.getTransaction().commit();
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			System.out.println(e);
		}
	}

	public void remove(Class<TypeOfClass> entityClass, Integer id) {

		try {
			this.em.getTransaction().begin();
			this.em.remove(em.find(entityClass, id));
			this.em.getTransaction().commit();
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			System.out.println(e);
		}

	}

}
