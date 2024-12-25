package ba.edu.ibu.movieswatchlist.rest.controllers;

import ba.edu.ibu.movieswatchlist.core.model.User;
import ba.edu.ibu.movieswatchlist.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Long> addUser(@RequestBody String email) {
        Long userId = userService.addOrGetUserId(email);
        return ResponseEntity.ok(userId);
    }

    @PutMapping("/change-notification-status/{id}")
    public ResponseEntity<Void> changeNotificationStatus(@PathVariable Long id) {
        userService.toggleNotificationStatus(id);
        return ResponseEntity.noContent().build();
    }
}