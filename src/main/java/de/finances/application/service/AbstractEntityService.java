package de.finances.application.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.exception.ConstraintViolationException;

import de.finances.application.model.BaseEntity;
import lombok.Getter;

public class AbstractEntityService<E extends BaseEntity> {

	@Getter
	private final EntityManager em;

	public AbstractEntityService() {
		this.em = PersistenceManager.INSTANCE.getEntityManager();
	}

	private TypedQuery<E> addParams(final Map<String, Object> params, final TypedQuery<E> query) {
		for (final Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query;
	}

	private TypedQuery<Double> addParamsForDouble(final Map<String, Object> params, final TypedQuery<Double> query) {
		for (final Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query;
	}

	public E create(final E entity) {
		this.em.getTransaction().begin();
		this.em.persist(entity);
		this.em.flush();
		this.em.refresh(entity);
		this.em.getTransaction().commit();
		return entity;
	}

	public void delete(final Collection<E> entities) {
		entities.stream().forEach(this::delete);
	}

	public void delete(final E entity) throws ConstraintViolationException {
		this.em.getTransaction().begin();
		this.em.remove(this.em.merge(entity));
		this.em.flush();
		this.em.getTransaction().commit();
	}

	public E find(final E entity) {
		return this.find(entity.getId());
	}

	public E find(final Long id) {
		return this.em.find(this.getEntityClass(), id);
	}

	public Long findCountWithNamedQuery(final String namedQueryName, final Map<String, Object> params) {

		final TypedQuery<Long> query = this.em.createNamedQuery(namedQueryName, Long.class);

		for (final Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getSingleResult();
	}

	public Double findCountWithNamedQuery(final String namedQueryName, final Map<String, Object> params,
			final int firstRow, final int maxRow) {

		TypedQuery<Double> query = this.em.createNamedQuery(namedQueryName, Double.class);

		query = this.addParamsForDouble(params, query);
		query.setFirstResult(firstRow).setMaxResults(maxRow).getResultList();
		query.setMaxResults(maxRow);

		return query.getSingleResult();
	}

	public E findSingleWithNamedQuery(final String namedQueryName, final Map<String, Object> params) {
		return this.findWithNamedQuery(namedQueryName, params).stream().findFirst().orElse(null);
	}

	public List<E> findWithNamedQuery(final String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName, this.getEntityClass()).getResultList();
	}

	public List<E> findWithNamedQuery(final String namedQueryName, final Map<String, Object> params) {

		final TypedQuery<E> query = this.em.createNamedQuery(namedQueryName, this.getEntityClass());

		for (final Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getResultList();
	}

	public List<E> findWithNamedQuery(final String namedQueryName, final Map<String, Object> params, final int firstRow,
			final int maxRow) {

		TypedQuery<E> query = this.em.createNamedQuery(namedQueryName, this.getEntityClass());

		query = this.addParams(params, query);
		query.setFirstResult(firstRow).setMaxResults(maxRow).getResultList();
		query.setMaxResults(maxRow);

		return query.getResultList();
	}

	public List<E> findWithNamedQuery(final String namedQueryName, final Map<String, Object> params, final int first,
			final int pagesize, final String sortfield, final Map<String, Object> filters) {

		TypedQuery<E> query = this.em.createNamedQuery(namedQueryName, this.getEntityClass());
		query = this.addParams(params, query);
		return query.setFirstResult(first).setMaxResults(pagesize).getResultList();

	}

	protected Class<E> getEntityClass() {
		return ClassUtil.getActualTypeBinding(this.getClass(), AbstractEntityService.class, 0);
	}

	public List<E> listAll() {
		final CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
		final CriteriaQuery<E> cq = cb
				.createQuery(ClassUtil.getActualTypeBinding(this.getClass(), AbstractEntityService.class, 0));
		final Root<E> rootEntry = cq
				.from(ClassUtil.getActualTypeBinding(this.getClass(), AbstractEntityService.class, 0));
		final CriteriaQuery<E> all = cq.select(rootEntry);
		final TypedQuery<E> allQuery = this.getEm().createQuery(all);
		return allQuery.getResultList();
	}

	public E update(final E entity) {
		E result;
		this.em.getTransaction().begin();

		if (entity.isNew()) {
			this.em.persist(entity);
			this.em.flush();
			this.em.refresh(entity);
			result = entity;
		} else {
			result = this.em.merge(entity);
		}

		this.em.getTransaction().commit();

		return result;
	}

}
