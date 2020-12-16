/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.DatTruoc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.KhachHang;
import entity.TieuDe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class DatTruocJpaController implements Serializable {

    public DatTruocJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatTruoc datTruoc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KhachHang khMa = datTruoc.getKhMa();
            if (khMa != null) {
                khMa = em.getReference(khMa.getClass(), khMa.getKhMa());
                datTruoc.setKhMa(khMa);
            }
            TieuDe tdMa = datTruoc.getTdMa();
            if (tdMa != null) {
                tdMa = em.getReference(tdMa.getClass(), tdMa.getTdMa());
                datTruoc.setTdMa(tdMa);
            }
            em.persist(datTruoc);
            if (khMa != null) {
                khMa.getDatTruocCollection().add(datTruoc);
                khMa = em.merge(khMa);
            }
            if (tdMa != null) {
                tdMa.getDatTruocCollection().add(datTruoc);
                tdMa = em.merge(tdMa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDatTruoc(datTruoc.getDtMa()) != null) {
                throw new PreexistingEntityException("DatTruoc " + datTruoc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatTruoc datTruoc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatTruoc persistentDatTruoc = em.find(DatTruoc.class, datTruoc.getDtMa());
            KhachHang khMaOld = persistentDatTruoc.getKhMa();
            KhachHang khMaNew = datTruoc.getKhMa();
            TieuDe tdMaOld = persistentDatTruoc.getTdMa();
            TieuDe tdMaNew = datTruoc.getTdMa();
            if (khMaNew != null) {
                khMaNew = em.getReference(khMaNew.getClass(), khMaNew.getKhMa());
                datTruoc.setKhMa(khMaNew);
            }
            if (tdMaNew != null) {
                tdMaNew = em.getReference(tdMaNew.getClass(), tdMaNew.getTdMa());
                datTruoc.setTdMa(tdMaNew);
            }
            datTruoc = em.merge(datTruoc);
            if (khMaOld != null && !khMaOld.equals(khMaNew)) {
                khMaOld.getDatTruocCollection().remove(datTruoc);
                khMaOld = em.merge(khMaOld);
            }
            if (khMaNew != null && !khMaNew.equals(khMaOld)) {
                khMaNew.getDatTruocCollection().add(datTruoc);
                khMaNew = em.merge(khMaNew);
            }
            if (tdMaOld != null && !tdMaOld.equals(tdMaNew)) {
                tdMaOld.getDatTruocCollection().remove(datTruoc);
                tdMaOld = em.merge(tdMaOld);
            }
            if (tdMaNew != null && !tdMaNew.equals(tdMaOld)) {
                tdMaNew.getDatTruocCollection().add(datTruoc);
                tdMaNew = em.merge(tdMaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = datTruoc.getDtMa();
                if (findDatTruoc(id) == null) {
                    throw new NonexistentEntityException("The datTruoc with id " + id + " no longer exists.");
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
            DatTruoc datTruoc;
            try {
                datTruoc = em.getReference(DatTruoc.class, id);
                datTruoc.getDtMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datTruoc with id " + id + " no longer exists.", enfe);
            }
            KhachHang khMa = datTruoc.getKhMa();
            if (khMa != null) {
                khMa.getDatTruocCollection().remove(datTruoc);
                khMa = em.merge(khMa);
            }
            TieuDe tdMa = datTruoc.getTdMa();
            if (tdMa != null) {
                tdMa.getDatTruocCollection().remove(datTruoc);
                tdMa = em.merge(tdMa);
            }
            em.remove(datTruoc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatTruoc> findDatTruocEntities() {
        return findDatTruocEntities(true, -1, -1);
    }

    public List<DatTruoc> findDatTruocEntities(int maxResults, int firstResult) {
        return findDatTruocEntities(false, maxResults, firstResult);
    }

    private List<DatTruoc> findDatTruocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatTruoc.class));
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

    public DatTruoc findDatTruoc(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatTruoc.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatTruocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatTruoc> rt = cq.from(DatTruoc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
