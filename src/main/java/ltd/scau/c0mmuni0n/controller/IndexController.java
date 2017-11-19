package ltd.scau.c0mmuni0n.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping()
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("message", "NULLLLLLLLL");
        return view;
    }

    @RequestMapping("/{param}")
    public ModelAndView index(@PathVariable String param) {
        ModelAndView view = new ModelAndView("index");
        view.addObject("message", param);
        return view;
    }
}
