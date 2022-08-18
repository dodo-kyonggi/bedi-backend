package com.deadline826.bedi.Home.Controller;


import com.deadline826.bedi.exception.DuplicateEmailException;
import com.deadline826.bedi.login.Domain.Dto.UserDto;
import com.deadline826.bedi.login.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() throws DuplicateEmailException {

        return " 홈 테스트";

    }

}
