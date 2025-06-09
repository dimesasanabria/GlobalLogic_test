package test.java.com.globalogic.test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.globalogic.test.entity.User;
import com.globalogic.test.service.UserService;
public class TestService {

    private final UserService service;

    public TestService(UserService service) {
        this.service = service;
    }

    @RequestMapping("/api/usuarios")
    @ResponseBody
    public Object getUser() {
        return (Object)this.service.getUser();
    }

}
