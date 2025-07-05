package com.flowired.saludpol.controller;

import com.flowired.saludpol.dto.LoginRequest;
import com.flowired.saludpol.dto.LoginResponse;
import com.flowired.saludpol.dto.UserProfile;
import com.flowired.saludpol.model.Usuario;
import com.flowired.saludpol.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@Tag(name = "login", description = "Autenticación de usuarios y obtención de perfil")
public class LoginController {

   /* @Operation(summary = "Iniciar sesión del usuario")
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        response.setMessage("Autenticación simulada exitosa");
        response.setPerfil("Usuario común");
        response.setRol("Administrador");
        response.setRedirectUrl("/dashboard");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener perfil del usuario autenticado")
    @GetMapping("/perfil")
    public ResponseEntity<UserProfile> getProfile() {
        UserProfile profile = new UserProfile();
        profile.setIdUsuario("c.flores");
        profile.setNombres("Christian");
        profile.setApellidos("Flores");
        profile.setCorreo("c.flores@miempresa.com");
        profile.setTelefono("999999999");
        profile.setArea("Soporte");
        profile.setOficina("Sede Central");
        profile.setRol("Administrador");
        profile.setGruposAD(List.of("GRP_SOPORTE", "GRP_TI"));
        return ResponseEntity.ok(profile);
    }*/

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getIdUsuario());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            LoginResponse response = new LoginResponse();
            response.setMessage("Autenticación exitosa");
            response.setPerfil(usuario.getNombres() + " " + usuario.getApellidos());
            response.setRol(usuario.getRol().getNombre());
            response.setRedirectUrl("/ZohoHelpDesk");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(
            summary = "Obtener perfil del usuario autenticado",
            description = "Retorna la información detallada del usuario según su ID. El rol es asignado automáticamente."
    )
    @GetMapping("/perfil")
    public ResponseEntity<UserProfile> getPerfilPorId(@RequestParam Integer idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            UserProfile profile = new UserProfile();
            profile.setIdUsuario(usuario.getIdUsuario().toString());
            profile.setNombres(usuario.getNombres());
            profile.setApellidos(usuario.getApellidos());
            profile.setCorreo(usuario.getCorreo());
            profile.setTelefono("999999999"); // O mapear de tu entidad si lo tienes
            profile.setArea(usuario.getArea().getNombre());
            profile.setOficina(usuario.getSede());
            profile.setRol(usuario.getRol().getNombre());
            profile.setGruposAD(List.of("GRP_DEFAULT")); // Aquí puedes mapear desde una relación real

            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }




}
