package narnia.registrationAPI.controllers;

import javassist.NotFoundException;
import narnia.registrationAPI.exceptions.IncompleteTouristException;
import narnia.registrationAPI.exceptions.NoMoreTouristsAllowedException;
import narnia.registrationAPI.models.Tourist;
import narnia.registrationAPI.services.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TouristController {

    @Autowired
    private TouristService touristService;

    @PostMapping("registerAccount/tourist")
    public void registerTourist(@RequestBody Tourist tourist) {
        System.out.println(tourist);
        if(tourist.getAge() < 0) {
            throw new IncompleteTouristException("Age is a negative number, cannot be negative.");
        }
        if(tourist.getCity().isEmpty()) {
            throw new IncompleteTouristException("City value of tourist body is empty.");
        }
        if(tourist.getFirstName().isEmpty()) {
            throw new IncompleteTouristException("Tourist has no first name.");
        }
        if(tourist.getLastName().isEmpty()) {
            throw new IncompleteTouristException("Tourist has no last name.");
        }
        touristService.registerTourist(tourist);
    }

    @GetMapping("registerAccount")
    @ResponseBody
    public List<Tourist> getAllTourists() {
        return touristService.getAllTourists();
    }
    @GetMapping("registerAccount/newTourist")
    @ResponseBody
    public Tourist getMostRecentlyAddedTourist() {
        return touristService.getMostRecentlyAddedTourist();
    }
    @PostMapping("enterNarnia/border_control")
    @ResponseBody
    public Tourist touristExists(@RequestBody Tourist t) throws Exception {
        try {
            return touristService.touristExists(t);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * success response means open for business
     * @throws NoMoreTouristsAllowedException when the land is full, throws an exception
     */
    @GetMapping("enterNarnia/checkAvailability")
    public void checkAvailability() throws NoMoreTouristsAllowedException {
        if(touristService.getAmountOfTouristInNarnia() > 5) {
            throw new NoMoreTouristsAllowedException("The land is full! comeback later.");
        }
    }

    @GetMapping("enterNarnia/isInNarnia")
    public boolean isInNarnia(@RequestBody Tourist t) throws Exception {
        try {
            // throws if not found
            return touristService.touristIsInNarnia(t);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("enterNarnia/enterNarnia")
    public void enterNarnia(@RequestBody Tourist t) throws NotFoundException {
        try {
            // throws if not found
            touristService.touristExists(t);
            Tourist temp = new Tourist();
            temp.setInNarnia(true);
            System.out.println(temp);
            System.out.println(t);
            touristService.updateTourist(t.getId(),temp);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("enterNarnia/leaveNarnia")
    public void leaveNarnia(@RequestBody Tourist t) throws Exception {
       try {
           // throws if not found
           touristService.touristExists(t);
           Tourist temp = new Tourist();
           temp.setInNarnia(false);
           touristService.updateTourist(t.getId(),temp);
       } catch (Exception e) {
           throw e;
       }
    }

}
