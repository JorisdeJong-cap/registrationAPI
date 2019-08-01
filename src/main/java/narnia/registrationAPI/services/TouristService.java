package narnia.registrationAPI.services;

import javassist.NotFoundException;
import narnia.registrationAPI.models.Tourist;
import narnia.registrationAPI.repositories.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    @Autowired
    private TouristRepository touristRepository;

    public void registerTourist(Tourist tourist) {
        touristRepository.save(tourist);
    }

    public Tourist getTouristById(long id) {
        return touristRepository.findById(id).get();
    }

    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    /**
     *
     * @param id the id of the tourist to update
     * @param tourist a dummy tourist that only holds the data that needs to be updated. note: isInNarnia always gets updated
     */
    public void updateTourist(long id, Tourist tourist) throws NotFoundException {
        if (!touristRepository.findById(id).isPresent()) {
            throw new NotFoundException("could not find tourist.");
        }
        Tourist touristInDB = touristRepository.findById(id).get();
        // only update if values are given
        if (tourist.getAge() >= 0) {
            touristInDB.setAge(tourist.getAge());
        }
        if (tourist.getCity() != null) {
            touristInDB.setCity(tourist.getCity());
        }
        if (tourist.getFirstName() != null) {
            touristInDB.setFirstName(tourist.getFirstName());
        }
        if (tourist.getLastName() != null) {
            touristInDB.setLastName(tourist.getLastName());
        }
        // booleans cannot be null, so always set
        touristInDB.setInNarnia(tourist.isInNarnia());

        touristRepository.save(touristInDB);
    }

    public Tourist getMostRecentlyAddedTourist() {
        return touristRepository.findFirstByOrderByIdDesc();
    }

    public Tourist touristExists(Tourist t) throws NotFoundException {
        if(touristRepository.existsByFirstNameAndLastName(t.getFirstName(),t.getLastName())) {
            return touristRepository.findByFirstNameAndLastName(t.getFirstName(),t.getLastName());
        } else {
            throw new NotFoundException("could not find tourist.");
        }
    }

    public int getAmountOfTouristInNarnia() {
        return touristRepository.countAllByInNarniaTrue();
    }

    public boolean touristIsInNarnia(Tourist t) throws Exception {
        try {
            touristExists(t);
            return touristRepository.findById(t.getId()).get().isInNarnia();
        } catch (Exception e) {
            throw e;
        }
    }
}
