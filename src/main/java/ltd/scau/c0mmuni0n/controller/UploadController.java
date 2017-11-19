package ltd.scau.c0mmuni0n.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping
    public ModelAndView upload() {
        ModelAndView view = new ModelAndView("upload");
        return view;
    }
}
