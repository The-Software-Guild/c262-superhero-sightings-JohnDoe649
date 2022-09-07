package com.sg.superherofinder.controller;

import com.sg.superherofinder.dao.SuperDao;
import com.sg.superherofinder.dto.Super;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SuperController {

    @Autowired
    SuperDao superDao;

    Set<ConstraintViolation<Super>> violations = new HashSet<>();

    @GetMapping("supers")
    public String displaySupers(Model model) {
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        model.addAttribute("errors", violations);
        return "supers";
    }

    @PostMapping("/supers/addSuper")
    public String addSuper(HttpServletRequest request) {
        String superName = request.getParameter("superName");
        String superDescription = request.getParameter("superDescription");
        String superpower = request.getParameter("superpower");

        Super superhero = new Super();
        superhero.setName(superName);
        superhero.setDescription(superDescription);
        superhero.setPower(superpower);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);

        if(violations.isEmpty()) {
            superDao.addSuper(superhero);
        }

        return "redirect:/supers";
    }

    @GetMapping("supers/deleteSuper")
    public String deleteSuper(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superDao.deleteSuper(id);

        return "redirect:/supers";
    }

    @GetMapping("supers/editSuper")
    public String editSuper(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Super superhero = superDao.getSuperById(id);

        model.addAttribute("super", superhero);
        return "editSuper";
    }

    @PostMapping("supers/editSuper")
    public String performEditSuper(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Super superhero = superDao.getSuperById(id);

        superhero.setName(request.getParameter("superName"));
        superhero.setDescription(request.getParameter("superDescription"));
        superhero.setPower(request.getParameter("superpower"));

        superDao.updateSuper(superhero);

        return "redirect:/supers";
    }
}
