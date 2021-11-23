package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Mapper
public interface PaymentMapper {
    void paymentUpdate(Payment payment);

    void paymentHistoryInsert(PaymentHistory paymentHistory);

    List<PaymentHistory> paymentHistory(Date startHistory, Date endHistory, int idx);
    Optional<Order> itemBuyCheck(int galleryId,int idx);

    Payment userPoint(int idx);
    GalleryPrice priceCheck(int galleryId);
    void itemHistoryInsert(Order order);
    SellerIdxCheck sellerIdxCheck(int galleryId);
    List<Order> idxToGallId(int idx, Date start, Date end);
    Gallery gallCheck(int galleryId);
    void cartInsert(int idx, int galleryId);
    List<Cart> cartCheck(int idx);
    Optional<Cart> cartDup(int idx, int galleryId);
    void cartDelete(int idx, int galleryId);
    int withdrawal(int idx, int profitPoint);
    void withdrawalHistoryInsert(Withdrawal withdrawal);
    List<Withdrawal> withdrawalHistory(Date start, Date end,int idx);
    List<Withdrawal> moneyWithdrawn(int idx);
    List<Order> totalSales(int galleryId);

}