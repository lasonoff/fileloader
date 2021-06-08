package ru.yauroff.test.fileloader.repository.impl;

import ru.yauroff.test.fileloader.model.File;
import ru.yauroff.test.fileloader.repository.IFileRepository;

public class FileRepository extends Repository<Long, File> implements IFileRepository {

    @Override
    protected Class<File> getEntityClass() {
        return File.class;
    }

    @Override
    protected Long getEntityId(File entity) {
        return entity.getId();
    }

    @Override
    protected void updateEntity(File entityFromDB, File entity) {
        entityFromDB.setStatus(entity.getStatus());
    }
}
