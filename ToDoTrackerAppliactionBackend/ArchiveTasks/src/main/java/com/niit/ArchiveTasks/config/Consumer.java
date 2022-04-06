package com.niit.ArchiveTasks.config;

import com.niit.ArchiveTasks.exceptions.ArchiveAlreadyExistsException;
import com.niit.ArchiveTasks.exceptions.ArchiveNotFoundException;
import com.niit.ArchiveTasks.exceptions.TaskAlreadyExistsException;
import com.niit.ArchiveTasks.model.Archive;
import com.niit.ArchiveTasks.model.ArchiveDTO;
import com.niit.ArchiveTasks.respository.ArchiveRespository;
import com.niit.ArchiveTasks.service.ArchiveServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private ArchiveServiceImpl archiveService;

    @Autowired
    private ArchiveRespository archiveRespository;
    @RabbitListener(queues="archive_queue")
    public void getArchiveDtoFromRabbitMq(ArchiveDTO archiveDto) throws ArchiveAlreadyExistsException, TaskAlreadyExistsException
    {
        System.out.println("GargasArchive"+archiveDto);
        String userEmail=archiveDto.getUserEmail();
        try {
            if(archiveRespository.findByUserEmail(archiveDto.getUserEmail())==null)
           {System.out.println("inside "+archiveDto.getUserEmail()+archiveRespository.findByUserEmail(archiveDto.getUserEmail()));

            Archive archive=new Archive();
            archiveService.saveArchive(archive,userEmail);
            }
            if(archiveDto.getTaskType().equalsIgnoreCase("Official Task"))
            archiveService.saveOfficialTaskToArchive(userEmail,archiveDto.getTask());
            else if(archiveDto.getTaskType().equalsIgnoreCase("Personal Task"))
                archiveService.savePersonalTaskToArchive(userEmail,archiveDto.getTask());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       }


  }

