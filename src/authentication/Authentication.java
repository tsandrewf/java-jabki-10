package authentication;

import java.util.Map;

public class Authentication {
    private Map<String, String> loginMap;

    public Authentication(Map<String, String> loginMap) {
        this.loginMap = loginMap;
    }

    public void login(String username, String password) throws LoginFailedException {
        if (!this.loginMap.containsKey(username) || !this.loginMap.get(username).equals(password)) {
            throw new LoginFailedException();
        }

        System.out.println("Успешная аутентификация!");
    }
}
