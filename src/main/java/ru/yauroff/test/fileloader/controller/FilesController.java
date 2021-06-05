package ru.yauroff.test.fileloader.controller;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.File;
import ru.yauroff.test.fileloader.service.ParamsNotValidException;
import ru.yauroff.test.fileloader.service.UnknownUserException;
import ru.yauroff.test.fileloader.service.impl.FileService;
import ru.yauroff.test.fileloader.service.impl.ServiceRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FilesController extends BaseController {
    private static final Logger logger = LogManager.getLogger(FilesController.class);

    private static FileService fileService;

    @Override
    public void init() throws ServletException {
        fileService = ServiceRepository.getInstance().getFileService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<File> users = fileService.getAll();
        writeJsonToResponse(users, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }

        // Create file item factory and parse request to find list file item
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(fileService.getMaxSize());
        factory.setRepository(new java.io.File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(fileService.getMaxSize());

        List fileItems;
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException e) {
            throw new ServletException("Parse file items in request");
        }

        List<File> files = null;
        try {
            files = fileService.uploadFiles(fileItems);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        writeJsonToResponse(files, response);
    }
}
