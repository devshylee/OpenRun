// package com.shopping.shoppingmall.common.handler;

// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.validation.FieldError;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.ModelAttribute;

// import com.shopping.shoppingmall.user.signup.SignupRequest;
// import com.shopping.shoppingmall.common.exception.DuplicateUserIdException;
// import com.shopping.shoppingmall.common.exception.PasswordMismatchException;
// import com.shopping.shoppingmall.common.exception.EmailNotVerifiedException;

// @ControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(DuplicateUserIdException.class)
//     public String handleDuplicateUserId(DuplicateUserIdException ex,
//                                         @ModelAttribute("signupForm") SignupRequest signupRequest,
//                                         BindingResult bindingResult) {
//         // 기존 bindingResult 객체에 에러 추가
//         bindingResult.addError(new FieldError("signupForm", "userId", signupRequest.getUserId(), false, null, null, ex.getMessage()));
//         return "user/signup";
//     }

//     @ExceptionHandler(PasswordMismatchException.class)
//     public String handlePasswordMismatch(PasswordMismatchException ex,
//                                          @ModelAttribute("signupForm") SignupRequest signupRequest,
//                                          BindingResult bindingResult) {
//         // 기존 bindingResult 객체에 에러 추가
//         bindingResult.addError(new FieldError("signupForm", "password", signupRequest.getPassword(), false, null, null, ex.getMessage()));
//         bindingResult.addError(new FieldError("signupForm", "confirmPassword", signupRequest.getConfirmPassword(), false, null, null, ex.getMessage()));
//         return "user/signup";
//     }

//     @ExceptionHandler(EmailNotVerifiedException.class)
//     public String handleEmailNotVerified(EmailNotVerifiedException ex,
//                                          @ModelAttribute("signupForm") SignupRequest signupRequest,
//                                          BindingResult bindingResult) {
//         // 기존 bindingResult 객체에 에러 추가
//         bindingResult.addError(new FieldError("signupForm", "email", signupRequest.getEmail(), false, null, null, ex.getMessage()));
//         return "user/signup";
//     }

//     @ExceptionHandler(IllegalStateException.class)
//     public String handleIllegalStateException(IllegalStateException ex, Model model,
//                                               @ModelAttribute("signupForm") SignupRequest signupRequest) {
//         // 글로벌 에러 메시지만 추가
//         model.addAttribute("globalError", ex.getMessage());
//         return "user/signup";
//     }
// }