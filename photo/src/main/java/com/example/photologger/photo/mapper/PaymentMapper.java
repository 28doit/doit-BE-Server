package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Payment;
import com.example.photologger.photo.domain.PaymentHistory;
import org.apache.ibatis.annotations.Mapper;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaymentMapper {
    void payment_update(Payment payment);

    void payment_History_Insert(PaymentHistory paymentHistory);

    List<PaymentHistory> payment_History(Date start_history, Date end_history, String email);

    int item_select(String itemIdx);

    void item_History_insert()
}