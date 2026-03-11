package cityu.khchan744.blog.service;

import cityu.khchan744.blog.dao.UserDao;
import cityu.khchan744.blog.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Optional;

public class AuthService {
    private final UserDao userDao = new UserDao();
    private static final SecureRandom RAND = new SecureRandom();
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256; // bits

    public Optional<User> login(String usernameOrEmail, String password) {
        Optional<User> byUsername = userDao.findByUsername(usernameOrEmail);
        Optional<User> byEmail = userDao.findByEmail(usernameOrEmail);
        Optional<User> user = byUsername.isPresent() ? byUsername : byEmail;
        if (user.isPresent()) {
            User u = user.get();
            if (verifyPassword(password, u.getPasswordHash())) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    public User register(String username, String email, String plainPassword, String displayName) {
        String hashed = hashPassword(plainPassword);
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(hashed);
        u.setDisplayName(displayName);
        u.setRole("USER");
        userDao.create(u);
        return u;
    }

    private String hashPassword(String password) {
        byte[] salt = new byte[16];
        RAND.nextBytes(salt);
        byte[] hashed = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        String sSalt = Base64.getEncoder().encodeToString(salt);
        String sHash = Base64.getEncoder().encodeToString(hashed);
        return String.format("pbkdf2$%d$%s$%s", ITERATIONS, sSalt, sHash);
    }

    private boolean verifyPassword(String password, String stored) {
        if (stored == null) return false;
        try {
            String[] parts = stored.split("\\$");
            if (parts.length != 4) return false;
            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] hash = Base64.getDecoder().decode(parts[3]);
            byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length * 8);
            return slowEquals(hash, testHash);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < Math.min(a.length, b.length); i++) diff |= a[i] ^ b[i];
        return diff == 0;
    }
}
