package com.example.photologger.photo.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

import com.example.photologger.photo.domain.*;
import com.example.photologger.photo.mapper.AccountsMapper;
import com.example.photologger.photo.mapper.PaymentMapper;
import com.example.photologger.photo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.photologger.photo.constants.paymentConstant.*;

@Slf4j
@Service
public class PaymentSerivce {
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    AccountsMapper accountsMapper;
    @Autowired
    AccountsService accountsService;
    @Autowired
    UserMapper userMapper;
    //가맹점 식별코드 imp89552474
    // 아임포트 인증(토큰)을 받아주는 함수
    public String getImportToken() {
        String result = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_TOKEN_URL);
        Map<String, String> m = new HashMap<String, String>();
        m.put(imp_key, KEY);
        m.put(imp_secret, SECRET);
        try {
            post.setEntity(new UrlEncodedFormEntity(convertParameter(m)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String body = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(body);
            JsonNode resNode = rootNode.get("response");
            result = resNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    } // Map을 사용해서 Http요청 파라미터를 만들어 주는 함수

    private List<NameValuePair> convertParameter(Map<String, String> paramMap) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Set<Entry<String, String>> entries = paramMap.entrySet();
        for (Entry<String, String> entry : entries) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return paramList;
    }

    // 결제취소
    public int cancelPayment(String token, String mid) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_CANCEL_URL);
        Map<String, String> map = new HashMap<String, String>();
        post.setHeader("Authorization", token);
        map.put("merchant_uid", mid);
        String asd = "";
        try {
            post.setEntity(new UrlEncodedFormEntity(convertParameter(map)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String enty = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(enty);
            asd = rootNode.get("response").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (asd.equals("null")) {
            System.err.println("환불실패");
            return -1;
        } else {
            System.err.println("환불성공");
            return 1;
        }
    }

    // 아임포트 결제정보를 조회해서 결제금액을 뽑아주는 함수
    public String getAmount(String token, String mId) {
        String amount = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(IMPORT_PAYMENTINFO_URL + mId + "/paid");
        get.setHeader("Authorization", token);
        try {
            HttpResponse res = client.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            String body = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(body);
            log.info(rootNode.toString());
            JsonNode resNode = rootNode.get("response");
            log.info(resNode.toString());
            amount = resNode.get("amount").asText();
            log.info(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    } // 아임포트 결제금액 변조는 방지하는 함수
    public boolean setHackCheck(String amount, String mId, String token) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_PREPARE_URL);
        Map<String, String> m = new HashMap<String, String>();
        post.setHeader("Authorization", token);
        m.put("amount", amount);
        m.put("merchant_uid", mId);
        try {
            post.setEntity(new UrlEncodedFormEntity(convertParameter(m)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String body = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(body);
            log.info(rootNode.toString());
            if(rootNode.findValue("code").toString().replaceAll("\"","").equals("1"))
            {
                return false;  //false 로 바꿔라
            }
            else
            {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;// 사실상 사용하지않음.
    }
    public Object setDB(String token, String mId)
    {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(IMPORT_PAYMENTINFO_URL + mId + "/paid");
        get.setHeader("Authorization", token);
        JsonNode rootNode;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpResponse res = client.execute(get);

            String body = EntityUtils.toString(res.getEntity());
            rootNode = mapper.readTree(body);;
            return rootNode;
            }catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }

    }
    public boolean check(Map <String,String>mid)
    {
        boolean trueAndfalse;
        int idx= accountsMapper.findEmail(mid.get("login_email")).get().getIdx();
        String userName= accountsMapper.findEmail(mid.get("login_email")).get().getName();
        log.info("test:"+accountsService.token_Expiration(mid.get("token"),mid.get("login_email")).get("Token").toString());
        String iAmToken = getImportToken();
        JsonNode tmp = (JsonNode) setDB(iAmToken,mid.get("mid"));
        String buyerName = tmp.findValue("buyer_name").toString().replaceAll("\"", "");
        log.info(buyerName);
        log.info(mid.get("login_email"));
        if ((boolean)accountsService.token_Expiration(mid.get("token"),mid.get("login_email")).get("Token")&&userName.equals(buyerName))
        {
            int pay = Integer.parseInt(getAmount(iAmToken,mid.get("mid")));

            log.info("결제를 시도한 사람의 이름은 : " + mid.get("name"));
            log.info(mid.get("mid"));
            log.info(iAmToken);
            trueAndfalse = setHackCheck(
                    Integer.toString(pay)
                    , mid.get("mid")
                    , iAmToken
            );
            if(trueAndfalse) {
                //DB갱신
                log.info("결제 금액 : "+Integer.toString(pay));
                Payment payment = Payment.builder()
                        .idx(idx)
                        .buyPoint(pay)
                        .build();
                log.info(payment.toString());
                paymentMapper.paymentUpdate(payment);
                Date date = new Date();
                log.info(tmp.findValue(PAID_AT).toString());
                date.setTime(Long.parseLong(tmp.findValue(PAID_AT).toString())*1000);
                log.info(tmp.findValue(BUYER_EMAIL).toString());
                log.info(date.toString());
                log.info(tmp.findValue(MERCHANT_UID).toString().replaceAll("\"",""));
                PaymentHistory paymentHistory = PaymentHistory.builder()
                        .idx(idx)
                        .pay(pay)
                        .payCard(card_Code(tmp.findValue(PAY_CARD).toString().replaceAll("\"", "")))
                        .mId(tmp.findValue(MERCHANT_UID).toString().replaceAll("\"",""))
                        .time(date)
                        .build();
                log.info(paymentHistory.toString());
                try{
                    paymentMapper.paymentHistoryInsert(paymentHistory);
                    return trueAndfalse;
                }catch (Exception e)
                {
                    log.info("이미 결제된 사용자입니다.");
                    return trueAndfalse=false;
                }


            }
            else {
                log.info("이미 결제된 사용자입니다.");
                return trueAndfalse;
            }

        }
        else
        {
            log.info("결제자와 로그인 중 사용자가 다릅니다.");  //debug
            return false;
        }
    }
    public Object withdrawal(String token,String email,int pay)
    {
        int idx;

        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.ENGLISH);     //결재완료후 뛰어줄 date
        LocalDateTime now = LocalDateTime.now();
        Date date = Timestamp.valueOf(now);
        log.info(date.toString());
        if(userCheck(token,email))
        {
            try
            {
                idx =accountsMapper.findEmail(email).get().getIdx();
                int profitPoint=paymentMapper.userPoint(idx).getProfitPoint();
                if(profitPoint<pay)
                {
                    log.info("잔여금액이 부족합니다.");
                    return false;
                }
                paymentMapper.withdrawal(idx,pay);
                log.info(pay +"원 인출되었습니다");
                Withdrawal withdrawal = Withdrawal.builder()
                                .date(date)
                                .idx(idx)
                                .withdrawalNumber((Integer.toString(idx))+"_"+outputFormat.format(date))
                                .point(pay)
                                .build();
                paymentMapper.withdrawalHistoryInsert(withdrawal);
                return true;
            }catch (Exception e)
            {
                log.info(e.getMessage());
                return false;
            }
        }
        log.info("유저 인증 실패");
        return false;
    }
    public Object withdrawalHistory(String email, String token,String start, String end)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);  //get으로 가져올 date
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:SS", Locale.ENGLISH);  //get으로 가져올 date
        List<ReturnWithdrawal> output = new ArrayList<>();
        int idx = accountsMapper.findEmail(email).get().getIdx();
        try
        {

            if(userCheck(token,email))
            {

                Date start_history = inputFormat.parse(start);  //시작날짜 inputFormat형식으로 변경
                Date end_history = inputFormat.parse(end);      //종료날짜 inputFormat형식으로 변경
                log.info("로그인중 토큰정보 : " +token);
                log.info("결제내역 시작 : " + start_history.toString());
                log.info("결제내역 끝 : "+ end_history.toString());
                List<Withdrawal> tmp =  paymentMapper.withdrawalHistory(start_history,end_history,idx);
                for (int i =0;i<tmp.size();i++)
                {
                    ReturnWithdrawal returnWithdrawal = ReturnWithdrawal.builder()
                            .withdrawalNumber(tmp.get(i).getWithdrawalNumber())
                            .idx(tmp.get(i).getIdx())
                            .date(outputFormat.format(tmp.get(i).getDate()))
                            .point(tmp.get(i).getPoint())
                            .build();
                    output.add(i,returnWithdrawal);
                }
                return output;
            }
        }catch (Exception e)
        {
            log.info(e.getMessage());
            log.info("조회요청 실패");
            return false;
        }
        log.info("유저인증 실패");
        return false;
    }
    public Object history(String token, String start, String end)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);  //get으로 가져올 date
        SimpleDateFormat rootFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH); //DB저장되있는 date
        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);     //변환해줄 date
        int idx;
        try {
            Date start_history = inputFormat.parse(start);  //시작날짜 inputFormat형식으로 변경
            Date end_history = inputFormat.parse(end);      //종료날짜 inputFormat형식으로 변경
            log.info("로그인중 토큰정보 : " +token);
            log.info("결제내역 시작 : " + start_history.toString());
            log.info("결제내역 끝 : "+ end_history.toString());

            if (accountsService.login_check(token)) //로그인 체크
            {
                log.info(token+start_history+end_history);
                try {
                    String email= accountsService.getUserPk(token);     //본인확인용 이메일
                    log.info(email);
                    List<PaymentHistory> history = paymentMapper.paymentHistory(start_history, end_history,idx = accountsMapper.findEmail(email).get().getIdx()); //db조회
                    if(userMapper.findOne(idx).getEmail().equals(email))        //token에서나온 이메일과 db조회 이메일이 같은경우
                    {
                        log.info(Integer.toString(history.size()));
                        List<ReturnHistory> returnHistory = new ArrayList();    //date형식 지정 반환을 위해 새로생성
                        for(int i=0;i<history.size();i++)   //사이즈만큼
                        {
                            try {
                                Date data = rootFormat.parse(history.get(i).getTime().toString());  //rootFormat형식을 받아서..
                                String timeTmp = outputFormat.format(data);
                                ReturnHistory tmp = ReturnHistory.builder()
                                        .idx(history.get(i).getIdx())
                                        .pay(history.get(i).getPay())
                                        .payCard(history.get(i).getPayCard())
                                        .time(timeTmp)
                                        .mId(history.get(i).getMId())
                                        .build();
                                log.info(tmp.toString());
                                returnHistory.add(tmp);
                            } catch (Exception e) {
                                log.info("날짜변환 과정중 오류가 있습니다.");
                            }
                        }
                        log.info(history.toString());

                        return returnHistory;

                    }
                }catch (Exception e)
                {
                    log.info(e.toString());
                    log.info("조회 할 자료가 없습니다");

                }
            }
            else        //토큰인증이 되지않은경우
            {
                log.info("올바른 토큰정보가 아닙니다.");
                return false;
            }
        }catch (Exception e)
        {
            log.info(e.toString());
            log.info("제대로 된 값을 입력하지 않았습니다.");
            return false;
        }
        ReturnHistory returnHistory = ReturnHistory.builder()   //초기화 되지않은 반환값을위해 미리세팅
                .payCard("")
                .time("")
                .idx(0)
                .pay(0)
                .mId("")
                .build();
        return returnHistory;
    }
    public Object itemBuy(int galleryId, String token)
    {
        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.ENGLISH);     //결재완료후 뛰어줄 date
        LocalDateTime now = LocalDateTime.now();
        Date date = Timestamp.valueOf(now);
        log.info(date.toString());
        String dateOut =outputFormat.format(date);
        log.info(dateOut);
        //1번 구매금액만큼 포인트 소비후 잔여포인트 갱신(반환)하기
        try {
            String email = accountsService.getUserPk(token);
            User user = accountsMapper.findEmail(email)
                    .orElseThrow(() -> new Exception("잘못된 이메일입니다."));
            log.info(user.getIdx()+"님이 로그인 중입니다.");

            try {
                if(paymentMapper.itemBuyCheck(galleryId,user.getIdx()).get().getIdx()==user.getIdx())
                {
                    //존재하면 이어질것임으로
                    return false;
                }

            }
            catch(Throwable e) {
                log.info(e.getMessage());
            }
            GalleryPrice tmp = paymentMapper.priceCheck(galleryId);
            int sellPoint = tmp.getPrice();
            log.info(sellPoint + " 원 결제시도");
            Payment buyer = paymentMapper.userPoint(user.getIdx());
            log.info(buyer.toString());
            if (buyer.getTotalPoint() < sellPoint) {
                String returnCheck = Integer.toString(buyer.getTotalPoint() - sellPoint) + " 원 모자랍니다. 충전해주세요.";
                return returnCheck;
            }
            log.info(buyer.toString());
            itemBuyCheck(buyer,sellPoint);
            log.info("test");
            paymentMapper.paymentUpdate(buyer);  //위에 리턴을 통과안햇다는건 결제시도를 해도된다는뜻임으로 강제로payment타입으로 변환해준다.
            Order order = Order.builder()   //구매내역
                    .idx(user.getIdx())
                    .date(date)
                    .orderNumber(user.getIdx() + "_" + dateOut + "_" + galleryId)
                    .galleryId(galleryId)
                    .build();
            log.info(order.getOrderNumber());
            paymentMapper.itemHistoryInsert(order);
            log.info("test");
            //상품등록자에게 구매금액 지급
            log.info(paymentMapper.sellerIdxCheck(galleryId).toString());
            Payment seller = Payment.builder()
                    .idx(paymentMapper.sellerIdxCheck(galleryId).getIdx())        //수정해야합니다.
                    .profitPoint(sellPoint)
                    .build();
            log.info("test");
            buyer = paymentMapper.userPoint(user.getIdx());
            paymentMapper.paymentUpdate(seller);
            log.info("test");
            return Integer.toString(buyer.getTotalPoint());
        }catch (Exception e)
        {
            //test해야합니다.
            log.info(e.toString());
            return false;
        }
        //고려해야할것 3번 쿠폰
    }

    public Payment itemBuyCheck(Payment payment,int sellPoint)   //이 함수를 실행시켰다는것은 total-sell을 통과했다는거 따라서 세부조항만 계산하면됨.
    {
        int tmp;    //임시로 사용할 int
            if (sellPoint-payment.getFreePoint() <= 0 )  //잔여결제금액이 0보다크거나같으면
            {
                payment.setFreePoint(-sellPoint);  //setfreePoint를 설정
                payment.setBuyPoint(0);
                payment.setProfitPoint(0);
                payment.setSellPoint(sellPoint);
                return payment; //payment 다시반환
            }
            tmp = (payment.getFreePoint() - sellPoint)*-1;
            log.info("현재 결제포인트 : "+payment.getBuyPoint()+ "판매 포인트 : " + sellPoint + "남은 금액 : "+ Integer.toString(tmp));

            if(tmp-payment.getBuyPoint()<=0)      //잔여결제금액이 0보다 크거나같으면
            {
                payment.setFreePoint(0);    //freePoint = 0
                payment.setBuyPoint(-tmp);   //bouPoint = tmp로 저장
                payment.setProfitPoint(0);
                payment.setSellPoint(sellPoint);
                return payment;             //결제금액 반환
            }
            tmp = payment.getFreePoint()+ payment.getBuyPoint() - sellPoint;
            log.info("현재 수익포인트 : "+payment.getBuyPoint()+ "판매 포인트 : " + sellPoint + "남은 금액 : "+ Integer.toString(tmp));
            payment.setFreePoint(0);    //무료포인트 0
            payment.setBuyPoint(0);     //충전포인트 0
            payment.setProfitPoint(tmp);    //수익포인트 - rtmp
            payment.setSellPoint(sellPoint);
            return payment; //결제금액 반환
    }
    public Object itemHistoryCheck(String token,String start, String end)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);  //get으로 가져올 date
        SimpleDateFormat rootFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH); //DB저장되있는 date
        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try{
            Date start_history = inputFormat.parse(start);  //시작날짜 inputFormat형식으로 변경
            Date end_history = inputFormat.parse(end);      //종료날짜 inputFormat형식으로 변경
            log.info("로그인중 토큰정보 : " + token);
            log.info("결제내역 시작 : " + start_history.toString());
            log.info("결제내역 끝 : " + end_history.toString());

        if (accountsService.login_check(token)) //로그인 체크
        {
            log.info(token+start+end);
            try {
                String email= accountsService.getUserPk(token);     //본인확인용 이메일
                int idx= accountsMapper.findEmail(email).get().getIdx();
                log.info(email);
                List<Order> galleryId = paymentMapper.idxToGallId(idx,start_history, end_history); //db조회
                if(userMapper.findOne(galleryId.get(0).getIdx()).getEmail().equals(email))        //token에서나온 이메일과 db조회 이메일이 같은경우
                {
                    log.info(Integer.toString(galleryId.size()));
                    List<ReturnGalleryHistory> returngalleryHistory = new ArrayList();    //date형식 지정 반환을 위해 새로생성
                    log.info(Integer.toString(galleryId.get(0).getGalleryId()));
                    for(int i=0;i<galleryId.size();i++)   //사이즈만큼
                    {
                        try {
                            Date data = rootFormat.parse(galleryId.get(i).getDate().toString());  //rootFormat형식을 받아서..
                            String timeTmp = outputFormat.format(data);
                            log.info(timeTmp);
                            int tmpGallId = galleryId.get(i).getGalleryId();
                            log.info(Integer.toString(tmpGallId));

                            Gallery gallTmp =paymentMapper.gallCheck(tmpGallId);
                            log.info("galltmp : "+gallTmp.toString());
                            ReturnGalleryHistory History = ReturnGalleryHistory.builder()
                                            .galleryId(tmpGallId)
                                            .galleryName(gallTmp.getGalleryName())
                                            .galleryImageLocation(gallTmp.getGalleryImageLocation())
                                            .order_number(galleryId.get(i).getOrderNumber())
                                            .date(timeTmp)
                                            .seller_Name(userMapper.findOne(paymentMapper.gallCheck(tmpGallId).getIdx()).getNickName())
                                            .build();

                            log.info(History.getDate() + " "+History.getGalleryName()+ " "+History.getGalleryId()+ " "+History.getGalleryImageLocation()+ " "+History.getOrder_number());
                            returngalleryHistory.add(History);
                            log.info(returngalleryHistory.toString());
                        }catch (Exception e) {
                                log.info(e.toString());
                        }
                    }
                    log.info(galleryId.toString());
                    return returngalleryHistory;

                    }
                }catch (Exception e)
                {
                    log.info(e.toString());
                    log.info("조회 할 자료가 없습니다");

                }
            }
            else        //토큰인증이 되지않은경우
            {
                log.info("올바른 토큰정보가 아닙니다.");
                return false;
            }
        }catch (Exception e)
        {
            log.info(e.toString());
            log.info("제대로 된 값을 입력하지 않았습니다.");
            return false;
        }
        return false;
    }
    public Object cartCheck(String token,String email)
    {
        log.info(token+ " " + email);
        List outPut = new ArrayList();
        int gallidx;

        if(userCheck(token,email))
        {
            log.info("user체크 통과");
            try {
               List <Cart> cart = paymentMapper.cartCheck(accountsMapper.findEmail(email).get().getIdx());
               log.info("test");
               for(int i =0;i<cart.size();i++)
               {
                   HashMap<String,Object> outPut_in = new HashMap<>();
                   Gallery tmp  =paymentMapper.gallCheck(cart.get(i).getGalleryId());
                   outPut_in.put("gallery_location",tmp.getGalleryImageLocation());
                   outPut_in.put("gallery_name",tmp.getGalleryName());
                   outPut_in.put("price",paymentMapper.priceCheck(tmp.getGalleryId()).getPrice());
                   outPut_in.put("gallery_id",tmp.getGalleryId());
                   outPut_in.put("seller_Name",userMapper.findOne(paymentMapper.gallCheck(tmp.getGalleryId()).getIdx()).getNickName());
                   outPut.add(i,outPut_in);
                   log.info(outPut.toString());
               }
               log.info(outPut.toString());
               return outPut;
            }catch (Exception e)
            {
                log.info(e.toString());

            }
            log.info("상품 조회 실패");
            return false;
        }
        log.info("유저 인증 실패");
        return false;
    }
    public Object cartInsert(Map<String,Object> tmp)
    {
        String token = tmp.get("token").toString();
        String email = tmp.get("email").toString();
        int gallery_id = Integer.parseInt(tmp.get("gallery_id").toString());
        if(userCheck(token,email))
        {

            int idx = 0;
            try {
                idx = accountsMapper.findEmail(email).get().getIdx();

               if(paymentMapper.itemBuyCheck(gallery_id,idx).get().getIdx()==idx)
               {
                   log.info("이미 구매내역에 존재합니다.");
                   return false;
               }
                log.info(Integer.toString(idx));
                paymentMapper.cartDup(idx,gallery_id)
                        .orElseThrow(() -> new IllegalArgumentException("장바구니 담기 가는 상태"));
                log.info("장바구니 담기 불가능 상태");
                return false;
            }catch (Exception e)
            {
                log.info(e.getMessage());
                try {
                    paymentMapper.cartInsert(idx, gallery_id);
                    log.info(gallery_id+"의 물건이 장바구니에 담겼습니다.");
                    return true;
                }
                catch (Exception e2) {
                    log.info(e2.toString());
                    log.info("예상치못한 에러 발생");
                }
            }

            log.info("장바구니에 담지 못하였습니다");
            return false;
        }
        log.info("유저 인증 실패");
        return false;
    }
    public Object cartBuy(String json)
    {
        log.info(json);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode data = mapper.readTree(json);
            String email = data.get("user_email").toString().replace("\"","");
            String token = data.get("token").toString().replace("\"","");
            int price = Integer.parseInt(data.get("pay").toString());
            String galleryId = data.get("gallery_id").toString().replace("\"","");
            String array[]=galleryId.split(",");
            int arrayLength = array.length;
            Integer cart[] = new Integer[arrayLength];
            for(int i  =0;i<cart.length;i++)
            {
                cart[i]=Integer.parseInt(array[i]);
            }
            int sum=0;
            // 최종금액 > 보유돈보다 크면 결제안되야함..
            int idx = accountsMapper.findEmail(email).get().getIdx();
            log.info("현재사용중인 로그인한계정은 "+email+"("+idx+") 입니다.");
            int totalPrice = paymentMapper.userPoint(idx).getTotalPoint();
            log.info(idx+"의 충전된 금액은"+totalPrice+" 입니다");
            if(price<=totalPrice) {
                if (userCheck(token, email)) {
                    try {
                        log.info(Integer.toString(cart.length));
                        for (int i = 0; i < cart.length; i++) {
                            log.info(cart[i]+"번 물건 결제시도");
                            String tmp = itemBuy(cart[i], token).toString();
                            log.info(tmp);
                        }
                        log.info(price+"원 결제");
                        log.info(totalPrice-price+"원 남았습니다.");
                        for(int i=0; i<cart.length;i++)
                        {
                            log.info(cart[i]+"구매 완료후 삭제");
                            delete(token,email,cart[i].toString());
                        }
                        return totalPrice-price;

                        //return 최종결제금액
                    } catch (Exception e) {
                        log.info(e.getMessage());
                        log.info("결제 오류");
                    }
                }
            }
            log.info(totalPrice-price+"원 모잘랍니다");
            return totalPrice-price;
        }catch (Exception e)
        {
            log.info("Json 오류");
            return "{\"code\" : false}";
        }
    }
    public Object cartDelete(String token, String email, String gallery_id)
    {
        String array[]=gallery_id.split(",");
        int arrayLength = array.length;
        Integer arrayGallId[] = new Integer[arrayLength];
        int idx = accountsMapper.findEmail(email).get().getIdx();

        if(userCheck(token,email))
        {
            try {
                for (int i = 0; i < arrayLength; i++) {
                    arrayGallId[i] = Integer.parseInt(array[i]);
                    log.info(Integer.toString(arrayGallId[i]));
                }
                for (int i=0; i< arrayLength;i++) {
                    log.info(idx+" "+arrayGallId[i]);
                    paymentMapper.cartDelete(idx, arrayGallId[i]);
                    log.info(gallery_id+"   삭제 완료");
                }
            }catch (Exception e)
            {
                log.info(e.getMessage());
                return false;
            }

            return true;
        }
        return false;
    }
    public Object delete(String token, String email, String gallery_id)
    {
        String array[]=gallery_id.split(",");
        int arrayLength = array.length;
        Integer arrayGallId[] = new Integer[arrayLength];
        int idx = accountsMapper.findEmail(email).get().getIdx();

        if(userCheck(token,email))
        {
            try {
                for (int i = 0; i < arrayLength; i++) {
                    arrayGallId[i] = Integer.parseInt(array[i]);
                }
                for (int i=0; i< arrayGallId.length;i++) {
                    paymentMapper.cartDelete(idx, arrayGallId[i]);
                }
            }catch (Exception e)
            {
                log.info(e.getMessage());
                return false;
            }
            log.info(gallery_id+"   삭제 완료");
            return true;
        }
        return false;
    }
    //cart 삭제만들어야함 (매퍼포함) 그이후에 장바구니 구매하면 삭제되게 조치필요.

    public boolean userCheck(String token, String email)
    {
        log.info("유저 인증을 시도합니다 : "+token+ " "+email);
        if (accountsService.login_check(token))
        {
            log.info("유저 인증");
            log.info(accountsService.getUserPk(token));
            if (accountsService.getUserPk(token).equals(email)) {
                log.info("유저 인증성공");
                return true;
            }
            return false;
        }
        return false;
    }
    public String card_Code(String code)
    {
        switch (code)
        {
            case "361":
                return "BC카드";
            case "364":
                return "광주카드";
            case "365":
                return "삼성카드";
            case "366":
                return "신한카드";
            case "367":
                return "현대카드";
            case "368":
                return "롯데카드";
            case "369":
                return "수협카드";
            case "370":
                return "씨티카드";
            case "371":
                return "NH카드";
            case "372":
                return "전북카드";
            case "373":
                 return "제주카드";
            case "374":
                 return "하나SK카드";
            case "381":
                 return "KB국민카드";
            case "041":
                return "우리카드";
            case "071":
                return "우체국";
            default:
                return "등록되지 않은카드입니다.";
        }
    }
}