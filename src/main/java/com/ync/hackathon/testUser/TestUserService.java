package com.ync.hackathon.testUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestUserService {

    private final TestUserRepository testUserRepository;

    @Autowired
    public TestUserService(TestUserRepository testUserRepository) {
        this.testUserRepository = testUserRepository;
    }

    public Optional<TestUser> finByUsername(String username) {
        return testUserRepository.findByUsername(username);
    }

    public TestUser saveUser(TestUser testUser) {
        return testUserRepository.save(testUser);
    }
}
