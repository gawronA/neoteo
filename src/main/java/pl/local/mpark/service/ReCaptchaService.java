package pl.local.mpark.service;

public interface ReCaptchaService {
    boolean verify(String captcha);
}
