package com.example.photologger.photo.repository;

import com.example.photologger.photo.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBUserRepository implements UserRepository {
    private static Map<Integer, User> store = new HashMap<>();
    private static int sequence = 0;
    public User save(User user) {
        user.setIdx(++sequence);
        store.put(user.getIdx(), user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Optional<User> findByNickName(String nicName) {
        return store.values().stream()
                .filter(user -> user.getNicName().equals(nicName))
                .findAny();
    }

    @Override
    public User login(User user) {
        return null;
    }
}
