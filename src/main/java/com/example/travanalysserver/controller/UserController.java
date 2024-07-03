package com.example.travanalysserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
//@PreAuthorize("hasAuthority('Admin')")
public class UserController {
}
