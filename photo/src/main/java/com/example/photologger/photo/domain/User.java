package com.example.photologger.photo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @ApiModelProperty(example = "회원 번호")
    private int idx;

    @ApiModelProperty(example = "프로필 사진")
    private String profileImageLocation;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @ApiModelProperty(example = "E-mail")
    private String email;

    @NotEmpty(message = "이름을 입력하세요.")
    @ApiModelProperty(example = "이름")
    private String name;

    @NotEmpty(message = "닉네임을 입력하세요.")
    @ApiModelProperty(example = "닉네임")
    private String nickName;

    @Size(min = 10, max = 11, message = "전화번호 양식에 맞지 않습니다")
    @ApiModelProperty(example = "전화번호")
    private String phoneNumber;

    @NotEmpty(message = "성별을 체크 해주세요.")
    @ApiModelProperty(example = "성별")
    private int sex;

    @Size(min =4 ,max = 4,message = "태어난 연도를 입력해주세요.")
    @ApiModelProperty(example = "년")
    private String userYear;

    @Size(min =2 ,max = 2,message = "태어난 달을 입력해주세요.")
    @ApiModelProperty(example = "월")
    private String userMonth;

    @Size(min =2 ,max = 2,message = "태어난 일을 입력해주세요.")
    @ApiModelProperty(example = "일")
    private String userDay;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @ApiModelProperty(example = "비밀번호")
    private String password;

    @ApiModelProperty(example = "사용자 직급")
    private int type;

    @ApiModelProperty(example = "사진 갯수")
    private int gallCount;

    @ApiModelProperty(example = "구독자 수")
    private int userSubscribeCount;

    @ApiModelProperty(example = "이메일 체크 확인")
    private int authKey;
}
