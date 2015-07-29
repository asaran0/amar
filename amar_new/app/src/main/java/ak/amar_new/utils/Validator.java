package ak.amar_new.utils;

import java.util.regex.Pattern;

/**
 * Created by amar on 31/5/15.
 */
public class Validator {
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASS_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String MobilePattern = "[0-9]{10}";
    public static boolean emailValidate(String email)
    {
        return Pattern.matches(EMAIL_REGEX, email);
    }
    public static boolean passwordValidate(String pwd)
    {
        return Pattern.matches(PASS_REGEX, pwd);
    }
    public static boolean mobileValidate(String mobile){ return Pattern.matches(MobilePattern, mobile); }
}
