package com.cmms.api.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dj4zfm8og",
                "api_key", "269129754142163",
                "api_secret", "k3FQ6J3qe59vJ2ZpHw5bR_Wq4UI"));
    }
}
