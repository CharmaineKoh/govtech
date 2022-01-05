package com.charmaine.govtech.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.charmaine.govtech.domains.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<User> filterSalary(List<User> results, double min, double max) {
        return results.stream()
                .filter(u -> u.getSalary() >= min &&
                        u.getSalary() <= max)
                .collect(Collectors.toList());
    }

    public List<User> offset(List<User> results, int offset) {
        return results.stream()
                .skip(offset)
                .collect(Collectors.toList());
    }

    public List<User> limitResults(List<User> results, int limit) {
        return results.stream().limit(limit).collect(Collectors.toList());
    }

    public List<User> sortResults(List<User> results, String sort) {
        if (sort.equals("name")) {
            Collections.sort(results, Comparator.comparing(u -> u.getName()));
        } else if (sort.equals("salary")) {
            Collections.sort(results, Comparator.comparing(u -> u.getSalary()));
        }
        return results;
    }

    public void addAllUsers(List<User> users) {
        List<User> filtered = users.stream().filter(u -> u.getSalary() >= 0).collect(Collectors.toList());
        userRepository.saveAll(filtered);
    }
}