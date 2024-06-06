package com.example.makeup.utils;

public interface WHITELIST_UTILS {
      String[] SWAGGER = {
              "/hello",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources",
            "/swagger-ui.html/**",};
    String[] POST_REQUESTS = {
            "/user"
    };


}
