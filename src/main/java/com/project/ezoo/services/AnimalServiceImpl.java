package com.project.ezoo.services;

import com.project.ezoo.exceptions.ResourceFoundException;
import com.project.ezoo.exceptions.ResourceNotFoundException;
import com.project.ezoo.model.Animal;
import com.project.ezoo.model.AnimalFeedingSchedules;
import com.project.ezoo.model.FeedingSchedule;
import com.project.ezoo.repository.AnimalRepository;
import com.project.ezoo.repository.FeedingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("animalService")
public class AnimalServiceImpl implements AnimalService{
    @Autowired
    UserAuditing userAuditing;

    @Autowired
    AnimalRepository animalrepos;

    @Autowired
    FeedingScheduleRepository feedingschedulerepos;

    @Override
    public List<Animal> findAll() {
        List<Animal> list = new ArrayList<>();
        animalrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Animal findAnimalById(long id) {
        return animalrepos.findById(id)
                .orElseThrow(()->new ResourceAccessException("Animal with id " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if(animalrepos.findById(id).isPresent()){
            animalrepos.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Animal with id " + id + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Animal save(Animal animal) {
        Animal newAnimal = new Animal();
        if(animal.getAnimalid() != 0){
            animalrepos.findById(animal.getAnimalid())
                    .orElseThrow(()-> new ResourceNotFoundException("Animal with id " + animal.getAnimalid() + " Not Found!"));
        }
        newAnimal.setName(animal.getName());
        newAnimal.setTaxClass(animal.getTaxClass());
        newAnimal.setTaxFamily(animal.getTaxFamily());
        newAnimal.setTaxGenus(animal.getTaxGenus());
        newAnimal.setTaxKingdom(animal.getTaxKingdom());
        newAnimal.setTaxOrder(animal.getTaxOrder());
        newAnimal.setTaxSpecies(animal.getTaxSpecies());
        newAnimal.setHeight(animal.getHeight());
        newAnimal.setWeight(animal.getWeight());
        newAnimal.setType(animal.getType());
        newAnimal.setHealthStatus(animal.getHealthStatus());

        if(animal.getFeedingschedules() != null){
            newAnimal.setFeedingschedules(animal.getFeedingschedules());
        }
        return animalrepos.save(newAnimal);
    }

    @Transactional
    @Override
    public Animal update(Animal animal, long id) {
        Animal a = animalrepos.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Animal id " + id + " Not Found!"));
        if(animal.getName() == null){
            throw new ResourceNotFoundException("No Animal found to update!");
        }
        a.setName(animal.getName());
        if(animal.getFeedingschedules().size() > 0){
            a.getFeedingschedules().clear();
            for(AnimalFeedingSchedules afs : animal.getFeedingschedules()){
                FeedingSchedule fs = feedingschedulerepos.findById(afs.getFeedingSchedule().getScheduleid())
                        .orElseThrow(()-> new EntityNotFoundException("Feeding Schedule " + afs.getFeedingSchedule().getScheduleid() + " Not Found!"));
                a.getFeedingschedules().add(new AnimalFeedingSchedules(a,fs));
            }
        }
        return animalrepos.save(a);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        animalrepos.deleteAll();
    }
}
