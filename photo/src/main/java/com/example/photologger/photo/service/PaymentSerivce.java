package com.example.photologger.photo.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import com.example.photologger.photo.domain.Payment;
import com.example.photologger.photo.domain.PaymentHistory;
import com.example.photologger.photo.domain.ReturnHistory;
import com.example.photologger.photo.domain.User;
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
                return true;  //false 로 바꿔라
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
    public boolean check(Map <String,String>mid){
        boolean trueAndfalse;
        int idx= accountsMapper.findEmail(mid.get("login_email")).get().getIdx();
        String userName= accountsMapper.findEmail(mid.get("login_email")).get().getName();
        log.info("test:"+accountsService.token_Expiration(mid.get("token"),mid.get("login_email")).get("Token").toString());
        String iAmToken = getImportToken();
        JsonNode tmp = (JsonNode) setDB(iAmToken,mid.get("mid"));
        String buyerName = tmp.findValue("buyer_name").toString().replaceAll("\"", "");
        if ((boolean)accountsService.token_Expiration(mid.get("token"),mid.get("login_email")).get("Token")&&userName.equals(buyerName)){
            int pay = Integer.parseInt(getAmount(iAmToken,mid.get("mid")));
            trueAndfalse = setHackCheck(
                    Integer.toString(pay)
                    , mid.get("mid")
                    , iAmToken
            );
            if(trueAndfalse) {
                log.info("결제 금액 : "+Integer.toString(pay));
                Payment payment = Payment.builder()
                        .idx(idx)
                        .totalPoint(pay)
                        .sellPoint(0)
                        .build();
                paymentMapper.paymentUpdate(payment);
                Date date = new Date();
                date.setTime(Long.parseLong(tmp.findValue(PAID_AT).toString())*1000);
                PaymentHistory paymentHistory = PaymentHistory.builder()
                        .idx(idx)
                        .pay(pay)
                        .payCard(card_Code(tmp.findValue(PAY_CARD).toString().replaceAll("\"", "")))
                        .email(tmp.findValue(BUYER_EMAIL).toString().replaceAll("\"",""))
                        .name(tmp.findValue(BUYER_NAME).toString().replaceAll("\"",""))
                        .mId(tmp.findValue(MERCHANT_UID).toString().replaceAll("\"",""))
                        .time(date)
                        .build();
                try{
                    paymentMapper.paymentHistoryInsert(paymentHistory);
                    return trueAndfalse;
                }catch (Exception e){
                    log.info("이미 결제된 사용자입니다.");
                    return trueAndfalse=false;
                }
            }
            else {
                log.info("이미 결제된 사용자입니다.");
                return trueAndfalse;
            }
        }
        else{
            log.info("결제자와 로그인 중 사용자가 다릅니다.");  //debug
            return false;
        }
    }
    public Object history(String token, String start, String end)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);  //get으로 가져올 date
        SimpleDateFormat rootFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH); //DB저장되있는 date
        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);     //변환해줄 date

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
                    List<PaymentHistory> history = paymentMapper.paymentHistory(start_history, end_history,email); //db조회
                    if(userMapper.findOne(history.get(0).getIdx()).getEmail().equals(email))        //token에서나온 이메일과 db조회 이메일이 같은경우
                    {
                        log.info(Integer.toString(history.size()));
                        List<ReturnHistory> returnHistory = new ArrayList();    //date형식 지정 반환을 위해 새로생성
                        for(int i=0;i<history.size();i++)   //사이즈만큼
                        {
                            try {
                                Date data = rootFormat.parse(history.get(i).getTime().toString());  //rootFormat형식을 받아서..
                                String timeTmp = outputFormat.format(data);
                                ReturnHistory tmp = ReturnHistory.builder()
                                        .name(history.get(i).getName())
                                        .mId(history.get(i).getMId())
                                        .email(history.get(i).getEmail())
                                        .pay(history.get(i).getPay())
                                        .idx(history.get(i).getIdx())
                                        .payCard(history.get(i).getPayCard())
                                        .time(timeTmp)
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
                .name("")
                .idx(0)
                .pay(0)
                .email("")
                .mId("")
                .build();
        return returnHistory;
    }
//    public Map<String,Integer> itemBuy(String itemIdx,String token)
//    {
//        //1번 구매금액만큼 포인트 소비후 잔여포인트 갱신(반환)하기
//        try {
//            String email = accountsService.getUserPk(token);
//            User user = accountsMapper.findEmail(email)
//                    .orElseThrow(() -> new Exception("잘못된 이메일입니다."));
//
//            //구현해야할부분 구매내역에 존재하면 구매못하고 false Return 예정
//            Payment payment = Payment.builder()
//                            .sellPoint(paymentMapper.itemSelect(itemIdx))
//                            .idx(user.getIdx())
//                            .totalPoint(0)
//                            .build();
//            paymentMapper.itemSelect(itemIdx);
//        }catch (Exception e)
//        {
//            log.info(e.toString());
//        }
//
//
//        //고려해야할것 3번 쿠폰
//
//        //고려햐야할것 4번 포인트
//
//        //고려해야할것 1번 상품등록자에게 구매금액 지급
//
//    }
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
