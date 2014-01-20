package fr.miage.orleans.campark.testneuroph.controller.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Steve Cancès <steve.cances@gmail.com>
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    protected ModelAndView getIndex(Model model) throws Exception {
	ModelAndView modelAndView = new ModelAndView("indexPage");
	return modelAndView;
    }
}
