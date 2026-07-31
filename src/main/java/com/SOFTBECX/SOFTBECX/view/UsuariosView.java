package com.SOFTBECX.SOFTBECX.view;

import com.SOFTBECX.SOFTBECX.model.Usuarios;
import com.SOFTBECX.SOFTBECX.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuariosView {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @GetMapping("/")
    public String inicio() {
        return "index"; // Abre templates/index.html
    }

    @GetMapping("/view/usuarios")
    public String lista(Model model) {
        model.addAttribute("usuarios", usuariosRepository.findAll());
        return "usuarios/list";
    }

    @GetMapping("/view/usuarios/form")
    public String form(Model model) {
        model.addAttribute("usuarios", new Usuarios());
        return "usuarios/form";
    }

    @PostMapping("/view/usuarios/save")
    public String save(@ModelAttribute Usuarios usuarios, RedirectAttributes ra) {
        // Lógica para no borrar la contraseña si se está editando y el campo se dejó en blanco
        if (usuarios.getId_usuario() != null) {
            Usuarios usuarioExistente = usuariosRepository.findById(usuarios.getId_usuario()).orElse(null);
            if (usuarioExistente != null) {
                if (usuarios.getContrasena() == null || usuarios.getContrasena().trim().isEmpty()) {
                    usuarios.setContrasena(usuarioExistente.getContrasena());
                }
            }
        }

        usuariosRepository.save(usuarios);
        ra.addFlashAttribute("success", "Usuario guardado con éxito");
        return "redirect:/view/usuarios";
    }

    @GetMapping("/view/usuarios/update/{id}")
    public String update(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Usuarios usuarios = usuariosRepository.findById(id).orElse(null);
        if (usuarios == null) {
            ra.addFlashAttribute("error", "El usuario no existe");
            return "redirect:/view/usuarios";
        }
        model.addAttribute("usuarios", usuarios);
        return "usuarios/form";
    }

    @PostMapping("/view/usuarios/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (usuariosRepository.existsById(id)) {
            usuariosRepository.deleteById(id);
            ra.addFlashAttribute("success", "Usuario eliminado con éxito");
        } else {
            ra.addFlashAttribute("error", "No se pudo eliminar el usuario");
        }
        return "redirect:/view/usuarios";
    }
}