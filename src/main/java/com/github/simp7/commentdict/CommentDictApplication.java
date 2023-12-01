package com.github.simp7.commentdict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CommentDictApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentDictApplication.class, args);
    }

}
