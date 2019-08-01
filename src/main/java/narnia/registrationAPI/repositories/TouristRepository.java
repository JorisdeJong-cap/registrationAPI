package narnia.registrationAPI.repositories;

import narnia.registrationAPI.models.Tourist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristRepository extends CrudRepository<Tourist, Long> {
    List<Tourist> findAll();
    Tourist findFirstByOrderByIdDesc();
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    Tourist findByFirstNameAndLastName(String firstName, String lastName);
    int countAllByInNarniaTrue();
}
