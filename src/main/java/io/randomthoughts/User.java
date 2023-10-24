package io.randomthoughts;

public class User {
    private String name;

    private String email;

    // Password
    private String password;

    // Google account security phone number
    private String securityPhoneNumber;

    // Google Voice phone number
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityPhoneNumber() {
        return securityPhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static User create(String name, String email, String password, String securityPhoneNumber, String phoneNumber) {
        var user = new User();

        user.name = name;
        user.email = email;
        user.password = password;
        user.securityPhoneNumber = securityPhoneNumber;
        user.phoneNumber = phoneNumber;

        return user;
    }
}
