package ru.yauroff.test.fileloader.controller;

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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class FileController extends BaseController {
    private static final Logger logger = LogManager.getLogger(FileController.class);

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
        if (request.getRequestURI().contains("/download")) {
            download(request, response);
            return;
        }
        Long id = parseId(request.getRequestURI(), "files");
        File file = fileService.getById(id);
        writeJsonToResponse(file, response);
    }

    private void download(HttpServletRequest request, HttpServletResponse respons) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri =  uri.replaceAll("/download", "");
        Long id = parseId(uri, "files");
        File file = fileService.getById(id);
        if (file == null) {
            throw new ServletException("Nof found file!");
        }

        respons.setContentType("text/plain");
        respons.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        try(InputStream in = request.getServletContext().getResourceAsStream(file.getFileLocation());
            OutputStream out = respons.getOutputStream()) {
            byte[] buffer = new byte[1048];
            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = parseId(request.getRequestURI(), "files");
        String stringRequestBody = getStringJsonBody(request);
        Map<String, String> params = fromJson(stringRequestBody, Map.class);
        File deletedFile;
        try {
            deletedFile = fileService.deleteFile(id, params.get("login"));
        } catch (Exception e) {
            throw new ServletException(e);
        }
        writeJsonToResponse(deletedFile, response);
    }
}
