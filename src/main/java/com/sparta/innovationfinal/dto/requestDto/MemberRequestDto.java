package com.sparta.innovationfinal.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    @Email(message = "Email 형식에 맞춰주세요.")
    private String email;

    @Size(max = 10, message = "닉네임은 10자 이내로 입력해주세요.")
    @NotBlank
    private String nickname;

    @Pattern(regexp = "[a-zA-Z\\d]*${4,16}", message = "비밀번호는 영어와 숫자 포함해서 4~16자 사이로 입력해주세요.")
    @NotBlank
    private String password;


}
