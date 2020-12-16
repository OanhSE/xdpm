/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Dvd;
import java.util.ArrayList;
import java.util.Collection;
import entity.DatTruoc;
import entity.TieuDe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class TieuDeJpaController implements Serializable {

    public TieuDeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TieuDe tieuDe) throws PreexistingEntityException, Exception {
        if (tieuDe.getDvdCollection() == null) {
            tieuDe.setDvdCollection(new ArrayList<Dvd>());
        }
        if (tieuDe.getDatTruocCollection() == null) {
            tieuDe.setDatTruocCollection(new ArrayList<DatTruoc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Dvd> attachedDvdCollection = new ArrayList<Dvd>();
            for (Dvd dvdCollectionDvdToAttach : tieuDe.getDvdCollection()) {
                dvdCollectionDvdToAttach = em.getReference(dvdCollectionDvdToAttach.getClass(), dvdCollectionDvdToAttach.getDvdMa());
                attachedDvdCollection.add(dvdCollectionDvdToAttach);
            }
            tieuDe.setDvdCollection(attachedDvdCollection);
            Collection<DatTruoc> attachedDatTruocCollection = new ArrayList<DatTruoc>();
            for (DatTruoc datTruocCollectionDatTruocToAttach : tieuDe.getDatTruocCollection()) {
                datTruocCollectionDatTruocToAttach = em.getReference(datTruocCollectionDatTruocToAttach.getClass(), datTruocCollectionDatTruocToAttach.getDtMa());
                attachedDatTruocCollection.add(datTruocCollectionDatTruocToAttach);
            }
            tieuDe.setDatTruocCollection(attachedDatTruocCollection);
            em.persist(tieuDe);
            for (Dvd dvdCollectionDvd : tieuDe.getDvdCollection()) {
                TieuDe oldTdMaOfDvdCollectionDvd = dvdCollectionDvd.getTdMa();
                dvdCollectionDvd.setTdMa(tieuDe);
                dvdCollectionDvd = em.merge(dvdCollectionDvd);
                if (oldTdMaOfDvdCollectionDvd != null) {
                    oldTdMaOfDvdCollectionDvd.getDvdCollection().remove(dvdCollectionDvd);
                    oldTdMaOfDvdCollectionDvd = em.merge(oldTdMaOfDvdCollectionDvd);
                }
            }
            for (DatTruoc datTruocCollectionDatTruoc : tieuDe.getDatTruocCollection()) {
                TieuDe oldTdMaOfDatTruocCollectionDatTruoc = datTruocCollectionDatTruoc.getTdMa();
                datTruocCollectionDatTruoc.setTdMa(tieuDe);
                datTruocCollectionDatTruoc = em.merge(datTruocCollectionDatTruoc);
                if (oldTdMaOfDatTruocCollectionDatTruoc != null) {
                    oldTdMaOfDatTruocCollectionDatTruoc.getDatTruocCollection().remove(datTruocCollectionDatTruoc);
                    oldTdMaOfDatTruocCollectionDatTruoc = em.merge(oldTdMaOfDatTruocCollectionDatTruoc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTieuDe(tieuDe.getTdMa()) != null) {
                throw new PreexistingEntityException("TieuDe " + tieuDe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TieuDe tieuDe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TieuDe persistentTieuDe = em.find(TieuDe.class, tieuDe.getTdMa());
            Collection<Dvd> dvdCollectionOld = persistentTieuDe.getDvdCollection();
            Collection<Dvd> dvdCollectionNew = tieuDe.getDvdCollection();
            Collection<DatTruoc> datTruocCollectionOld = persistentTieuDe.getDatTruocCollection();
            Collection<DatTruoc> datTruocCollectionNew = tieuDe.getDatTruocCollection();
            Collection<Dvd> attachedDvdCollectionNew = new ArrayList<Dvd>();
            for (Dvd dvdCollectionNewDvdToAttach : dvdCollectionNew) {
                dvdCollectionNewDvdToAttach = em.getReference(dvdCollectionNewDvdToAttach.getClass(), dvdCollectionNewDvdToAttach.getDvdMa());
                attachedDvdCollectionNew.add(dvdCollectionNewDvdToAttach);
            }
            dvdCollectionNew = attachedDvdCollectionNew;
            tieuDe.setDvdCollection(dvdCollectionNew);
            Collection<DatTruoc> attachedDatTruocCollectionNew = new ArrayList<DatTruoc>();
            for (DatTruoc datTruocCollectionNewDatTruocToAttach : datTruocCollectionNew) {
                datTruocCollectionNewDatTruocToAttach = em.getReference(datTruocCollectionNewDatTruocToAttach.getClass(), datTruocCollectionNewDatTruocToAttach.getDtMa());
                attachedDatTruocCollectionNew.add(datTruocCollectionNewDatTruocToAttach);
            }
            datTruocCollectionNew = attachedDatTruocCollectionNew;
            tieuDe.setDatTruocCollection(datTruocCollectionNew);
            tieuDe = em.merge(tieuDe);
            for (Dvd dvdCollectionOldDvd : dvdCollectionOld) {
                if (!dvdCollectionNew.contains(dvdCollectionOldDvd)) {
                    dvdCollectionOldDvd.setTdMa(null);
                    dvdCollectionOldDvd = em.merge(dvdCollectionOldDvd);
                }
            }
            for (Dvd dvdCollectionNewDvd : dvdCollectionNew) {
                if (!dvdCollectionOld.contains(dvdCollectionNewDvd)) {
                    TieuDe oldTdMaOfDvdCollectionNewDvd = dvdCollectionNewDvd.getTdMa();
                    dvdCollectionNewDvd.setTdMa(tieuDe);
                    dvdCollectionNewDvd = em.merge(dvdCollectionNewDvd);
                    if (oldTdMaOfDvdCollectionNewDvd != null && !oldTdMaOfDvdCollectionNewDvd.equals(tieuDe)) {
                        oldTdMaOfDvdCollectionNewDvd.getDvdCollection().remove(dvdCollectionNewDvd);
                        oldTdMaOfDvdCollectionNewDvd = em.merge(oldTdMaOfDvdCollectionNewDvd);
                    }
                }
            }
            for (DatTruoc datTruocCollectionOldDatTruoc : datTruocCollectionOld) {
                if (!datTruocCollectionNew.contains(datTruocCollectionOldDatTruoc)) {
                    datTruocCollectionOldDatTruoc.setTdMa(null);
                    datTruocCollectionOldDatTruoc = em.merge(datTruocCollectionOldDatTruoc);
                }
            }
            for (DatTruoc datTruocCollectionNewDatTruoc : datTruocCollectionNew) {
                if (!datTruocCollectionOld.contains(datTruocCollectionNewDatTruoc)) {
                    TieuDe oldTdMaOfDatTruocCollectionNewDatTruoc = datTruocCollectionNewDatTruoc.getTdMa();
                    datTruocCollectionNewDatTruoc.setTdMa(tieuDe);
                    datTruocCollectionNewDatTruoc = em.merge(datTruocCollectionNewDatTruoc);
                    if (oldTdMaOfDatTruocCollectionNewDatTruoc != null && !oldTdMaOfDatTruocCollectionNewDatTruoc.equals(tieuDe)) {
                        oldTdMaOfDatTruocCollectionNewDatTruoc.getDatTruocCollection().remove(datTruocCollectionNewDatTruoc);
                        oldTdMaOfDatTruocCollectionNewDatTruoc = em.merge(oldTdMaOfDatTruocCollectionNewDatTruoc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tieuDe.getTdMa();
                if (findTieuDe(id) == null) {
                    throw new NonexistentEntityException("The tieuDe with id " + id + " no longer exists.");
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
            TieuDe tieuDe;
            try {
                tieuDe = em.getReference(TieuDe.class, id);
                tieuDe.getTdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tieuDe with id " + id + " no longer exists.", enfe);
            }
            Collection<Dvd> dvdCollection = tieuDe.getDvdCollection();
            for (Dvd dvdCollectionDvd : dvdCollection) {
                dvdCollectionDvd.setTdMa(null);
                dvdCollectionDvd = em.merge(dvdCollectionDvd);
            }
            Collection<DatTruoc> datTruocCollection = tieuDe.getDatTruocCollection();
            for (DatTruoc datTruocCollectionDatTruoc : datTruocCollection) {
                datTruocCollectionDatTruoc.setTdMa(null);
                datTruocCollectionDatTruoc = em.merge(datTruocCollectionDatTruoc);
            }
            em.remove(tieuDe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TieuDe> findTieuDeEntities() {
        return findTieuDeEntities(true, -1, -1);
    }

    public List<TieuDe> findTieuDeEntities(int maxResults, int firstResult) {
        return findTieuDeEntities(false, maxResults, firstResult);
    }

    private List<TieuDe> findTieuDeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TieuDe.class));
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

    public TieuDe findTieuDe(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TieuDe.class, id);
        } finally {
            em.close();
        }
    }

    public int getTieuDeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TieuDe> rt = cq.from(TieuDe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
     public List<TieuDe> findTieuDebyName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("TieuDe.findByTdTenTD", TieuDe.class).setParameter("tdTenTD", name).getResultList();
        } finally {
            em.close();
        }
    }
      public List<Dvd> findByDvdTrangThaiAndTieuDe(Integer tt, String td){
         EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Dvd d WHERE d.dvdTrangThai = " + tt + " AND d.tdMa.tdTenTD = " + td).getResultList();
        } finally {
            em.close();
        }
    }
    
}
