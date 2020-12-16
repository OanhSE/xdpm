/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.Dvd;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.TieuDe;
import entity.HoaDon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class DvdJpaController implements Serializable {

    public DvdJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dvd dvd) throws PreexistingEntityException, Exception {
        if (dvd.getHoaDonCollection() == null) {
            dvd.setHoaDonCollection(new ArrayList<HoaDon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TieuDe tdMa = dvd.getTdMa();
            if (tdMa != null) {
                tdMa = em.getReference(tdMa.getClass(), tdMa.getTdMa());
                dvd.setTdMa(tdMa);
            }
            Collection<HoaDon> attachedHoaDonCollection = new ArrayList<HoaDon>();
            for (HoaDon hoaDonCollectionHoaDonToAttach : dvd.getHoaDonCollection()) {
                hoaDonCollectionHoaDonToAttach = em.getReference(hoaDonCollectionHoaDonToAttach.getClass(), hoaDonCollectionHoaDonToAttach.getHdMa());
                attachedHoaDonCollection.add(hoaDonCollectionHoaDonToAttach);
            }
            dvd.setHoaDonCollection(attachedHoaDonCollection);
            em.persist(dvd);
            if (tdMa != null) {
                tdMa.getDvdCollection().add(dvd);
                tdMa = em.merge(tdMa);
            }
            for (HoaDon hoaDonCollectionHoaDon : dvd.getHoaDonCollection()) {
                Dvd oldDvdMaOfHoaDonCollectionHoaDon = hoaDonCollectionHoaDon.getDvdMa();
                hoaDonCollectionHoaDon.setDvdMa(dvd);
                hoaDonCollectionHoaDon = em.merge(hoaDonCollectionHoaDon);
                if (oldDvdMaOfHoaDonCollectionHoaDon != null) {
                    oldDvdMaOfHoaDonCollectionHoaDon.getHoaDonCollection().remove(hoaDonCollectionHoaDon);
                    oldDvdMaOfHoaDonCollectionHoaDon = em.merge(oldDvdMaOfHoaDonCollectionHoaDon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDvd(dvd.getDvdMa()) != null) {
                throw new PreexistingEntityException("Dvd " + dvd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dvd dvd) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd persistentDvd = em.find(Dvd.class, dvd.getDvdMa());
            TieuDe tdMaOld = persistentDvd.getTdMa();
            TieuDe tdMaNew = dvd.getTdMa();
            Collection<HoaDon> hoaDonCollectionOld = persistentDvd.getHoaDonCollection();
            Collection<HoaDon> hoaDonCollectionNew = dvd.getHoaDonCollection();
            if (tdMaNew != null) {
                tdMaNew = em.getReference(tdMaNew.getClass(), tdMaNew.getTdMa());
                dvd.setTdMa(tdMaNew);
            }
            Collection<HoaDon> attachedHoaDonCollectionNew = new ArrayList<HoaDon>();
            for (HoaDon hoaDonCollectionNewHoaDonToAttach : hoaDonCollectionNew) {
                hoaDonCollectionNewHoaDonToAttach = em.getReference(hoaDonCollectionNewHoaDonToAttach.getClass(), hoaDonCollectionNewHoaDonToAttach.getHdMa());
                attachedHoaDonCollectionNew.add(hoaDonCollectionNewHoaDonToAttach);
            }
            hoaDonCollectionNew = attachedHoaDonCollectionNew;
            dvd.setHoaDonCollection(hoaDonCollectionNew);
            dvd = em.merge(dvd);
            if (tdMaOld != null && !tdMaOld.equals(tdMaNew)) {
                tdMaOld.getDvdCollection().remove(dvd);
                tdMaOld = em.merge(tdMaOld);
            }
            if (tdMaNew != null && !tdMaNew.equals(tdMaOld)) {
                tdMaNew.getDvdCollection().add(dvd);
                tdMaNew = em.merge(tdMaNew);
            }
            for (HoaDon hoaDonCollectionOldHoaDon : hoaDonCollectionOld) {
                if (!hoaDonCollectionNew.contains(hoaDonCollectionOldHoaDon)) {
                    hoaDonCollectionOldHoaDon.setDvdMa(null);
                    hoaDonCollectionOldHoaDon = em.merge(hoaDonCollectionOldHoaDon);
                }
            }
            for (HoaDon hoaDonCollectionNewHoaDon : hoaDonCollectionNew) {
                if (!hoaDonCollectionOld.contains(hoaDonCollectionNewHoaDon)) {
                    Dvd oldDvdMaOfHoaDonCollectionNewHoaDon = hoaDonCollectionNewHoaDon.getDvdMa();
                    hoaDonCollectionNewHoaDon.setDvdMa(dvd);
                    hoaDonCollectionNewHoaDon = em.merge(hoaDonCollectionNewHoaDon);
                    if (oldDvdMaOfHoaDonCollectionNewHoaDon != null && !oldDvdMaOfHoaDonCollectionNewHoaDon.equals(dvd)) {
                        oldDvdMaOfHoaDonCollectionNewHoaDon.getHoaDonCollection().remove(hoaDonCollectionNewHoaDon);
                        oldDvdMaOfHoaDonCollectionNewHoaDon = em.merge(oldDvdMaOfHoaDonCollectionNewHoaDon);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dvd.getDvdMa();
                if (findDvd(id) == null) {
                    throw new NonexistentEntityException("The dvd with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd dvd;
            try {
                dvd = em.getReference(Dvd.class, id);
                dvd.getDvdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dvd with id " + id + " no longer exists.", enfe);
            }
            TieuDe tdMa = dvd.getTdMa();
            if (tdMa != null) {
                tdMa.getDvdCollection().remove(dvd);
                tdMa = em.merge(tdMa);
            }
            Collection<HoaDon> hoaDonCollection = dvd.getHoaDonCollection();
            for (HoaDon hoaDonCollectionHoaDon : hoaDonCollection) {
                hoaDonCollectionHoaDon.setDvdMa(null);
                hoaDonCollectionHoaDon = em.merge(hoaDonCollectionHoaDon);
            }
            em.remove(dvd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dvd> findDvdEntities() {
        return findDvdEntities(true, -1, -1);
    }

    public List<Dvd> findDvdEntities(int maxResults, int firstResult) {
        return findDvdEntities(false, maxResults, firstResult);
    }

    private List<Dvd> findDvdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dvd.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Dvd findDvd(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dvd.class, id);
        } finally {
            em.close();
        }
    }

    public int getDvdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dvd> rt = cq.from(Dvd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
