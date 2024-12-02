package coding;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+$)");

    public static boolean isValidEmail(String email) {
        return EMAIL.matcher(email).matches();
    }
    public static boolean fieldsProvided(String... fields){
        for (String field : fields) {
            if(field.trim().isEmpty() || field == null){
                return false;
            }
        }
        return true;
    }
    public static String hashPassword(String password) {
        try{
            MessageDigest pass = MessageDigest.getInstance("SHA-256");
            byte[] hash = pass.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}