package com.cmms.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmms.api.entity.Model;
import com.cmms.api.repository.ModelRepository;

@Service
public class ServiceModel implements IServiceModel {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Model createModel(Model model) {

        return modelRepository.save(model);
    }

    @Override
    public Model findModelById(int id) {

        return modelRepository.findById(id).get();
    }

    @Override
    public Model updateModel(Model model) {

        return modelRepository.save(model);
    }

    @Override
    public List<Model> findAllModels() {

        return modelRepository.findAll();
    }

    @Override
    public void deleteModel(Model model) {

        modelRepository.delete(model);
    }

}
