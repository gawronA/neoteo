package pl.local.neoteo.service;

public interface ReCaptchaService {
    boolean verify(String captcha);
}
