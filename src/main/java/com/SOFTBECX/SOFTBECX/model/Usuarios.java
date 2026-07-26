package com.SOFTBECX.SOFTBECX.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuarios
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @NotNull(message = "El documento es obligatorio")
    private Integer documento;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    private Long rol;

    //@NotBlank(message = "La contraseña es obligatorio")
    private String contrasena;
}
