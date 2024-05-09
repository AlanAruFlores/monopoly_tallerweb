package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
        ModelMap mp = new ModelMap();
        mp.put("datosPerfil", new DatosPerfil());
        return new ModelAndView("editarPerfil",mp);

    }



    @RequestMapping(path = "/editar-perfil", method = RequestMethod.POST)
    public ModelAndView actualizarPerfil(@ModelAttribute("datosPerfil") DatosPerfil datosPerfil) {
        ModelAndView mv = new ModelAndView("editarPerfil");
        ModelMap model = new ModelMap();

        try {
            servicioPerfil.actualizarPerfil(datosPerfil);
            model.put("mensaje", "Perfil actualizado exitosamente");
        } catch (ContraseniaInvalidaException e) {
            model.put("error", "Las contraseñas no coinciden");

        } catch (EmailInvalidoException e) {
            model.put("error", "El email proporcionado no es válido");

        } catch (Exception e) {
            model.put("error", "Error al actualizar el perfil");

        }


        return new ModelAndView("editarPerfil", model);
    }






}