package com.niit.ArchiveTasks.service;

import com.niit.ArchiveTasks.exceptions.ArchiveAlreadyExistsException;
import com.niit.ArchiveTasks.exceptions.ArchiveNotFoundException;
import com.niit.ArchiveTasks.exceptions.TaskAlreadyExistsException;
import com.niit.ArchiveTasks.model.Archive;
import com.niit.ArchiveTasks.model.Task;

import java.util.List;

public interface ArchiveService {
    Archive saveArchive(Archive archive,String userEmail) throws ArchiveAlreadyExistsException;
    Archive saveOfficialTaskToArchive(String userEmail , Task task) throws ArchiveNotFoundException, TaskAlreadyExistsException;
     Archive savePersonalTaskToArchive(String userEmail , Task task) throws ArchiveNotFoundException, TaskAlreadyExistsException;
    List<Archive> getAllArchives() throws Exception;
    List<Task>    getOfficialTasklistByUserEmail(String userEmail)throws ArchiveNotFoundException;
     List<Task> getPersonalTasklistByUserEmail(String userEmail) throws ArchiveNotFoundException;
    Archive getArchiveUserEmail(String userEmail) throws ArchiveNotFoundException;

}
