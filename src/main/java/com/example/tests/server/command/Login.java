package com.example.tests.server.command;

import com.example.tests.server.controller.protocol.TcpRequest;
import com.example.tests.server.controller.protocol.TcpResponse;
import com.example.tests.server.entity.User;
import com.example.tests.server.service.UserService;

import java.util.Optional;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public TcpResponse execute(TcpRequest tcpRequest) {
        String login = (String) tcpRequest.getParameter("login");
        String password = (String) tcpRequest.getParameter("password");
        Optional<User> userOptional = userService.login(login, password);
        TcpResponse tcpResponse = new TcpResponse();
        tcpResponse.addAttribute("user", userOptional.orElse(null));
        return tcpResponse;
    }
}
