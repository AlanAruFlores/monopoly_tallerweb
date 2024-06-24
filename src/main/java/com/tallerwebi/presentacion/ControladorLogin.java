package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin2;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorLogin {

    private ServicioLogin2 servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin2 servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

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
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin2 datosLogin, HttpServletRequest request,
                                     Model model2) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if(usuarioBuscado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioActual", usuarioBuscado);
            if (usuarioBuscado.getId() == 1) {
                return new ModelAndView("redirect:/ir-menu-admin");
            } else {
                return new ModelAndView("redirect:/ir-menu");
            }
        } else {
            model.put("error", "Usuario o contraseña incorrectos.");
            return new ModelAndView("login", model);
        }
    }

    @RequestMapping("/ir-menu-admin")
    public ModelAndView irMenuAdmin() {
        ModelMap model = new ModelMap();
        List<Usuario> usuarios = servicioLogin.buscarTodos();
        model.put("usuarios", usuarios);
        return new ModelAndView("Admin-menu", model);
    }

    @RequestMapping("/banear-usuario")
    public ModelAndView banearUsuario(@RequestParam("id") Long id, @RequestParam("motivo") String motivo) {
        servicioLogin.banearUsuario(id, motivo);
        return new ModelAndView("redirect:/ir-menu-admin");
    }

    @RequestMapping(path = "/verificar-registro", method = RequestMethod.POST)
    public ModelAndView verificarRegistro(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        model.clear();
        try {
            servicioLogin.registrar(usuario);
            servicioLogin.agregarUsuario(usuario);
            return new ModelAndView("redirect:/volver-login");
        } catch (CamposIncompletosException e) {
            model.put("error", "Todos los campos son obligatorios.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (MailRegistradoException e) {
            model.put("error", "El mail ingresado ya existe.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (EmailInvalidoException e) {
            model.put("error", "El correo debe tener @ y finalizar con \".com\"");
            return new ModelAndView("nuevo-usuario", model);
        } catch (LongitudContraseñaException e) {
            model.put("error", "La contraseña debe tener al menos 5 caracteres.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (MayusculaNumeroException e) {
            model.put("error", "La contraseña debe llevar al menos un número y mayúscula.");
            return new ModelAndView("nuevo-usuario", model);
        } catch (ContraseñasNoCoincidenException e) {
            model.put("error", "Las contraseñas no coinciden.");
            return new ModelAndView("nuevo-usuario", model);
        }
    }
}
