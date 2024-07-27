package com.ync.hackathon.testUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class TestUserController {

    private final TestUserService testUserService;

    @Autowired
    public TestUserController(TestUserService testUserService) {
        this.testUserService = testUserService;
    }

    @GetMapping("/{username}")
    public TestUser getUser(@PathVariable String username) throws UserNotFoundException {
        Optional<TestUser> testUser = testUserService.finByUsername(username);

        if (testUser.isPresent()) {
            return testUser.get();
        }
        else {
            throw new UserNotFoundException();
        }

    }
}
