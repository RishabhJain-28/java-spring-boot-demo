package com.rishabh.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
          Student abc =   new Student(
                    "abc",
                    "abc@gmail.com",
                    LocalDate.of(2001, Month.MAY,28)

            );
          Student zxc =   new Student(
                    "zxc",
                    "zxc@gmail.com",
                    LocalDate.of(2006, Month.JANUARY,2)
            );
          repository.saveAll(
                  List.of(abc,zxc)
          );
        };
    }

}
