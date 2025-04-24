package pl.radekpalka.flashcardsApp.conroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.radekpalka.flashcardsApp.dto.UserLoginDto;
import pl.radekpalka.flashcardsApp.dto.UserRegistrationDto;
import pl.radekpalka.flashcardsApp.model.User;
import pl.radekpalka.flashcardsApp.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto dto){
        if (userRepository.existsByLogin(dto.getLogin())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login already taken");
        }

        User user = new User(dto.getLogin(), passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto){
        Optional<User> userOptional = userRepository.findByUser(dto.getLogin());

        if (userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong login");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong login or password");
        }

        return ResponseEntity.ok("You are logged succesfuly");
    }
}
