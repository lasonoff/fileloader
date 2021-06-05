package ru.yauroff.test.fileloader.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.File;
import ru.yauroff.test.fileloader.service.impl.FileService;
import ru.yauroff.test.fileloader.service.impl.ServiceRepository;
import ru.yauroff.test.fileloader.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FileController extends BaseController {
    private static final Logger logger = LogManager.getLogger(FileController.class);

    private static UserService userService;
    private static FileService fileService;

    @Override
    public void init() throws ServletException {
        fileService = ServiceRepository.getInstance().getFileService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().equals("/files/count")) {
            response.setContentType("text/html");
            PrintWriter messageWriter = response.getWriter();
            messageWriter.println(fileService.getCount());
            return;
        }
        Long id = parseId(request.getRequestURI(), "files");
        File file = fileService.getById(id);
        writeJsonToResponse(file, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
