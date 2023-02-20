package hyo.boardexample.Controller;

import com.nhncorp.lucy.security.xss.XssPreventer;
import hyo.boardexample.Service.LoginService;
import hyo.boardexample.common.SessionConstants;
import hyo.boardexample.domain.BoardType;
import hyo.boardexample.domain.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncoder pwEncoder;

    //컨트롤러 내에서 발생하는 예외를 모두 처리해준다
    @ExceptionHandler(value = Exception.class)
    public String controllerExceptionHandler(Exception e) {
        System.out.println(new RuntimeException(e + " : 에러 발생"));
        return "/error";
    }

    @GetMapping(value = "/admin/login")
    public String loginForm(Model model, HttpServletRequest request) {
        String path = request.getRequestURI();
        System.out.println(path);

        model.addAttribute("loginForm", new Login());
        model.addAttribute("path", path);

        return "/login/loginForm";
    }

    @PostMapping("/loginCheck")
    @ResponseBody
    public String loginCheck(@ModelAttribute Login loginForm) {
        String rawPw = "";
        String encodePw = "";

        Login loginMember = loginService.getUser(loginForm);

        if(loginMember == null) {
            return "아이디가 일치하지 않습니다.";
        }

        rawPw = loginForm.getUser_pw();
        encodePw = loginMember.getUser_pw();

        if(!pwEncoder.matches(rawPw, encodePw)) {
            return "비밀번호가 일치하지 않습니다.";
        }

        return "성공";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute Login loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request,
                        Model model) {

        Login loginMember = loginService.getUser(loginForm);
        String path = request.getParameter("path");

        // 관리자 페이지 주소로 접근 시
        if(path.equals("/admin/login")) {
            if(loginMember.getAuth_code().equals("admin")) {
                // 로그인 성공
                HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
                session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);   // 세션에 로그인 회원 정보 보관

                return "redirect:/admin";
            } else {
                model.addAttribute("loginForm", loginMember);
                model.addAttribute("msg", "관리자 권한이 없습니다.");
                model.addAttribute("path", path);
                return "/login/loginForm";
            }
        }

        // 로그인 성공
        HttpSession session = request.getSession(); // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);   // 세션에 로그인 회원 정보 보관

        return "redirect:" + redirectURL;
    }


    @PostMapping("/admin/logout")
    public String adminLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/admin";
    }
}
