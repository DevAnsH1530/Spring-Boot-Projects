package com.javaProjects.taskMaster;
import com.javaProjects.taskMaster.model.Users;
import com.javaProjects.taskMaster.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@SpringBootTest
class TaskMasterApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	public void testGetUser() {
		String userName = "Test";
		List<Users> res = userRepo.findByUserNameContaining(userName);
		System.out.println(res);
		Assertions.assertTrue(res!=null);

	}

}
