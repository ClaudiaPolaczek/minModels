package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.configurations.jwt.JwtUtils;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.*;
import pl.polsl.polaczek.models.dto.auth.*;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;
import pl.polsl.polaczek.models.services.SurveyService;
import pl.polsl.polaczek.models.services.UserDetailsImpl;
import pl.polsl.polaczek.models.configurations.Error;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    private final ModelRepository modelRepository;
    private final PhotographerRepository photographerRepository;
    private final PasswordEncoder encoder;
    private final SurveyService surveyService;

    @Autowired
    public AuthController(final ModelRepository modelRepository,
                          final PhotographerRepository photographerRepository,
                          final SurveyService surveyService,
                          final PasswordEncoder encoder) {
        this.modelRepository = modelRepository;
        this.photographerRepository = photographerRepository;
        this.encoder = encoder;
        this.surveyService = surveyService;
    }

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String role = userDetails.getAuthorities().toString();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                role));
    }

    @PostMapping("/m/signup")
    public ResponseEntity registerModelUser(@Valid @RequestBody NewModelDto newModelDto) {

        if(newModelDto == null)  {
            new Error("Please provide an input");
            return ResponseEntity.ok(new MessageResponse("User not registered"));
        }

        if (userRepository.existsByUsername(newModelDto.getUsername())) {
            return ResponseEntity .badRequest()
                    .body( new BadRequestException("User", "username", newModelDto.getUsername(), "already exist"));
        }

        if(newModelDto.getGender() != 'M' && newModelDto.getGender() != 'W')
            throw new BadRequestException("Model", "gender", newModelDto.getGender().toString(),
                    "Model's gender should be either W or M");

        Survey survey = surveyService.add(newModelDto);

        final Model model = new Model(survey);

        final User user = new User(newModelDto.getUsername(),
                encoder.encode(newModelDto.getPassword()), URole.MODEL);

        userRepository.save(user);
        model.setUser(user);
        modelRepository.save(model);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/p/signup")
    public ResponseEntity registerPhotographerUser(@Valid @RequestBody NewPhotographerDto newPhotographerDto) {

        if (userRepository.existsByUsername(newPhotographerDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new BadRequestException("User", "username", newPhotographerDto.getUsername(), "already exist"));
        }

        if(newPhotographerDto.getGender() != 'M' && newPhotographerDto.getGender() != 'W')
            throw new BadRequestException("Photographer", "gender", newPhotographerDto.getGender().toString(),
                    "Model's gender should be either W or M");

        Survey survey = surveyService.add(newPhotographerDto);

        final Photographer photographer = new Photographer(survey);

        User user = new User(newPhotographerDto.getUsername(),
                encoder.encode(newPhotographerDto.getPassword()), URole.PHOTOGRAPHER);

        userRepository.save(user);
        photographer.setUser(user);
        photographerRepository.save(photographer);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/a/signup")
    public ResponseEntity registerAdmin(@Valid @RequestBody NewAdmin newAdmin) {

        if (userRepository.existsByUsername(newAdmin.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body( new BadRequestException("User", "username", newAdmin.getUsername(), "already exist"));
        }

        User user = new User(newAdmin.getUsername(),
                encoder.encode(newAdmin.getPassword()), URole.ADMIN
        );

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PatchMapping("/password")
    public ResponseEntity changePassword(@Valid @RequestBody NewAdmin newAdmin) {

        User user = userRepository.findById(newAdmin.getUsername())
                .orElseThrow(() -> new EntityDoesNotExistException("User", "username", newAdmin.getPassword()));

        user.setPassword(encoder.encode(newAdmin.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
    }
}
