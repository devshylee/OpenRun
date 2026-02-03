package com.openrun.api.service.user;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.openrun.core.mapper.user.PasswordMapper;
import com.openrun.api.dto.user.PasswordDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordMapper passwordMapper;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(Long id, PasswordDTO passwordDTO) {

        String currentEncodedPassword = passwordMapper.findPasswordByUserId(id);

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), currentEncodedPassword)) {
            throw new com.openrun.common.exception.IncorrectPasswordException();
        }

        // 신규 비밀번호 일치 확인
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            throw new com.openrun.common.exception.PasswordMismatchException();
        }

        // 비밀번호 업데이트
        String encodedNewPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
        int updated = passwordMapper.updatePassword(id, encodedNewPassword);

        if (updated == 0) {
            throw new RuntimeException("비밀번호 변경 중 문제가 발생하였습니다.");
        }
    }

}
