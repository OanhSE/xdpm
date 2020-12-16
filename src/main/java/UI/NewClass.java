/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import controller.KhachHangJpaController;
import controller.TaiKhoanJpaController;
import entity.KhachHang;
import entity.TaiKhoan;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author vothi
 */
public class NewClass {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_xaydungphanmem_jar_1.0-SNAPSHOTPU");
        KhachHang kh = new KhachHang("3", "Nghe An", "Oanh Oanh", "079988898", Boolean.TRUE);
        KhachHangJpaController khachHangJpaController = new KhachHangJpaController(emf);
        khachHangJpaController.create(kh);
        
//        TaiKhoan a = new TaiKhoan("Minh");
//        a.setTkMatKhau("123456");
//        TaiKhoanJpaController ac = new TaiKhoanJpaController(emf);
//        ac.create(a);
    }
         
    
}
