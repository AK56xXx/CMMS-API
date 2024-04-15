package com.cmms.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.Model;
import com.cmms.api.service.IServiceModel;



@RestController
@RequestMapping("/api/v1/model")
public class RestModelController {

    @Autowired
    IServiceModel iServiceModel;

    @GetMapping("")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Model> findAllModels() {
        return iServiceModel.findAllModels();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Model findModelById(@PathVariable int id) {

        return iServiceModel.findModelById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Model AddModel(@RequestBody Model model) {
        return iServiceModel.createModel(model);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Model UpdateModel(@RequestBody Model model) {
        return iServiceModel.updateModel(model);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void DeleteModel(@PathVariable int id) {
        iServiceModel.deleteModel(iServiceModel.findModelById(id));
    }

}
