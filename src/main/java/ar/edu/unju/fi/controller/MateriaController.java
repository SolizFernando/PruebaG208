package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.fi.model.Materia;
import ar.edu.unju.fi.service.MateriaService;
import jakarta.validation.Valid;

@Controller
public class MateriaController {
	
	@Autowired
	Materia nuevaMateria;
	
	@Autowired
	MateriaService materiaService;
	
	@GetMapping("/formularioMateria")
	public ModelAndView getFormMateria() {
		ModelAndView modelView=new ModelAndView("formMateria");
		//agregar el objeto
		modelView.addObject("nuevaMateria",nuevaMateria);
		modelView.addObject("flag",false);
		return modelView;
	}
	
	@PostMapping("/guardarMateria")
	public ModelAndView saveMateria(@Valid @ModelAttribute("nuevaMateria") Materia m, BindingResult resultado) {
		ModelAndView modelView=new ModelAndView();
		if (resultado.hasErrors()) {
			modelView.setViewName("formMateria");
		}
		else {
			//guardar
			materiaService.guardarMateria(m);
			//mostrar el listado
			modelView.setViewName("listaDeMaterias");
			modelView.addObject("listadoMaterias",materiaService.mostrarMateriasDTO());
		}		
		return modelView;
	}
	
	@GetMapping("/mostrarMaterias")
	public ModelAndView listarLasMaterias() {
		//mostrar el listado
		ModelAndView modelView=new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMaterias",materiaService.mostrarMateriasDTO());
		return modelView;
	}
	
	@GetMapping("/modificarMateria/{codigo}")
	public ModelAndView editarMateria(@PathVariable(name="codigo") Integer codigo) {
		//buscar
		Materia m = materiaService.buscarMateria(codigo);
		//mostrar el nuevo formulario
		ModelAndView modelView=new ModelAndView("formMateria");	
		modelView.addObject("nuevaMateria",m);
		modelView.addObject("flag",true);
		return modelView;
	}
	
	@PostMapping("/modificarMateria")
	public ModelAndView modificarMateriaListado(@ModelAttribute("nuevaMateria") Materia m) {
		//entidades a guardar		
		materiaService.modificarMateria(m);
		//mostrar el listado
		ModelAndView modelView=new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMaterias",materiaService.mostrarMateriasDTO());
		return modelView;
	}
	
	@GetMapping("/eliminarMateria/{codigo}")
	public ModelAndView borrarMateriaListado(@PathVariable(name="codigo") Integer codigo) {
		//borrar
		materiaService.borrarMateria(codigo);
		//mostrar el nuevo listado
		ModelAndView modelView=new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMaterias",materiaService.mostrarMateriasDTO());	
		return modelView;
	}
	
}