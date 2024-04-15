package com.cmms.api.service;

import java.util.List;

import com.cmms.api.entity.Model;

public interface IServiceModel {

    public Model createModel(Model model);

    public Model findModelById(int id);

    public Model updateModel(Model model);

    public List<Model> findAllModels();

    public void deleteModel(Model model);

}
