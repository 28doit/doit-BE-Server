package com.example.photologger.photo.apidocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags= "item Controller")
public interface itemControllerDocs {
    @ApiOperation(value = "사진구매",notes = "사진을구매합니다")
    public Object itemBuy(@ApiParam(value = "사진구매데이터")  Map<String,Object> tmp);

    @ApiOperation(value = "사진구매내역",notes = "사진을 구매한내역을 시작날짜와 끝날짜에 맞춰보여줍니다.")
    public Object itemHistory(@ApiParam(value = "토큰")String token,
                              @ApiParam(value = "조회 시작날짜") String start,
                              @ApiParam(name = "조회 종료날짜(0시기준 조회기때문에 조회날짜는 +1을 해서 보내는게좋습니다.") String end);

    @ApiOperation(value = "장바구니 확인",notes = "장바구니목록을 보여줍니다.")
    public Object cartCheck(@ApiParam(value = "토큰")  String token);

    @ApiOperation(value = "장바구니 담기",notes = "해당사진을 장바구니에 담습니다.")
    public Object cartInsert(@ApiParam(value = "장바구니에 넣을 데이터")  Map<String,Object> tmp);

    @ApiOperation(value = "장바구니 구매",notes = "장바구니에 있는 물건을 선택하여 구매합니다.")
    public Object cartBuy(@ApiParam(value = "장바구니에서 구매할 상품")  String json);

    @ApiOperation(value = "장바구니 삭제",notes = "구매가 완료된 상품 OR 장바구니에서 삭제할 물건을 입력합니다.")
    public Object cartDelete(@ApiParam(value = "토큰")  String token,
                             @ApiParam(value = "이메일") String email,
                             @ApiParam(value = "사진번호") String gallery_id);
}