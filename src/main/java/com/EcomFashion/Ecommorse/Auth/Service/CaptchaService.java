package com.EcomFashion.Ecommorse.Auth.Service;


import com.EcomFashion.Ecommorse.Auth.Dto.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private final RestTemplate restTemplate;

    public boolean verifyCaptcha(
            String captchaToken
    ) {

        String url =
                "https://www.google.com/recaptcha/api/siteverify";

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("secret", recaptchaSecret);

        body.add("response", captchaToken);

        RecaptchaResponse response =
                restTemplate.postForObject(
                        url,
                        body,
                        RecaptchaResponse.class
                );

        return response != null
                && response.isSuccess();
    }
}

