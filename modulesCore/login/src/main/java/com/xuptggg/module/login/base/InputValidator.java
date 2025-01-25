package com.xuptggg.module.login.base;
public class InputValidator {

    // 验证手机号方法，返回不同状态码
    private int verifyPhone(String username) {
        if (username.isEmpty()|| username.length() != 11) {
            return 1;
        }
        String regStr = "^1[3458]\\d{9}$";
        if (!username.matches(regStr)) {
            return 2;
        }
        return 0;
    }

    // 验证邮箱方法，返回不同状态码
    private int verifyEmail(String email) {
        if (email.isEmpty()|| email.length() < 8) {
            return 3;
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z]+";
        if (!email.matches(emailPattern)) {
            return 4;
        }
        return 0;
    }

    // 处理验证结果的方法
    private void handleResult(int result) {
        switch (result) {
            case 1:
                System.out.println("请输入手机号");
                break;
            case 2:
                System.out.println("请输入正确的手机号");
                break;
            case 3:
                System.out.println("请输入邮箱");
                break;
            case 4:
                System.out.println("请输入正确的邮箱格式");
                break;
        }
    }

    // 仅传入一个账号，判断其是手机号还是邮箱并验证
    public  boolean validateAccount(String account) {
        int phoneResult = verifyPhone(account);
        int emailResult = verifyEmail(account);
        if (phoneResult == 0 || emailResult == 0) {
            return true;
        }else {
            return false;
        }
    }
}