//package wit.shortterm1.exercisetogether.global.common.jwt;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import wit.shortterm1.kkoowoon.domain.user.persist.Account;
//import wit.shortterm1.kkoowoon.domain.user.persist.AuthAccount;
//
//@Component
//public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        final boolean isRegUserAnnotation = parameter.getParameterAnnotation(AuthAccount.class) != null;
//        final boolean isRegisterDto = parameter.getParameterType().equals(Account.class);
//        return isRegUserAnnotation && isRegisterDto;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return Account.of(userDetails.getUsername(), userDetails.getUsername(), userDetails.getPassword());
//        }
//        return null;
//    }
//
//}
