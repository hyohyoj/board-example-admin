package hyo.boardexample.common;

import hyo.boardexample.domain.Login;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        ModelAndView mv = new ModelAndView("/alert");
        Login loginMember = null;

        try{
            loginMember = (Login) session.getAttribute(SessionConstants.LOGIN_MEMBER);

            if(loginMember == null) {
                mv.addObject("msg", "세션이 만료되어 로그아웃 되었습니다.");
                mv.addObject("url", "/admin/login");
                throw new ModelAndViewDefiningException(mv);
            } else {
                if(!loginMember.getAuth_code().equals("admin")) {
                    session.invalidate();

                    mv.addObject("msg", "관리자 권한이 없습니다.");
                    mv.addObject("url", "/admin");
                    throw new ModelAndViewDefiningException(mv);
                } else {
                    return true;
                }
            }

        } catch (Exception e) {
            mv.addObject("msg", "세션이 만료되어 로그아웃 되었습니다.");
            mv.addObject("url", "/admin/login");
            throw new ModelAndViewDefiningException(mv);
        }

    }
}
