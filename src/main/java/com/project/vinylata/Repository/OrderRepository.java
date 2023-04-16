package com.project.vinylata.Repository;

import com.project.vinylata.Model.Order;
import com.project.vinylata.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByOrderStatus(String status);

//    @Query(value = "select DATE_FORMAT(dh.ngayNhanHang, '%m') as month, "
//            + " DATE_FORMAT(dh.ngayNhanHang, '%Y') as year, sum(ct.soLuongNhanHang * ct.donGia) as total "
//            + " from DonHang dh, ChiTietDonHang ct"
//            + " where dh.id = ct.donHang.id and dh.trangThaiDonHang ='Hoàn thành'"
//            + " group by DATE_FORMAT(dh.ngayNhanHang, '%Y%m')"
//            + " order by year asc" )
//    public List<Object> layDonHangTheoThangVaNam();

    public List<Order> findByUser(User user);

    public int countByOrderStatus(String status);
}
