package ru.yauroff.test.fileloader.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.*;
import ru.yauroff.test.fileloader.repository.IFileRepository;
import ru.yauroff.test.fileloader.repository.impl.EventRepository;
import ru.yauroff.test.fileloader.repository.impl.ObjectRepository;
import ru.yauroff.test.fileloader.repository.impl.UserRepository;
import ru.yauroff.test.fileloader.service.IService;
import ru.yauroff.test.fileloader.service.ParamsNotValidException;
import ru.yauroff.test.fileloader.service.UnknownUserException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FileService implements IService<File, Long> {
    private static final int DEFAULT_MAX_SIZE = 100 * 1024;
    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir");
    private static final String SEP = System.getProperty("file.separator");
    private static final Logger logger = LogManager.getLogger(FileService.class);

    private IFileRepository fileRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;

    public FileService() {
        fileRepository = ObjectRepository.getInstance().getFileRepository();
        userRepository = (UserRepository) ObjectRepository.getInstance().getUserRepository();
        eventRepository = (EventRepository) ObjectRepository.getInstance().getEventRepository();
    }

    @Override
    public List<File> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public File getById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public long getCount() {
        return fileRepository.count();
    }

    public int getMaxSize() {
        return DEFAULT_MAX_SIZE;
    }

    public List<File> uploadFiles(List<FileItem> fileItems) throws Exception {

        String login = fileItems.stream().filter(item -> item.getFieldName().equals("login"))
                .map(item -> item.getString()).findFirst().orElse(null);
        String description = fileItems.stream().filter(item -> item.getFieldName().equals("description"))
                .map(item -> item.getString()).findFirst().orElse("");
        if (login == null) {
            throw new ParamsNotValidException("Not valid input params for uploadFiles!");
        }

        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null) {
            throw new UnknownUserException("Unknown user " + login);
        }

        java.io.File file;
        List<File> res = new ArrayList<>();

        Iterator fileItemIterator = fileItems.iterator();
        while (fileItemIterator.hasNext()) {
            FileItem fi = (FileItem) fileItemIterator.next();

            if (!fi.isFormField()) {
                String fileName = fi.getName();
                String filePath;
                if (fileName.lastIndexOf("\\") >= 0) {
                    filePath = DEFAULT_FILE_PATH + SEP +fileName.substring(fileName.lastIndexOf("\\"));
                } else {
                    filePath = DEFAULT_FILE_PATH + SEP + fileName.substring(fileName.lastIndexOf("\\") + 1);
                }
                file = new java.io.File(filePath);
                File fileEntity = new File(fileName, filePath, description, FileStatus.ACTIVE);
                Event event = new Event(fileEntity, user, ActionType.LOAD);
                fi.write(file);
                fileRepository.create(fileEntity);
                eventRepository.create(event);
                res.add(fileEntity);
            }
        }
        return res;
    }

    public File deleteFile(Long id, String login) throws Exception {
        if (login == null) {
            throw new ParamsNotValidException("Not valid login for deleteFile!");
        }
        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null) {
            throw new UnknownUserException("Unknown user " + login);
        }

        File fileEntity = fileRepository.findById(id).orElse(null);
        if (fileEntity == null) {
            throw new ParamsNotValidException("Not find file for deleteFile!");
        }
        // Delete from disk
        java.io.File file = new java.io.File(fileEntity.getFileLocation());
        if (!file.exists()) {
            throw new FileExistsException("File " + fileEntity.getName() + " not exists!");
        }
        file.delete();
        // Update from db
        Event event = new Event(fileEntity, user, ActionType.DELETE);
        eventRepository.create(event);
        fileEntity.setStatus(FileStatus.DELETED);
        fileRepository.update(fileEntity);
        return fileEntity;
    }
}

