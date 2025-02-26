package com.xuptggg.module.login.base;

import android.util.Patterns;
import java.util.regex.Pattern;

public class ValidationUtil {

    // 正则表达式定义
    public static final String PHONE_REGEX_CN = "^1[3-9]\\d{9}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
    private static final String VERIFICATION_CODE_REGEX = "^\\d{6}$";

    // 私有构造方法防止实例化
    private ValidationUtil() {}

    /**
     * 验证手机号（中国大陆）
     */
    public static ValidationResult validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return new ValidationResult(false, "手机号不能为空");
        }
        if (!Pattern.matches(PHONE_REGEX_CN, phone)) {
            return new ValidationResult(false, "手机号格式错误");
        }
        return new ValidationResult(true);
    }

    /**
     * 验证邮箱
     */
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ValidationResult(false, "邮箱不能为空");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return new ValidationResult(false, "邮箱格式错误");
        }
        return new ValidationResult(true);
    }

    /**
     * 验证密码强度
     */
    public static ValidationResult validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return new ValidationResult(false, "密码不能为空");
        }
        if (password.length() < 8) {
            return new ValidationResult(false, "密码至少需要8位");
        }
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            return new ValidationResult(false, "需包含字母和数字");
        }
        return new ValidationResult(true);
    }

    /**
     * 验证6位数字验证码
     */
    public static ValidationResult validateVerificationCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return new ValidationResult(false, "验证码不能为空");
        }
        if (!Pattern.matches(VERIFICATION_CODE_REGEX, code)) {
            return new ValidationResult(false, "验证码需为6位数字");
        }
        return new ValidationResult(true);
    }

    /**
     * 验证确认密码一致性
     */
    public static ValidationResult validateConfirmPassword(String password, String confirmPassword) {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return new ValidationResult(false, "请确认密码");
        }
        if (!confirmPassword.equals(password)) {
            return new ValidationResult(false, "两次输入的密码不一致");
        }
        return new ValidationResult(true);
    }
}