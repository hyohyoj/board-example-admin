package hyo.boardexample.Controller;

import hyo.boardexample.Service.BoardTypeService;
import hyo.boardexample.common.SessionConstants;
import hyo.boardexample.domain.Board;
import hyo.boardexample.domain.BoardType;
import hyo.boardexample.domain.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardTypeService boardTypeService;

    //컨트롤러 내에서 발생하는 예외를 모두 처리해준다
    @ExceptionHandler(value = Exception.class)
    public String controllerExceptionHandler(Exception e) {
        return "/error";
    }

    @GetMapping(value= {"/", "/admin"})
    public String adminHome(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Login loginMember, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션에 회원 데이터가 없으면 관리자 홈으로 이동
        if (loginMember == null) {
            return "/admin/home";
        }
        // 관리자가 아닐 경우 관리자 홈으로 이동
        if (!loginMember.getAuth_code().equals("admin")) {
            session.invalidate();
            return "/admin/home";
        }

        // 세션이 유지되면 로그인 홈으로 이동
        model.addAttribute("member", loginMember);

        return "/admin/boardManage";
    }

}

