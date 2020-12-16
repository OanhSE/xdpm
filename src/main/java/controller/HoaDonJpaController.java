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
import entity.HoaDon;
import entity.KhachHang;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class HoaDonJpaController implements Serializable {

    public HoaDonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HoaDon hoaDon) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd dvdMa = hoaDon.getDvdMa();
            if (dvdMa != null) {
                dvdMa = em.getReference(dvdMa.getClass(), dvdMa.getDvdMa());
                hoaDon.setDvdMa(dvdMa);
            }
            KhachHang khMa = hoaDon.getKhMa();
            if (khMa != null) {
                khMa = em.getReference(khMa.getClass(), khMa.getKhMa());
                hoaDon.setKhMa(khMa);
            }
            em.persist(hoaDon);
            if (dvdMa != null) {
                dvdMa.getHoaDonCollection().add(hoaDon);
                dvdMa = em.merge(dvdMa);
            }
            if (khMa != null) {
                khMa.getHoaDonCollection().add(hoaDon);
                khMa = em.merge(khMa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHoaDon(hoaDon.getHdMa()) != null) {
                throw new PreexistingEntityException("HoaDon " + hoaDon + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HoaDon hoaDon) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HoaDon persistentHoaDon = em.find(HoaDon.class, hoaDon.getHdMa());
            Dvd dvdMaOld = persistentHoaDon.getDvdMa();
            Dvd dvdMaNew = hoaDon.getDvdMa();
            KhachHang khMaOld = persistentHoaDon.getKhMa();
            KhachHang khMaNew = hoaDon.getKhMa();
            if (dvdMaNew != null) {
                dvdMaNew = em.getReference(dvdMaNew.getClass(), dvdMaNew.getDvdMa());
                hoaDon.setDvdMa(dvdMaNew);
            }
            if (khMaNew != null) {
                khMaNew = em.getReference(khMaNew.getClass(), khMaNew.getKhMa());
                hoaDon.setKhMa(khMaNew);
            }
            hoaDon = em.merge(hoaDon);
            if (dvdMaOld != null && !dvdMaOld.equals(dvdMaNew)) {
                dvdMaOld.getHoaDonCollection().remove(hoaDon);
                dvdMaOld = em.merge(dvdMaOld);
            }
            if (dvdMaNew != null && !dvdMaNew.equals(dvdMaOld)) {
                dvdMaNew.getHoaDonCollection().add(hoaDon);
                dvdMaNew = em.merge(dvdMaNew);
            }
            if (khMaOld != null && !khMaOld.equals(khMaNew)) {
                khMaOld.getHoaDonCollection().remove(hoaDon);
                khMaOld = em.merge(khMaOld);
            }
            if (khMaNew != null && !khMaNew.equals(khMaOld)) {
                khMaNew.getHoaDonCollection().add(hoaDon);
                khMaNew = em.merge(khMaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = hoaDon.getHdMa();
                if (findHoaDon(id) == null) {
                    throw new NonexistentEntityException("The hoaDon with id " + id + " no longer exists.");
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
            HoaDon hoaDon;
            try {
                hoaDon = em.getReference(HoaDon.class, id);
                hoaDon.getHdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hoaDon with id " + id + " no longer exists.", enfe);
            }
            Dvd dvdMa = hoaDon.getDvdMa();
            if (dvdMa != null) {
                dvdMa.getHoaDonCollection().remove(hoaDon);
                dvdMa = em.merge(dvdMa);
            }
            KhachHang khMa = hoaDon.getKhMa();
            if (khMa != null) {
                khMa.getHoaDonCollection().remove(hoaDon);
                khMa = em.merge(khMa);
            }
            em.remove(hoaDon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HoaDon> findHoaDonEntities() {
        return findHoaDonEntities(true, -1, -1);
    }

    public List<HoaDon> findHoaDonEntities(int maxResults, int firstResult) {
        return findHoaDonEntities(false, maxResults, firstResult);
    }

    private List<HoaDon> findHoaDonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HoaDon.class));
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

    public HoaDon findHoaDon(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HoaDon.class, id);
        } finally {
            em.close();
        }
    }

    public int getHoaDonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HoaDon> rt = cq.from(HoaDon.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
