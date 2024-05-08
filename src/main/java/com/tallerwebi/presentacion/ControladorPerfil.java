package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseñaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorPerfil {

    private ServicioPerfil servicioPerfil;

    @Autowired
    public ControladorPerfil(ServicioPerfil servicioPerfil) {
        this.servicioPerfil = servicioPerfil;
    }
    public ControladorPerfil() {}


    @RequestMapping("/perfil")
    public ModelAndView irAlperfil() {
        return new ModelAndView("perfil");
    }

    @RequestMapping("/editar-perfil")
    public ModelAndView editarPerfil() {
        return new ModelAndView("editarPerfil");
    }



    @RequestMapping(path = "/editar-perfil", method = RequestMethod.POST)
   public ModelAndView actualizarPerfil(@ModelAttribute("DatosPerfil") DatosPerfil datosPerfil) {
        ModelAndView modelAndView = new ModelAndView("editarPerfil");

        try {
            servicioPerfil.actualizarPerfil(datosPerfil);
            modelAndView.addObject("mensaje", "Perfil actualizado exitosamente");
        } catch (ContraseñaInvalidaException e) {
            modelAndView.addObject("error", "La contraseña proporcionada no es válida");
        } catch (EmailInvalidoException e) {
            modelAndView.addObject("error", "El email proporcionado no es válido");
        } catch (Exception e) {
            modelAndView.addObject("error", "Error al actualizar el perfil");
            e.printStackTrace();
        }

        return modelAndView;
    }


}