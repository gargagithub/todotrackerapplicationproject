package com.niit.ArchiveTasks;


import com.niit.ArchiveTasks.exceptions.ArchiveAlreadyExistsException;
import com.niit.ArchiveTasks.exceptions.ArchiveNotFoundException;
import com.niit.ArchiveTasks.exceptions.TaskAlreadyExistsException;
import com.niit.ArchiveTasks.model.Archive;
import com.niit.ArchiveTasks.model.BasedOnPriority;
import com.niit.ArchiveTasks.model.Task;
import com.niit.ArchiveTasks.respository.ArchiveRespository;
import com.niit.ArchiveTasks.service.ArchiveService;
import com.niit.ArchiveTasks.service.ArchiveServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArchiveServiceTest {

    @Mock
    private ArchiveRespository archiveRespository;

    @InjectMocks
    private ArchiveServiceImpl archiveService;

    private Archive archive, archive2, archive1, archive3;
    private Task task;
    private Task task2, task3;
    private List<Task> personalTaskList1,personalTaskList2,personalTaskList3,officialTaskList1,officialTaskList2,officialTaskList3;
    private List<Archive> listOfArchive;

    @BeforeEach
    public void setUp() {
        java.util.Date dt = convertionOfdate("2022-03-21");

        task = new Task(1, "testing", "active", "doing positive and negative test cases", BasedOnPriority.LOW, dt);
        task2 = new Task(2, "testing2", "active", "doing positive and negative test cases", BasedOnPriority.MEDIUM, dt);
        task3 = new Task(3, "testing3", "notActive", "doing positive and negative test cases", BasedOnPriority.HIGH, dt);
        officialTaskList1 = new ArrayList<>(Arrays.asList(task));
        officialTaskList2 = new ArrayList<>(Arrays.asList(task, task2));
        officialTaskList3 = new ArrayList<>(Arrays.asList(task, task2, task3));
        personalTaskList1 = new ArrayList<>(Arrays.asList(task));
        personalTaskList2= new ArrayList<>(Arrays.asList(task, task2));
        personalTaskList3 = new ArrayList<>(Arrays.asList(task, task2, task3));
        archive = new Archive(1, "saisandeep@gmail.com", null,null);
        archive1 = new Archive(1, "saisandeep@gmail.com", officialTaskList1,null);
        archive2 = new Archive(1, "saisandeep@gmail.com", null,officialTaskList1);
        archive3 = new Archive(1, "saisandeep@gmail.com", officialTaskList3,personalTaskList3);
        listOfArchive = new ArrayList<>(Arrays.asList(archive, archive1));
    }

    @AfterEach
    public void tearDown() {
        archive = null;
        archive2 = null;
        officialTaskList1 = null;
        officialTaskList2 = null;
        officialTaskList2=null;
        personalTaskList1=null;
        personalTaskList2=null;
        personalTaskList3=null;
        task = null;
        task2 = null;
    }

    @Test
    public void saveArchivePositiveTestCase() throws ArchiveAlreadyExistsException {
        when(archiveRespository.findById(archive.getArchiveId())).thenReturn(Optional.ofNullable(null));
        when(archiveRespository.save(archive)).thenReturn(archive);
        assertEquals(archive, archiveService.saveArchive(archive,archive.getUserEmail()));
        verify(archiveRespository, times(1)).findById(archive.getArchiveId());
        verify(archiveRespository, times(1)).save(archive);
    }

    @Test
    public void saveArchiveNegativeTestCase() {
        when(archiveRespository.findById(archive.getArchiveId())).thenReturn(Optional.ofNullable(archive));
        assertThrows(ArchiveAlreadyExistsException.class, () -> archiveService.saveArchive(archive,archive.getUserEmail()));
        verify(archiveRespository, times(1)).findById(archive.getArchiveId());
        verify(archiveRespository, times(0)).save(archive);
    }

    @Test
    public void saveOfficialTaskToArchivePositiveTestCase() throws ArchiveNotFoundException, TaskAlreadyExistsException {
        when(archiveRespository.findByUserEmail(archive.getUserEmail())).thenReturn(archive);
        when(archiveRespository.save(archive)).thenReturn(archive);
        assertEquals(archive1, archiveService.saveOfficialTaskToArchive(archive.getUserEmail(), task));
        verify(archiveRespository, times(2)).findByUserEmail(archive.getUserEmail());
        verify(archiveRespository, times(1)).save(archive);
    }

    @Test
    public void saveOfficialTaskToArchiveNegativeTestCase() {
        when(archiveRespository.findByUserEmail(archive.getUserEmail())).thenReturn(null);
        assertThrows(ArchiveNotFoundException.class, () -> archiveService.saveOfficialTaskToArchive(archive1.getUserEmail(), task));
        when(archiveRespository.findByUserEmail("saisandeep@gmail.com")).thenReturn(archive1);
        assertThrows(TaskAlreadyExistsException.class, () -> archiveService.saveOfficialTaskToArchive(archive1.getUserEmail(), task));
        verify(archiveRespository, times(3)).findByUserEmail(archive.getUserEmail());
        verify(archiveRespository, times(0)).save(archive);
    }
    @Test
    public void savePersonalTaskToArchivePositiveTestCase() throws ArchiveNotFoundException, TaskAlreadyExistsException {
        when(archiveRespository.findByUserEmail(archive.getUserEmail())).thenReturn(archive);
        when(archiveRespository.save(archive)).thenReturn(archive);
        assertEquals(archive2, archiveService.savePersonalTaskToArchive(archive.getUserEmail(), task));
        verify(archiveRespository, times(2)).findByUserEmail(archive.getUserEmail());
        verify(archiveRespository, times(1)).save(archive);
    }

    @Test
    public void savePersonalTaskToArchiveNegativeTestCase() {
        when(archiveRespository.findByUserEmail(archive.getUserEmail())).thenReturn(null);
        assertThrows(ArchiveNotFoundException.class, () -> archiveService.savePersonalTaskToArchive(archive2.getUserEmail(), task));
        when(archiveRespository.findByUserEmail(archive2.getUserEmail())).thenReturn(archive2);
        assertThrows(TaskAlreadyExistsException.class, () -> archiveService.savePersonalTaskToArchive(archive2.getUserEmail(), task));
        verify(archiveRespository, times(3)).findByUserEmail(archive2.getUserEmail());
        verify(archiveRespository, times(0)).save(archive);
    }

    @Test
    public void getAllArchivePositiveTestCase() throws Exception {
        when(archiveRespository.findAll()).thenReturn(listOfArchive);
        assertEquals(listOfArchive, archiveService.getAllArchives());
        verify(archiveRespository, times(1)).findAll();
    }

    @Test
    public void getAllArchiveNegativeTestCase() throws Exception {
        when(archiveRespository.findAll()).thenReturn(null);
        assertEquals(null, archiveService.getAllArchives());
        verify(archiveRespository, times(1)).findAll();
    }

    @Test
    public void getOfficialTaskListByUserEmailPositiveTestCase() throws ArchiveNotFoundException {
        when(archiveRespository.findByUserEmail(archive1.getUserEmail())).thenReturn(archive1);
        assertEquals(officialTaskList1, archiveService.getOfficialTasklistByUserEmail(archive1.getUserEmail()));
        verify(archiveRespository, times(2)).findByUserEmail(archive1.getUserEmail());

    }

    @Test
    public void getOfficialTaskListByUserEmailNegativeTestCase() {
        when(archiveRespository.findByUserEmail(archive1.getUserEmail())).thenReturn(null);
        assertThrows(ArchiveNotFoundException.class, () -> archiveService.getOfficialTasklistByUserEmail(archive1.getUserEmail()));
        verify(archiveRespository, times(1)).findByUserEmail(archive1.getUserEmail());
    }
    @Test
    public void getPersonalTasklistByUserEmailPositiveTestCase() throws ArchiveNotFoundException {
        when(archiveRespository.findByUserEmail(archive2.getUserEmail())).thenReturn(archive2);
        assertEquals(officialTaskList1, archiveService.getPersonalTasklistByUserEmail(archive2.getUserEmail()));
        verify(archiveRespository, times(2)).findByUserEmail(archive2.getUserEmail());

    }

    @Test
    public void getPersonalTasklistByUserEmailNegativeTestCase() {
        when(archiveRespository.findByUserEmail(archive2.getUserEmail())).thenReturn(null);
        assertThrows(ArchiveNotFoundException.class, () -> archiveService.getPersonalTasklistByUserEmail(archive2.getUserEmail()));
        verify(archiveRespository, times(1)).findByUserEmail(archive1.getUserEmail());
    }

    @Test
    public void getArchiveUserEmailPositiveTestCase() throws Exception {
        when(archiveRespository.findByUserEmail(archive.getUserEmail())).thenReturn(archive);
        assertEquals(true, archiveService.getArchiveUserEmail(archive.getUserEmail()));
        verify(archiveRespository, times(1)).findByUserEmail(archive.getUserEmail());
    }

    @Test
    public void getArchiveUserEmailNegativeTestCase() throws Exception {
        when(archiveRespository.findByUserEmail(archive.getUserEmail())).thenReturn(null);
        assertEquals(false, archiveService.getArchiveUserEmail(archive.getUserEmail()));
        verify(archiveRespository, times(1)).findByUserEmail(archive.getUserEmail());
    }

    public Date convertionOfdate(String date) {
        java.util.Date dt = null;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return dt;
    }

}
