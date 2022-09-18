package com.plKoziol.applemarket.repository;

import com.plKoziol.applemarket.model.AppleBag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleBagRepository extends JpaRepository<AppleBag, Integer> {

    Long countById(String id);
}
