package ltd.scau.c0mmuni0n.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/user-profile")
@Controller
public class UserProfileController {

    @RequestMapping("/{id}")
    public ModelAndView user(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView("user-profile");

        return view;
    }
}
