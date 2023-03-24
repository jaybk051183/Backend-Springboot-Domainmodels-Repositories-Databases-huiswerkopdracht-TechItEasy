package com.example.domainmodelopdrachtspringbootles11.controller;

import com.example.domainmodelopdrachtspringbootles11.exceptions.RecordNotFoundException;
import com.example.domainmodelopdrachtspringbootles11.model.Television;
import com.example.domainmodelopdrachtspringbootles11.repository.TelevisionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TelevisionController {
    // Vorige week maakten we nog gebruik van een List<String>, nu gebruiken we de repository met een echte database.
    // We injecteren de repository hier via de constructor, maar je mag ook @Autowired gebruiken.
    private final TelevisionRepository televisionRepository;

    // Constructor injection
    public TelevisionController(TelevisionRepository televisionRepository) {
        this.televisionRepository = televisionRepository;
    }

    // We hebben hier de RequestParam "brand" toegevoegd om te laten zien hoe dat werkt.
    // Als de gebruiker een brand invult, dan zoeken we op brand, anders returnen we alle televisions.
    @GetMapping("/televisions")
    public ResponseEntity<List<Television>> getAllTelevisions(@RequestParam(value = "brand", required = false) String brand) {
        List<Television> televisions;
        if (brand == null) {
            // Vul de televisions lijst met alle televisions
            televisions = televisionRepository.findAll();

        } else {
            // Vul de televisions lijst met alle television die een bepaald brand hebben
            televisions = televisionRepository.findAllTelevisionsByBrandEqualsIgnoreCase(brand);
        }
        // Return de televisions lijst en een 200 status
        return ResponseEntity.ok().body(televisions);
    }

    // Zo ja, dan haal je de Television uit de Optional met "optionalTelevision.get()" en return je dat in responsestatus.ok, zo nee, dan return je een ResponseEntity.badRequest() of gooi je een RecordNotFoundException op.
    @GetMapping("televisions/{id}")
    public ResponseEntity<Television> getTelevisionById(@PathVariable Long id) {
        String television;
        //Repository call maken "repository.findById(id)".
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
// Daar krijg je een Optional van terug. Vervolgens checken of er een Television in die Optional zit of niet, door optionalTelevision.isPresent() te checken.
        if (optionalTelevision.isPresent()) {
            // Zo ja, dan haal je de Television uit de Optional met "optionalTelevision.get()" en return je dat in responsestatus.ok, zo nee, dan return je een ResponseEntity.badRequest() of gooi je een RecordNotFoundException op.
            Television television1 = optionalTelevision.get();
            return new ResponseEntity<>(television1, HttpStatus.OK);
        } else {
            throw new RecordNotFoundException("No television found with id: " + id);
        }
    }

    // We geven hier een television mee in de parameter. Zorg dat je JSON object exact overeenkomt met het Television object.
        @PostMapping("televisions")
        public ResponseEntity<Television> addTelevision (@RequestBody Television television){
            // Sla de nieuwe tv in de database op met de save-methode van de repository
            Television returnTelevision = televisionRepository.save(television);
            // Return de gemaakte television en een 201 status
            return new ResponseEntity<>(returnTelevision, HttpStatus.CREATED);
        }

        @DeleteMapping("televisions/{id}")
        public ResponseEntity<Object> deleteTelevision ( @PathVariable Long id) {
            // Check eerst of the id bestaat in de database, verwijder dan de television met het opgegeven id uit de database.
            if (televisionRepository.existsById(id)) {
                televisionRepository.deleteById(id);
                //Retourneer een response entity met status no content.
                return new ResponseEntity<>("Television deleted succesfully",HttpStatus.NO_CONTENT);
            } else {
                //Zo niet, gooi dan een exception
                throw new RecordNotFoundException("Television doesn't exist");
            }

        }

        @PutMapping("/televisions/{id}")
        public ResponseEntity<Television> updateTelevision (@PathVariable Long id, @RequestBody Television newTelevision)
        {
            // Haal de aan te passen tv uit de database met het gegeven id
            Optional<Television> television1 = televisionRepository.findById((long) id);

            // Als eerste checken we of de aan te passen tv wel in de database bestaat.
            if (television1.isEmpty()) {

                throw new RecordNotFoundException("No television found with id: " + id);

            } else {
                // Verander alle waardes van de television uit de database naar de waardes van de television uit de parameters.
                // Behalve de id. Als je de id veranderd, dan wordt er een nieuw object gemaakt in de database.
                Television television2 = television1.get();
                television2.setAmbiLight(newTelevision.getAmbiLight());
                television2.setAvailableSize(newTelevision.getAvailableSize());
                television2.setAmbiLight(newTelevision.getAmbiLight());
                television2.setBluetooth(newTelevision.getBluetooth());
                television2.setBrand(newTelevision.getBrand());
                television2.setHdr(newTelevision.getHdr());
                television2.setName(newTelevision.getName());
                television2.setOriginalStock(newTelevision.getOriginalStock());
                television2.setPrice(newTelevision.getPrice());
                television2.setRefreshRate(newTelevision.getRefreshRate());
                television2.setScreenQuality(newTelevision.getScreenQuality());
                television2.setScreenType(newTelevision.getScreenType());
                television2.setSmartTv(newTelevision.getSmartTv());
                television2.setSold(newTelevision.getSold());
                television2.setType(newTelevision.getType());
                television2.setVoiceControl(newTelevision.getVoiceControl());
                television2.setWifi(newTelevision.getWifi());
                // Sla de gewijzigde waarden op in de database onder dezelfde id. Dit moet je niet vergeten.
                Television returnTelevision = televisionRepository.save(television2);
                // Return de nieuwe versie van deze tv en een 200 code
                return ResponseEntity.ok().body(returnTelevision);
            }

        }

    }



