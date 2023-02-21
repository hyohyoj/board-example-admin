package hyo.boardexample.Service;

import hyo.boardexample.domain.Login;
import hyo.boardexample.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final LoginMapper loginMapper;

    public Login getUser(Login loginForm) { return loginMapper.getUser(loginForm); }

    public int insertUser(Login signUpForm) { return loginMapper.insertUser(signUpForm); }

    public List<Login> getUserList() {
        return loginMapper.getUserList();
    }

    public int updateUser(Login login) {
        return loginMapper.updateUser(login);
    }

    public int setUserDeleteYn(Login login) {
        return loginMapper.setUserDeleteYn(login);
    }

    public String stringTest(String input){
        return input;
    }

    public Login dtoTest(Login login) {
        Login returnLogin = new Login();
        returnLogin.setUser_id(login.getUser_id());
        returnLogin.setUser_pw(login.getUser_pw());
        return returnLogin;
    }
}
