//package com.example.userauthentication;
//
//
//import com.example.userauthentication.model.User;
//import com.example.userauthentication.repository.UserRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(MockitoExtension.class)
//@DataJdbcTest
//public class UserRepositoryTest {
//
//
//    @Autowired
//    private UserRepository userRepository;
//    private User user;
//
//    @BeforeEach
//    public void setUp()
//    {
//        user.setUserEmail("akashmittal@gmail.com");
//        user.setFirstName("Akash");
//        user.setLastName("mittal");
//        user.setPassword("sandeep123");
//        user.setMobileNo("7330843717");
//    }
//
//    @AfterEach
//    public void tearDown()
//    {
//     user=null;
//     userRepository=null;
//    }
//
//
//    @Test
//    public void saveUser(){
//        userRepository.save(user);
//        User user1=userRepository.findById(user.getUserEmail()).get();
//        assertNotNull(user1);
//        assertEquals(user.getUserEmail(),user1.getUserEmail());
//    }
//
//    @Test
//    public void findByUserEmailAndPassword(){
//        userRepository.insert(user);
//        User user1=userRepository.findById(user.getUserEmail()).get();
//        userRepository.delete(user1);
//        assertEquals(Optional.empty(),userRepository.findById(user.getUserEmail()));
//    }
//
//    @Test
//    public  void getAllUser(){
//        userRepository.insert(user);
//        List<User> list=userRepository.findAll();
//        assertEquals(1,list.size());
//        assertEquals("Akashmittal@gmail.com",list.get(0).getUserEmail());
//    }
//
//}
