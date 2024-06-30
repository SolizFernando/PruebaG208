package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.model.Docente;
import ar.edu.unju.fi.service.DocenteService;
import jakarta.validation.Valid;

@Controller
public class DocenteController {
	@Autowired
	Docente nuevoDocente;

	@Autowired
	DocenteService docenteService;

	@GetMapping("/formularioDocente")
	public ModelAndView getFormDocente() {

		ModelAndView modelView = new ModelAndView("formDocente");

		modelView.addObject("nuevoDocente", nuevoDocente);

		modelView.addObject("bandera", false);
		return modelView;
	}

	@PostMapping("/guardarDocente")
	public ModelAndView saveDocente(@Valid @ModelAttribute("nuevoDocente") Docente docenteGuardar, BindingResult resultado) {
		ModelAndView modelView=new ModelAndView();
		if (resultado.hasErrors()) {
			modelView.setViewName("formDocente");
		}
		else {
			docenteService.guardarDocente(docenteGuardar);
			modelView.setViewName("listaDeDocentes");
			modelView.addObject("listadoDocentes", docenteService.mostrarDocentesDTO());
		}
		return modelView;
	}

	@GetMapping("/eliminarDocente/{legajo}")
	public ModelAndView eliminarDocenteDelListado(@PathVariable(name = "legajo") String legajo) {
		docenteService.borrarDocente(legajo);
		ModelAndView modelView = new ModelAndView("listaDeDocentes");
		modelView.addObject("listadoDocentes", docenteService.mostrarDocentesDTO());
		return modelView;
	}

	@GetMapping("/modificarDocente/{legajo}")
	public ModelAndView modificarDocenteDelListado(@PathVariable(name = "legajo") String legajo) {
		Docente d = docenteService.buscarDocente(legajo);
		ModelAndView modelView = new ModelAndView("formDocente");
		modelView.addObject("nuevoDocente", d);
		modelView.addObject("bandera", true);
		return modelView;

	}

	@PostMapping("/modificarDocente")
	public ModelAndView updateDocente(@ModelAttribute("nuevoDocente") Docente docenteModificado) {
		docenteService.modificarDocente(docenteModificado);
		ModelAndView modelView = new ModelAndView("listaDeDocentes");
		modelView.addObject("listadoDocentes", docenteService.mostrarDocentesDTO());
		return modelView;

	}

	@GetMapping("/mostrarDocentes")
	public ModelAndView verDocente() {
		ModelAndView modelView = new ModelAndView("listaDeDocentes");
		modelView.addObject("listadoDocentes", docenteService.mostrarDocentesDTO());
		return modelView;
	}

}
