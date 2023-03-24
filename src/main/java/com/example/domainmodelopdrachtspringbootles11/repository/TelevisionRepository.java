package com.example.domainmodelopdrachtspringbootles11.repository;

import com.example.domainmodelopdrachtspringbootles11.model.Television;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelevisionRepository extends JpaRepository<Television, Long> {

    List<Television> findAllTelevisionsByBrandEqualsIgnoreCase(String brand);
}
