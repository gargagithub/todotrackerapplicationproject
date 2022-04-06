package com.niit.ArchiveTasks.controller;



import com.niit.ArchiveTasks.exceptions.ArchiveAlreadyExistsException;
import com.niit.ArchiveTasks.exceptions.ArchiveNotFoundException;
import com.niit.ArchiveTasks.exceptions.TaskAlreadyExistsException;
import com.niit.ArchiveTasks.model.Archive;
import com.niit.ArchiveTasks.model.Task;
import com.niit.ArchiveTasks.service.ArchiveService;
import com.niit.ArchiveTasks.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("api/v3/")
public class ArchiveController {
    ArchiveService archiveService;
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public ArchiveController( ArchiveService archiveService,SequenceGeneratorService sequenceGeneratorService) {
        this.archiveService = archiveService;
        this.sequenceGeneratorService=sequenceGeneratorService;
    }

  // @PostMapping("/archive")
/*   public ResponseEntity<?> saveArchive(@RequestBody Archive archive) throws ArchiveAlreadyExistsException {
       try {
            archive.setArchiveId(sequenceGeneratorService.getSequenceNumber(archive.SEQUENCE_NAME));
      //      return new ResponseEntity<>(archiveService.saveArchive(archive,archive.getUserEmail()), HttpStatus.CREATED);
        } catch (ArchiveAlreadyExistsException e) {
            throw new ArchiveAlreadyExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }*/

   @PostMapping("/archive/{userEmail}")
    public ResponseEntity<?> saveTaskToArchive(@RequestBody Task task, @PathVariable String userEmail) throws ArchiveNotFoundException, TaskAlreadyExistsException {
       try {
           System.out.println("printing 0");
            return new ResponseEntity<>(archiveService.saveOfficialTaskToArchive(userEmail,task), HttpStatus.CREATED);
        } catch (ArchiveNotFoundException e) {
            throw new ArchiveNotFoundException();
        } catch (TaskAlreadyExistsException e){
            throw new TaskAlreadyExistsException();
       } catch (Exception e) {
           System.out.println("printing x");
            return new ResponseEntity<>("try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/archive")
    public ResponseEntity<?> getAllContents()
    {
        try{
            return new ResponseEntity<>(archiveService.getAllArchives(),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return  new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/archive/officialTask/{userEmail}")
    public ResponseEntity<?> getOfficialTasklistByUserEmail(@PathVariable String userEmail)throws ArchiveNotFoundException
    {
        try{
            return new ResponseEntity<>(archiveService.getOfficialTasklistByUserEmail(userEmail),HttpStatus.OK);
        }
        catch (ArchiveNotFoundException e){
            throw new ArchiveNotFoundException();
        }
        catch(Exception e)
        {
            return  new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/archive/personalTask/{userEmail}")
    public ResponseEntity<?> getPersonalTasklistByUserEmail(@PathVariable String userEmail)throws ArchiveNotFoundException
    {
        try{
            return new ResponseEntity<>(archiveService.getPersonalTasklistByUserEmail(userEmail),HttpStatus.OK);
        }
        catch (ArchiveNotFoundException e){
            throw new ArchiveNotFoundException();
        }
        catch(Exception e)
        {
            return  new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/archive/{userEmail}")
    public ResponseEntity<?> getArchivebymail(@PathVariable String userEmail)
    {
        try{
            return new ResponseEntity<>(archiveService.getArchiveUserEmail(userEmail),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return  new ResponseEntity<>("try after some time",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("/archive/personalTask/{archiveId}/{taskId}")
//    public ResponseEntity<?> deletePersonalTask(@PathVariable int archiveId,@PathVariable int taskId) throws ArchiveNotFoundException{
//        try{
//           return new ResponseEntity(archiveService.deletePersonalTask(archiveId,taskId), HttpStatus.OK);
//        }
//        catch (ArchiveNotFoundException u){
//            throw new ArchiveNotFoundException();
//        }
//
//    }
}



