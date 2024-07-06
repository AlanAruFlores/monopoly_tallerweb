package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPerfil {

    private  ServicioPerfil servicioPerfil;

    @Autowired
    public ControladorPerfil(ServicioPerfil servicioPerfil) {
        this.servicioPerfil = servicioPerfil;
    }

    public ControladorPerfil() {}


    @RequestMapping("/perfil")
    public ModelAndView irAlperfil(HttpSession session) {
        session.setAttribute("usuarioLogeado", this.servicioPerfil.devolverUsuario(((Usuario)session.getAttribute("usuarioLogeado")).getId()));
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioLogeado");

        ModelMap model = new ModelMap();
        model.addAttribute("nombre", usuarioActual.getNombre());
        model.addAttribute("nombreUsuario",usuarioActual.getNombreUsuario());
        model.addAttribute("id", usuarioActual.getId());
        model.addAttribute("victorias", usuarioActual.getVictorias());
        model.addAttribute("imagenPerfil",usuarioActual.getImagenPerfil());
        model.put("usuarioActual",usuarioActual);
        return new ModelAndView("perfil",model);
    }


    @RequestMapping("/editar-perfil")
    public ModelAndView editarPerfil(HttpSession session) {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioLogeado");
        ModelMap mp = new ModelMap();
        Long userId = usuarioActual.getId();
        Usuario usuario = servicioPerfil.devolverUsuario(userId);
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setNombre(usuario.getNombre());
        datosPerfil.setNombreUsuario(usuario.getNombreUsuario());
        datosPerfil.setEmail(usuario.getEmail());
        datosPerfil.setImagen(usuario.getImagenPerfil());
        mp.put("datosPerfil", datosPerfil); // Usar datosPerfil con los valores iniciales
        return new ModelAndView("editarPerfil", mp);
    }

    @RequestMapping(path = "/editar-perfil", method = RequestMethod.POST)
    public ModelAndView actualizarPerfil(@ModelAttribute("datosPerfil") DatosPerfil datosPerfil, HttpSession session) {
        ModelMap model = new ModelMap();
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioLogeado");
        try {
            servicioPerfil.actualizarPerfil(datosPerfil, session);
            model.put("mensaje", "Perfil actualizado exitosamente");
        } catch (ContraseniaInvalidaException e) {
            model.put("error", "Las contraseñas no coinciden o tienen menos de 5 caracteres");
            return new ModelAndView("editarPerfil", model);
        } catch (EmailInvalidoException e) {
            model.put("error", "El email proporcionado no es válido");
            return new ModelAndView("editarPerfil", model);
        } catch (CamposVaciosException e) {
            model.put("error", "No se aceptan campos vacíos");
            return new ModelAndView("editarPerfil", model);
        } catch (Exception e) {
            model.put("error", "Error al actualizar el perfil");
            return new ModelAndView("editarPerfil", model);
        }

        return new ModelAndView("editarPerfil", model);
    }
}