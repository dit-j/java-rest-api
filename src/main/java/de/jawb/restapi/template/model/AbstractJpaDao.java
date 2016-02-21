/**
 * 
 */
package de.jawb.restapi.template.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dit
 * @formatter:off
 * @param <T>
 */
public abstract class AbstractJpaDao<T> {
    
    protected Logger _logger = LoggerFactory.getLogger(this.getClass());
                             
    private Class<T> _entityClass;
                     
    @PersistenceContext
    EntityManager    _em;
                     
    public final void setClazz( Class< T > clazzToSet ){
        this._entityClass = clazzToSet;
     }
    
    /**
     * Gibt ein Objekt fuer eine bestimmte ID zurueck.
     * 
     * @param id
     *            ID des Objekts
     * @return T
     */
    public T getByID(Serializable id) {
        T t = (T) _em.find(_entityClass, id);
        if (t == null) throw new IllegalArgumentException("no entity found for id " + id);
        return t;
    }
    
    public boolean isExists(Serializable id) {
        Query query = _em.createQuery("select 1 from " + _entityClass.getName() + " t where t.id = :id");
        query.setParameter("id", id);
        return !query.getResultList().isEmpty();
    }
    
    /**
     * Speichert ein Objekt in der DB
     * 
     * @param object
     *            Objekt des Typs {@link Entity}
     */
    public void create(T entity) {
        _em.persist(entity);
    }
    
    /**
     * Entfernt ein Objekt aus der DB.
     * 
     * @param object
     *            Objekt das entfernt werden soll.
     * @throws DBException
     *             DB-Fehler...
     */
    public void delete(T object) {
        _em.remove(object);
    }
    
    protected void defaultMerge(T o) {
        _em.merge(o);
    }
    
    public void deleteById(Serializable id) {
        _em.remove(getByID(id));
    }
    
    public int deleteByIds(String ids) {
        
        Query query = _em.createNativeQuery("DELETE FROM " + _entityClass.getName() + " WHERE ID IN (:ids);");
        query.setParameter("ids", ids);
        
        return query.executeUpdate();
    }
    
    /**
     * Gibt eine Liste eines bestimmten Typs zurueck.
     * 
     * @param clazz
     *            Typ des Objekts (Tabelle)
     * @param criterions
     *            Liste mit Einschraenkungen. Darf auch
     *            <code>null</code> sein. In dem Fall werden
     *            <b>alle</b> Objekte der Tabelle zurueck
     *            gegeben.
     * @return Liste mit Objekten
     */
    protected List<T> getList(PredicateBuilder<T> predicateBuilder) {
        return getList(predicateBuilder, (Order[]) null);
    }
    
    /**
     * @param orders
     * @return
     */
    protected List<T> getAllOrdered(Order... orders) {
        return getList(null, orders);
    }
    
    /**
     * Gibt eine Liste eines bestimmten Typs zurueck.
     * 
     * @param clazz
     *            Typ des Objekts (Tabelle)
     * @param criterions
     *            Liste mit Einschraenkungen. Darf auch
     *            <code>null</code> sein. In dem Fall werden
     *            <b>alle</b> Objekte der Tabelle zurueck
     *            gegeben.
     * @return Liste mit Objekten
     */
    protected List<T> getList(PredicateBuilder<T> predicateBuilder, Order... orders) {
        
        CriteriaBuilder builder = _em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(_entityClass);
        Root<T> root = criteria.from(_entityClass);
        
        criteria.select(root);
        
        Predicate[] where = predicateBuilder.createWhere(builder, root);
        
        if (where != null) {
            criteria.where(where);
        }
        
        if (orders != null) {
            criteria.orderBy(orders);
        }
        
        return _em.createQuery(criteria).getResultList();
    }
    
    protected T getUniqueResult(PredicateBuilder<T> predicateBuilder) {
        
        CriteriaBuilder builder = _em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(_entityClass);
        Root<T> root = criteria.from(_entityClass);
        
        criteria.select(root);
        
        Predicate[] where = predicateBuilder.createWhere(builder, root);
        
        if (where != null) {
            criteria.where(where);
        }
        
        return _em.createQuery(criteria).getSingleResult();
    }
    
    /**
     * @author dit
     * @param <T>
     */
    public interface PredicateBuilder<T> {
        
        /**
         * @param root
         * @param criteria
         * @return
         */
        Predicate[] createWhere(CriteriaBuilder criteriaBuilder, Root<T> root);
    }
}
