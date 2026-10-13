package ni.edu.uam.gestion_productos.controller;

import ni.edu.uam.gestion_productos.dto.LoginRequest;
import ni.edu.uam.gestion_productos.dto.LoginResponse;
import ni.edu.uam.gestion_productos.service.TokenService;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokens;

    public AuthController(AuthenticationManager manager,
                          TokenService tokens) {
        this.manager = manager;
        this.tokens = tokens;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest datos) {
        Authentication auth = manager.authenticate(
            new UsernamePasswordAuthenticationToken(
                datos.username(), datos.password()));
        return ResponseEntity.ok(
            new LoginResponse(tokens.crearToken(auth), "Bearer"));
    }

    // Reto adicional: datos del usuario identificado en el JWT.
    // SecurityConfig exige token para esta ruta, así que no acepta anónimos.
    @GetMapping("/perfil")
    public Map<String, Object> perfil(@AuthenticationPrincipal Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("roles");
        return Map.of(
            "usuario", jwt.getSubject(),
            "roles", roles,
            "expira", jwt.getExpiresAt().toString());
    }
}
