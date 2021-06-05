package ru.yauroff.test.fileloader.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.stream.Collectors;


public class BaseController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UsersController.class);

    protected String getStringJsonBody(HttpServletRequest request) throws IOException {
        String jsonBody = new BufferedReader(new InputStreamReader(request.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        return jsonBody;
    }

    protected <T> String toJson(T object) throws ServletException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = "";
        try {
            jsonString = gson.toJson(object, object.getClass());
        } catch (JsonIOException e) {
            throw new ServletException(e);
        }
        return jsonString;
    }

    protected <T> T fromJson(String json, Type typeOfT) throws ServletException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        T res = null;
        try {
            res = gson.fromJson(json, typeOfT);
        } catch (JsonIOException e) {
            throw new ServletException(e);
        }
        return res;
    }

    protected Long parseId(String uri, String startWith) throws ServletException {
        Long res = 0L;
        if (uri.startsWith("/" + startWith + "/")) {
            try {
                res = Long.valueOf(uri.replaceAll("/" + startWith + "/", ""));
            } catch (NumberFormatException e) {
                throw new ServletException(e);
            }
        }
        return res;
    }

    protected <T> void writeJsonToResponse(T object, HttpServletResponse response) throws ServletException, IOException {
        String stringJson = "";
        if (object != null) {
            stringJson = toJson(object);
        }

        response.setContentType("application/json");
        PrintWriter messageWriter = response.getWriter();
        messageWriter.println(stringJson);
    }
}
