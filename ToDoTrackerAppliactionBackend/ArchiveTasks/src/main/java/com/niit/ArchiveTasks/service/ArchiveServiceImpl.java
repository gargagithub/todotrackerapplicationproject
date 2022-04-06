package com.niit.ArchiveTasks.service;

import com.niit.ArchiveTasks.exceptions.ArchiveAlreadyExistsException;
import com.niit.ArchiveTasks.exceptions.ArchiveNotFoundException;
import com.niit.ArchiveTasks.exceptions.TaskAlreadyExistsException;
import com.niit.ArchiveTasks.model.Archive;
import com.niit.ArchiveTasks.model.Task;
import com.niit.ArchiveTasks.respository.ArchiveRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArchiveServiceImpl implements  ArchiveService{

  ArchiveRespository archiveRespository;
  SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public ArchiveServiceImpl(ArchiveRespository archiveRespository,SequenceGeneratorService sequenceGeneratorService)
    {
        this.archiveRespository = archiveRespository;
        this.sequenceGeneratorService=sequenceGeneratorService;
    }

    @Override
    public Archive saveArchive(Archive archive,String userEmail) throws ArchiveAlreadyExistsException {
        archive.setArchiveId(sequenceGeneratorService.getSequenceNumber(archive.SEQUENCE_NAME));
        archive.setUserEmail(userEmail);
        return archiveRespository.save(archive);
    }

    @Override
    public Archive saveOfficialTaskToArchive(String userEmail , Task task) throws ArchiveNotFoundException, TaskAlreadyExistsException {
        if (archiveRespository.findByUserEmail(userEmail)==null) {
            throw new ArchiveNotFoundException();
        }
        Archive archive = archiveRespository.findByUserEmail(userEmail);
        if (archive.getOffcialTaskList() == null) {
            archive.setOffcialTaskList(Arrays.asList(task));
        } else {
            List<Task> tasks = archive.getOffcialTaskList();
            tasks.add(task);
            archive.setOffcialTaskList(tasks);
        }

        return archiveRespository.save(archive);
    }

    @Override
    public Archive savePersonalTaskToArchive(String userEmail , Task task) throws ArchiveNotFoundException, TaskAlreadyExistsException {
        if (archiveRespository.findByUserEmail(userEmail)==null) {
            throw new ArchiveNotFoundException();
        }
        Archive archive = archiveRespository.findByUserEmail(userEmail);
        if (archive.getPersonalTaskList() == null) {
            archive.setPersonalTaskList(Arrays.asList(task));
        } else {
            List<Task> tasks = archive.getPersonalTaskList();
            tasks.add(task);
            archive.setPersonalTaskList(tasks);
        }

        return archiveRespository.save(archive);
    }

    @Override
    public List<Archive> getAllArchives() throws Exception {
        try {
            return archiveRespository.findAll();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Task> getOfficialTasklistByUserEmail(String userEmail) throws ArchiveNotFoundException{
        if(archiveRespository.findByUserEmail(userEmail)==null){
            throw new ArchiveNotFoundException();
        }
        else{
            return archiveRespository.findByUserEmail(userEmail).getOffcialTaskList();
        }

    }

    @Override
    public List<Task> getPersonalTasklistByUserEmail(String userEmail) throws ArchiveNotFoundException{
        if(archiveRespository.findByUserEmail(userEmail)==null){
            throw new ArchiveNotFoundException();
        }
        else{
            return archiveRespository.findByUserEmail(userEmail).getPersonalTaskList();
        }

    }

    @Override
    public Archive getArchiveUserEmail(String userEmail)  throws ArchiveNotFoundException{
        if(archiveRespository.findByUserEmail(userEmail)==null){
            throw new ArchiveNotFoundException();
        }

         return archiveRespository.findByUserEmail(userEmail);
    }
}
