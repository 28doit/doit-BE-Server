package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.Payment;
import com.example.photologger.photo.domain.PaymentHistory;
import org.apache.ibatis.annotations.Mapper;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaymentMapper {
    void paymentUpdate(Payment payment);

    void paymentHistoryInsert(PaymentHistory paymentHistory);

    List<PaymentHistory> paymentHistory(Date startHistory, Date endHistory, String email);

    int itemSelect(String itemIdx);

    void itemHistoryinsert();
}