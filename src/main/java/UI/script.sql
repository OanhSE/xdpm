CREATE TABLE Loai (loai_Ma VARCHAR(255) NOT NULL, loai_Gia FLOAT(32) NULL, loai_GiaTreNgay FLOAT(32) NULL, loai_TenLoai VARCHAR(255) NULL, PRIMARY KEY (loai_Ma))
CREATE TABLE TieuDe (td_Ma VARCHAR(255) NOT NULL, td_Images VARCHAR(255) NULL, td_Ngay DATETIME NULL, td_NhaSX VARCHAR(255) NULL, td_TenTD VARCHAR(255) NULL, td_TinhTrang BIT default 0 NULL, loai_Ma VARCHAR(255) NULL, PRIMARY KEY (td_Ma))
CREATE TABLE HoaDon (hd_Ma VARCHAR(255) NOT NULL, hd_NgayThue DATETIME NULL, hd_NgayTra DATETIME NULL, hd_SoLuong INTEGER NULL, hd_TinhTrang BIT default 0 NULL, kh_Ma VARCHAR(255) NULL, PRIMARY KEY (hd_Ma))
CREATE TABLE TienPhat (pt_Ma VARCHAR(255) NOT NULL, pt_SoNgay INTEGER NULL, pt_TienPhat FLOAT(32) NULL, PRIMARY KEY (pt_Ma))
CREATE TABLE DVD (dvd_Ma VARCHAR(255) NOT NULL, dvd_TrangThai INTEGER NULL, td_Ma VARCHAR(255) NULL, PRIMARY KEY (dvd_Ma))
CREATE TABLE ChiTietHoaDon (cthd_Ma VARCHAR(255) NOT NULL, dvd_Ma VARCHAR(255) NULL, hd_Ma VARCHAR(255) NULL, pt_Ma VARCHAR(255) NULL, PRIMARY KEY (cthd_Ma))
CREATE TABLE KhachHang (kh_Ma VARCHAR(255) NOT NULL, kh_DiaChi VARCHAR(255) NULL, kh_HoVaTen VARCHAR(255) NULL, kh_SDT VARCHAR(255) NULL, kh_TinhTrang BIT default 0 NULL, PRIMARY KEY (kh_Ma))
CREATE TABLE DatTruoc (dt_Ma VARCHAR(255) NOT NULL, dt_SoLuong INTEGER NULL, dvd_Ma VARCHAR(255) NULL, kh_Ma VARCHAR(255) NULL, PRIMARY KEY (dt_Ma))
CREATE TABLE TaiKhoan (tk_TenTK VARCHAR(255) NOT NULL, tk_MatKhau VARCHAR(255) NULL, PRIMARY KEY (tk_TenTK))
ALTER TABLE TieuDe ADD CONSTRAINT FK_TieuDe_loai_Ma FOREIGN KEY (loai_Ma) REFERENCES Loai (loai_Ma)
ALTER TABLE HoaDon ADD CONSTRAINT FK_HoaDon_kh_Ma FOREIGN KEY (kh_Ma) REFERENCES KhachHang (kh_Ma)
ALTER TABLE DVD ADD CONSTRAINT FK_DVD_td_Ma FOREIGN KEY (td_Ma) REFERENCES TieuDe (td_Ma)
ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_hd_Ma FOREIGN KEY (hd_Ma) REFERENCES HoaDon (hd_Ma)
ALTER TABLE ChiTietHoaDon ADD CONSTRAINT FK_ChiTietHoaDon_pt_Ma FOREIGN KEY (pt_Ma) REFERENCES TienPhat (pt_Ma)
ALTER TABLE ChiTietHoaDon ADD CONSTRAINT ChiTietHoaDon_dvd_Ma FOREIGN KEY (dvd_Ma) REFERENCES DVD (dvd_Ma)
ALTER TABLE DatTruoc ADD CONSTRAINT FK_DatTruoc_dvd_Ma FOREIGN KEY (dvd_Ma) REFERENCES DVD (dvd_Ma)
ALTER TABLE DatTruoc ADD CONSTRAINT FK_DatTruoc_kh_Ma FOREIGN KEY (kh_Ma) REFERENCES KhachHang (kh_Ma)
