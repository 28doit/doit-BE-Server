package com.example.photologger.photo.apidocument;

import com.example.photologger.photo.service.PaymentSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags= "Cash Controller")
public interface CashControllerDocs {
    @ApiOperation(value = "포인트 충전", notes = "아임포트측과 연동하여 결제햔 데이터의 정보를 받고 확인합니다.")
    public boolean check(@ApiParam(value = "결제데이터") Map<String, String> mid);

    @ApiOperation(value = "현글거래내역(결제내역)",notes = "데이터 베이스에 저장되있는 현금거래내역(결제내역)을 ")
    public Object history(@ApiParam(value = "토큰") String token,
                          @ApiParam(value = "조회 시작날짜") String start,
                          @ApiParam(name = "조회 종료날짜(0시기준 조회기때문에 조회날짜는 +1을 해서 보내는게좋습니다.") String end);
    
    @ApiOperation(value = "출금",notes = "실제 돈을 출금할방법이없기에 빼는거만 계산됬습니다.")
    public Object withdrawal(@ApiParam(value = "토큰") String token,
                             @ApiParam(value = "이메일") String email,
                             @ApiParam(value = "가격") int pay);

    @ApiOperation(value = "출금내역",notes = "출금내역을 보여줍니다.")
    public Object withdrawalHistory(@ApiParam(value = "이메일") String email,
                                    @ApiParam(value = "토큰") String token,
                                    @ApiParam(value = "조회 시작날짜") String start,
                                    @ApiParam(name = "조회 종료날짜(0시기준 조회기때문에 조회날짜는 +1을 해서 보내는게좋습니다.") String end);
}

