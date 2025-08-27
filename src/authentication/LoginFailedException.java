package authentication;

public class LoginFailedException extends Exception {
    public LoginFailedException() {
        super("Недопустимое имя пользователя или пароль");
    }
}
