package com.dharmesh.bookstore.catalog.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog")
public class MyController {

    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Running now lets check..!");
    }
}

/*
Open your project in IntelliJ IDEA.
Go to VCS (Version Control System) in the top menu.
Select Enable Version Control Integration.
In the dialog box, choose Git from the dropdown menu and click OK.
This initializes a local Git repository in your project.
* */
