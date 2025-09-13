package com.shopping.shoppingmall.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class) // 사용할 Validator 클래스 지정
@Target({ElementType.TYPE}) // 클래스 레벨에서만 사용하도록 지정
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {

    String message() default "비밀번호가 일치하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 추가: 두 필드의 이름을 지정할 수 있도록 필드명 추가
    String passwordField();
    String confirmPasswordField();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        PasswordMatch[] value();
    }
}
