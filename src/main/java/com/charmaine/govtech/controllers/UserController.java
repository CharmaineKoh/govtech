package com.charmaine.govtech.controllers;

import com.charmaine.govtech.data.UserService;
import com.charmaine.govtech.domains.Results;
import com.charmaine.govtech.domains.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public ResponseEntity<Results> getUsers(
            @RequestParam(name = "min", required = false, defaultValue = "0.0") String min,
            @RequestParam(name = "max", required = false, defaultValue = "4000.0") String max,
            @RequestParam(name = "offset", required = false, defaultValue = "0") String offset,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "sort", required = false) String sort,
            Model model) {

        List<User> users = userService.getUsers();

        users = userService.filterSalary(users, Double.parseDouble(min), Double.parseDouble(max));

        if (sort != null) {
            users = userService.sortResults(users, sort.toLowerCase());
        }

        users = userService.offset(users, Integer.parseInt(offset));

        if (limit != null) {
            users = userService.limitResults(users, Integer.parseInt(limit));
        }

        return new ResponseEntity<Results>(new Results(users), HttpStatus.OK);
    }

    @PostMapping(value = ("/upload"), headers = ("content-type=multipart/form-data"), produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {

        // parse CSV file to create a list of `User` objects
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            // create csv bean reader
            CsvToBean<User> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // convert `CsvToBean` object to list of users
            List<User> newUsers = csvToBean.parse();
            userService.addAllUsers(newUsers);

        } catch (Exception ex) {
            return new ResponseEntity<String>(
                    "{\"failure\": 0 " +
                            "\"error\": An error occurred while processing the CSV file.}",
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"success\": 1}");
    }
}
