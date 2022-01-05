package com.charmaine.govtech.domains;

import java.util.List;

public class Results {
    List<User> results;

    public Results(List<User> results) {
        this.results = results;
    }

    public List<User> getResults() {
        return results;
    }
}
