package ru.yauroff.test.fileloader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.User;
import ru.yauroff.test.fileloader.service.ParamsNotValidException;
import ru.yauroff.test.fileloader.service.impl.ServiceRepository;
import ru.yauroff.test.fileloader.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersController extends BaseController {
    private static final Logger logger = LogManager.getLogger(UsersController.class);
    private static UserService userService;

    @Override
    public void init() throws ServletException {
        userService = ServiceRepository.getInstance().getUserService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAll();
        writeJsonToResponse(users, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringRequestBody = getStringJsonBody(request);
        User user = fromJson(stringRequestBody, User.class);
        try {
            user = userService.create(user);
        } catch (ParamsNotValidException e) {
            throw new ServletException(e);
        }
        writeJsonToResponse(user, response);
    }
}
