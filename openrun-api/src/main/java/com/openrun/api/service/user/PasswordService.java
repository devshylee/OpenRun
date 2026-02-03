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

    public int changePassword(Long id, PasswordDTO passwordDTO) {

        String currentEncodedPassword = passwordMapper.findPasswordByUserId(id);

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), currentEncodedPassword)) {
            return 1;
        }

        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
            return 2;
        }

        String encodedNewPassword = passwordEncoder.encode(passwordDTO.getNewPassword());

        int updated = passwordMapper.updatePassword(id, encodedNewPassword);
        System.out.println("@@@@@@@@@@@ updated : " + updated);

        if (updated == 0) {
            return 3;
        }

        return 0;
    }

}
