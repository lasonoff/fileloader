package ru.yauroff.test.fileloader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.User;
import ru.yauroff.test.fileloader.service.impl.ServiceRepository;
import ru.yauroff.test.fileloader.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController extends BaseController {
    private static final Logger logger = LogManager.getLogger(UsersController.class);
    private static UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServiceRepository.getInstance().getUserService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().equals("/users/count")) {
            response.setContentType("text/html");
            PrintWriter messageWriter = response.getWriter();
            messageWriter.println(userService.getCount());
            return;
        }
        Long id = parseId(request.getRequestURI(), "users");
        User user = userService.getById(id);
        writeJsonToResponce(user, response);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = parseId(request.getRequestURI(), "users");
        String stringRequestBody = getStringJsonBody(request);
        User user = fromJson(stringRequestBody, User.class);
        user.setId(id);
        user = userService.update(user);
        writeJsonToResponce(user, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Long id = parseId(request.getRequestURI(), "users");
        userService.deleteById(id);
    }
}
