package com.wlovec.welovecodeapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wlovec.welovecodeapi.model.Formation;
import com.wlovec.welovecodeapi.repository.FormationRepository;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Formation getFormationById(Long id) {
        return formationRepository.findById(id).orElseThrow();
    }

    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public Formation updateFormation(Long id, Formation formation) {
        Formation existingFormation = formationRepository.findById(id).orElseThrow();
        existingFormation.setName(formation.getName());
        existingFormation.setDescription(formation.getDescription());
        existingFormation.setLanguage(formation.getLanguage());
        existingFormation.setLevel(formation.getLevel());
        existingFormation.setPrice(formation.getPrice());
        existingFormation.setOwner(formation.getOwner());
        return formationRepository.save(existingFormation);
    }

    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }
}
