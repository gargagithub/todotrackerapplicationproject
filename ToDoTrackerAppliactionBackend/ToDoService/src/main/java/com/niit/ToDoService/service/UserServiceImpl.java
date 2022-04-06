package com.niit.ToDoService.service;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import com.niit.ToDoService.config.Producer;
import com.niit.ToDoService.domain.ArchiveDTO;
import com.niit.ToDoService.domain.Task;
import com.niit.ToDoService.domain.User;
import com.niit.ToDoService.exceptions.TaskAlreadyExistException;
import com.niit.ToDoService.exceptions.TaskNotFoundException;
import com.niit.ToDoService.exceptions.UserAlreadyExistException;
import com.niit.ToDoService.exceptions.UserNotFoundException;
import com.niit.ToDoService.proxy.UserProxy;
import com.niit.ToDoService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private UserProxy userProxy;

    @Autowired
    Producer producer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProxy userProxy) {
        this.userRepository = userRepository;
        this.userProxy = userProxy;
    }

    @Override
    public User registerNewUser(User user) throws UserAlreadyExistException {
        if (userRepository.findById(user.getUserEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        ResponseEntity responseEntity = userProxy.saveUser(user);
        return userRepository.save(user);
    }

    @Override // for saving official tasks to user
    public User saveOfficialTask(String userEmail, Task task) throws UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findById(userEmail).get();
        if (user.getOfficialTask() == null) {
            user.setOfficialTask(Arrays.asList(task));
        } else {
            List<Task> tasks = user.getOfficialTask();
            tasks.add(task);
            user.setOfficialTask(tasks);
        }
        return userRepository.save(user);
    }

    @Override // for saving personal tasks to user
    public User savePersonalTask(String userEmail, Task task) throws UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userRepository.findById(userEmail).get();

        if (user.getPersonalTask() == null) {
            user.setPersonalTask(Arrays.asList(task));
        } else {
            List<Task> tasks = user.getPersonalTask();
            tasks.add(task);
            user.setPersonalTask(tasks);
        }
        return userRepository.save(user);

    }

    @Override
    public boolean deleteOfficialTask(String userEmail, int taskId) throws UserNotFoundException, TaskNotFoundException {
        boolean taskIsAvailable;
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        List<Task> taskList = user.getOfficialTask();
        taskIsAvailable = taskList.removeIf(p -> p.getTaskId() == taskId);
        if (!taskIsAvailable) {
            throw new TaskNotFoundException();
        }
        user.setOfficialTask(taskList);
        userRepository.save(user);
        return taskIsAvailable;
    }

    @Override
    public boolean deletePersonalTask(String userEmail, int taskId) throws UserNotFoundException, TaskNotFoundException {
        boolean taskIsAvailable;
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        List<Task> taskList = user.getPersonalTask();
        taskIsAvailable = taskList.removeIf(p -> p.getTaskId() == taskId);
        if (!taskIsAvailable) {
            throw new TaskNotFoundException();

        }
        user.setPersonalTask(taskList);
        userRepository.save(user);
        return taskIsAvailable;
    }

    @Override
    public User updateOfficialTask(String userEmail, Task task, int taskId) throws UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getOfficialTask() == null) {
            task.setTaskId(taskId);
            user.setOfficialTask(Arrays.asList(task));
        } else {
            List<Task> taskList = user.getOfficialTask();
            Task task1 = taskList.stream().filter(a -> a.getTaskId() == taskId).collect(Collectors.toList()).get(0);
            int index = taskList.indexOf(task1);
            task.setTaskId(taskId);
            taskList.set(index, task);
            user.setOfficialTask(taskList);
        }

        return userRepository.save(user);
    }

    @Override
    public User updatePersonalTask(String userEmail, Task task, int taskId) throws UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getPersonalTask() == null) {
            task.setTaskId(taskId);
            user.setPersonalTask(Arrays.asList(task));
        } else {
            List<Task> taskList = user.getPersonalTask();
            Task task1 = taskList.stream().filter(a -> a.getTaskId() == taskId).collect(Collectors.toList()).get(0);
            int index = taskList.indexOf(task1);
            task.setTaskId(taskId);
            taskList.set(index, task);
            user.setPersonalTask(taskList);
        }

        return userRepository.save(user);
    }

    @Override
    public List<Task> getAllOfficialTasks(String userEmail) throws UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getOfficialTask() == null) {
            return null;
        } else {
            return user.getOfficialTask();
        }
    }

    @Override
    public List<Task> getAllPersonalTasks(String userEmail) throws UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getPersonalTask() == null) {
            return null;
        } else {
            return user.getPersonalTask();
        }
    }

    @Override
    public List<Task> sortByPriorityOfOfficialTask(String userEmail) throws UserNotFoundException {
        getAllOfficialTasks(userEmail);
        List<Task> sortedList;
        sortedList = getAllOfficialTasks(userEmail).stream().sorted(Comparator.comparing(Task::getTaskPriority)).collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public List<Task> sortByPriorityOfPersonalTask(String userEmail) throws UserNotFoundException {
        getAllPersonalTasks(userEmail);
        List<Task> sortedList;
        sortedList = getAllPersonalTasks(userEmail).stream().sorted(Comparator.comparing(Task::getTaskPriority)).collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public Task getOfficialTaskById(int taskId, String userEmail) throws TaskNotFoundException, UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getOfficialTask() == null) {
            throw new TaskNotFoundException();
        } else {
            List<Task> taskList = user.getOfficialTask();
            Task task1 = taskList.stream().filter(a -> a.getTaskId() == taskId).collect(Collectors.toList()).get(0);
            return task1;
        }

    }

    @Override
    public Task getPersonalTaskById(int taskId, String userEmail) throws TaskNotFoundException, UserNotFoundException {
        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getPersonalTask() == null) {
            throw new TaskNotFoundException();
        } else {
            List<Task> taskList = user.getPersonalTask();
            Task task1 = taskList.stream().filter(a -> a.getTaskId() == taskId).collect(Collectors.toList()).get(0);
            return task1;
        }

    }

  public boolean completeOfficialTask(String userEmail, int taskId) throws UserNotFoundException,TaskNotFoundException{
    boolean flag=false;
    if(userRepository.findById(userEmail).isEmpty()){
      throw new UserNotFoundException();
    }
    User user=userRepository.findById(userEmail).get();
    if(user.getOfficialTask()==null){
      throw new TaskNotFoundException();
    }
    else {
      List<Task> taskList=user.getOfficialTask();
      Task task1 = taskList.stream().filter(a -> a.getTaskId() == taskId).collect(Collectors.toList()).get(0);
      if (task1==null){
        throw new TaskNotFoundException();
      }
      else{
        ArchiveDTO archivedto=new ArchiveDTO();
        archivedto.setTaskType("Official Task");
        archivedto.setUserEmail(userEmail);
        archivedto.setTask(task1);
        System.out.println(archivedto.getTask());
        producer.sendMessageToRabbitMq(archivedto);
        deleteOfficialTask(userEmail,taskId);
        flag=true;
      }
    }
    return flag;
  }

  public boolean completePersonalTask(String userEmail, int taskId) throws UserNotFoundException,TaskNotFoundException{
    boolean flag=false;
    if(userRepository.findById(userEmail).isEmpty()){
      throw new UserNotFoundException();
    }
    User user=userRepository.findById(userEmail).get();
    if(user.getPersonalTask()==null){
      throw new TaskNotFoundException();
    }
    else {
      List<Task> taskList=user.getPersonalTask();
      Task task1 = taskList.stream().filter(a -> a.getTaskId() == taskId).collect(Collectors.toList()).get(0);
      if (task1==null){
        throw new TaskNotFoundException();
      }
      else{
        ArchiveDTO archivedto=new ArchiveDTO();
        archivedto.setTaskType("Personal Task");
        archivedto.setUserEmail(userEmail);
        archivedto.setTask(task1);
        producer.sendMessageToRabbitMq(archivedto);
        deletePersonalTask(userEmail,taskId);
        flag=true;
      }
    }
    return flag;
  }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        if (userRepository.findAll() == null) {
            throw new UserNotFoundException();
        }
        return userRepository.findAll();
    }

    @Override
    public List<Task> getAllTasksNotificationsList(String userEmail) throws UserNotFoundException, ParseException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String str = formatter.format(date);
        List<Task> notificationList=new ArrayList<Task>();
        System.out.println("---C"+str);

        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getOfficialTask() == null && user.getPersonalTask()==null) {
            return notificationList;
        } else {
            List<Task> personaltasks = user.getPersonalTask();
            List<Task> officialtasks = user.getOfficialTask();

            for (Task task : officialtasks )
            {
                Date newDate = new Date(task.getTaskDeadline().getTime() - TimeUnit.HOURS.toMillis(6)+TimeUnit.MINUTES.toMillis(30));
                String str1=formatter.format(newDate);

                System.out.println("O"+str1);
                if(str1.compareTo(str)<=0)
                notificationList.add(task);
            }
            for (Task task : personaltasks )
            {   Date newDate = new Date(task.getTaskDeadline().getTime() - TimeUnit.HOURS.toMillis(6)+TimeUnit.MINUTES.toMillis(30));
                String str1=formatter.format(newDate);
                System.out.println("P"+str1);
                if(str1.compareTo(str)<=0)
                notificationList.add(task);
            }

            return notificationList;
        }
    }

    @Override
    public List<Task> getPendingOfficialTaskList(String userEmail) throws UserNotFoundException, ParseException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String str = formatter.format(date);
        List<Task> pendingOfficialTaskList = new ArrayList<Task>();
        System.out.println("---C" + str);

        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getOfficialTask() == null) {
            return pendingOfficialTaskList;
        } else {
            List<Task> officialtasks = user.getOfficialTask();

            for (Task task : officialtasks) {
                Date newDate = new Date(task.getTaskDeadline().getTime() - TimeUnit.HOURS.toMillis(6) + TimeUnit.MINUTES.toMillis(30));
                String str1 = formatter.format(newDate);

                System.out.println("O" + str1);
                if (str1.compareTo(str) <= 0)
                    pendingOfficialTaskList.add(task);
            }
        }
        return  pendingOfficialTaskList;
    }

    @Override
    public List<Task> getPendingPersonalTaskList(String userEmail) throws UserNotFoundException, ParseException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String str = formatter.format(date);
        List<Task> pendingPersonalTaskList = new ArrayList<Task>();
        System.out.println("---C" + str);

        if (userRepository.findById(userEmail).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userEmail).get();
        if (user.getPersonalTask() == null) {
            return pendingPersonalTaskList;
        } else {
            List<Task> personaltasks = user.getPersonalTask();

            for (Task task : personaltasks) {
                Date newDate = new Date(task.getTaskDeadline().getTime() - TimeUnit.HOURS.toMillis(6) + TimeUnit.MINUTES.toMillis(30));
                String str1 = formatter.format(newDate);

                System.out.println("O" + str1);
                if (str1.compareTo(str) <= 0)
                    pendingPersonalTaskList.add(task);
            }
        }
        return  pendingPersonalTaskList;
    }

    @Override
    public User updateUser(User user, String userEmail) throws UserNotFoundException
    {
        if (userRepository.findById(userEmail).isPresent()) {
            User user1=userRepository.findById(userEmail).get();
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setPassword(user.getPassword());
            user1.setMobileNo(user.getMobileNo());
            ResponseEntity responseEntity=userProxy.updateUser(user,userEmail);
            return userRepository.save(user1);
        }
        throw new UserNotFoundException();

    }
}
