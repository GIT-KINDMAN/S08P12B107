package B107.server.meerkat.controller;

import B107.server.meerkat.config.security.auth.PrincipalDetails;
import B107.server.meerkat.config.security.handler.DecodeEncodeHandler;
import B107.server.meerkat.config.utils.Msg;
import B107.server.meerkat.config.utils.ResponseDTO;
import B107.server.meerkat.dto.member.SignModReqDTO;
import B107.server.meerkat.dto.member.mod;
import B107.server.meerkat.dto.member.modPw;
import B107.server.meerkat.repository.MemberRepository;
import B107.server.meerkat.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

    private static final String METHOD_NAME = MemberController.class.getName();

    private final MemberService memberService;

    /**
     * 회원 정보 수정
     */
    @PostMapping("/updateInfo")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Validated(mod.class) SignModReqDTO signModReqDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MEMBER_MOD, memberService.update(signModReqDTO, principalDetails.getMember().getIdx())));
    }

    /**
     * 회원 비밀번호 수정
     */
    @PostMapping("/updatePw")
    public ResponseEntity<ResponseDTO> updatePw(@RequestBody @Validated(modPw.class) SignModReqDTO signModReqDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MEMBER_MODPW, memberService.updatePw(signModReqDTO, principalDetails.getMember().getIdx())));
    }

    /**
     * 회원 프로필 조회
     */
    @GetMapping("/profile/read")
    public ResponseEntity<ResponseDTO> readProfile(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MEMBER_PROFILE, memberService.readProfile(principalDetails.getMember().getIdx())));
    }

    /**
     * 회원 마이페이지
     */
    @GetMapping("/profile/mypage")
    public ResponseEntity<ResponseDTO> myPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok().body(ResponseDTO.of(HttpStatus.OK, Msg.SUCCESS_MEMBER_MYPAGE, memberService.myPage(principalDetails.getMember().getIdx())));
    }
}
