package com.plKoziol.applemarket.rest;

import com.plKoziol.applemarket.model.AppleBag;
import com.plKoziol.applemarket.repository.AppleBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class AppleBagController {
    @Autowired
    AppleBagRepository appleBagRepository;

    @GetMapping("/forSale/{numberBags}")
    public ResponseEntity<List<AppleBag>> getAppleBag(@PathVariable("numberBags") int numberBags) {
        try {
            List<AppleBag> appleBagList = appleBagRepository.findAll();
            long bagsInRepository = appleBagList.size();
            if (bagsInRepository > numberBags) {
                return new ResponseEntity<>(appleBagList.subList(0, numberBags), HttpStatus.OK);
            } else if (bagsInRepository == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(appleBagList.subList(0, (int) bagsInRepository), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/forSale/")
    public ResponseEntity<List<AppleBag>> getAppleBag() {
        try {
            List<AppleBag> appleBagList = appleBagRepository.findAll();
            long bagsInRepository = appleBagList.size();
            if (bagsInRepository > 3) {
                return new ResponseEntity<>(appleBagList.subList(0, 3), HttpStatus.OK);
            } else if (bagsInRepository == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(appleBagList.subList(0, (int) bagsInRepository), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addBag")
    public ResponseEntity addAppleBag(@RequestBody AppleBag appleBag) {

        try {
            Optional<AppleBag> appleBag1 = createAppleBagAndValidateId(appleBag);
            if (!validateAppleBag(appleBag).equals("OK")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validateAppleBag(appleBag));
            } else if (appleBag1.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem with id generation in the database");
            } else return new ResponseEntity(appleBagRepository.save(appleBag1.get()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String validateAppleBag(AppleBag appleBag) {
        if (!appleBag.appleBagValidationNumberOfApples()) {
            return "Incorrect number of apples (value from 1 to 100)";
        } else if (!appleBag.appleBagValidationPrice()) {
            return "Invalid price (value from 1 to 50)";
        } else return "OK";
    }

    private Optional<AppleBag> createAppleBagAndValidateId(AppleBag appleBag) {
        AppleBag appleBag1 = new AppleBag(appleBag.getNumberOfApples(), appleBag.getSupplier(), appleBag.getTimeBagPacked(), appleBag.getPrice());
        String id1 = appleBag1.getId();
        int maxNumberOfOperationInOrderToCreateNewId = 10;
        int counterOperation = 0;
        while (appleBagRepository.countById(id1) > 0) {
            appleBag1 = new AppleBag(appleBag.getNumberOfApples(), appleBag.getSupplier(), appleBag.getTimeBagPacked(), appleBag.getPrice());
            id1 = appleBag1.getId();
            counterOperation++;
            if (counterOperation == maxNumberOfOperationInOrderToCreateNewId) {
                return Optional.empty();
            }
        }
        return Optional.of(appleBag1);


    }

    @PostMapping("/addFiveRandomRecords")
    protected void createRandomFiveRecords(){
        for(int i = 0; i < 5; i++){
            AppleBag appleBag1 = new AppleBag(new Random().nextInt(100)+1, AppleBag.Supplier.values()[new Random().nextInt(4)], LocalDateTime.now(), new BigDecimal(new Random().nextInt(50)+1));
            createAppleBagAndValidateId(appleBag1).ifPresent(it -> appleBagRepository.save(it));
        }

    }
}