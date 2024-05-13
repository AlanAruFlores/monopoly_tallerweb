package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario2;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.ServicioLoginImpl2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorLogin {
    private ServicioLoginImpl2 servicioLogin = new ServicioLoginImpl2();

    @RequestMapping("/login")
    public ModelAndView irAlLogin() {
        ModelMap model = new ModelMap();
        model.put("datosLogin", new DatosLogin2());
        return new ModelAndView("login", model);
    }

    @RequestMapping("/volver-login")
    public ModelAndView VolverAlLogin() {
        ModelMap model = new ModelMap();
        model.put("datosLogin", new DatosLogin2());
        return new ModelAndView("login", model);
    }

    @RequestMapping("/ir-registro")
    public ModelAndView irAlRegistro() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario2());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin2 datosLogin) {
        ModelMap model = new ModelMap();

        Usuario2 usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if(usuarioBuscado != null) {
            return new ModelAndView("redirect:/ir-menu");
        } else {
            model.put("error", "Usuario o contraseña incorrectos.");
            return new ModelAndView("login", model);
        }
    }

    @RequestMapping(path = "/verificar-registro", method = RequestMethod.POST)
    public ModelAndView verificarRegistro(@ModelAttribute("usuario") Usuario2 usuario) {
        ModelMap model = new ModelMap();
        model.clear();
        try {
            servicioLogin.registrar(usuario);
            servicioLogin.agregarUsuario(usuario);
            return new ModelAndView("redirect:/volver-login");
        } catch (CamposIncompletosException e) {
            model.put("error", "Todos los campos son obligatorios.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (EmailInvalidoException e) {
            model.put("error", "El correo debe tener @ y finalizar con \".com\"");
            return new ModelAndView("nuevo-usuario", model);
        } catch (LongitudContraseñaException e) {
            model.put("error", "La contraseña debe tener al menos 5 caracteres.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (MayusculaNumeroException e) {
            model.put("error", "La contraseña debe llevar al menos una mayúscula.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (ContraseñasNoCoincidenException e) {
            model.put("error", "Las contraseñas no coinciden.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (MailRegistradoException e) {
            model.put("error", "El mail ingresado ya existe.");
            return new ModelAndView("nuevo-usuario", model);
        }
    }
}
