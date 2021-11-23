package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SellerIdxCheck {
    @ApiModelProperty(example = "사진번호")
    int gallaryId;
    @ApiModelProperty(example = "회원번호")
    int idx;
}
