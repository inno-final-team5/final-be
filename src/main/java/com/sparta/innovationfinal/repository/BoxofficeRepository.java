package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Boxoffice;
import com.sparta.innovationfinal.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxofficeRepository extends JpaRepository<Boxoffice, Long> {
    List<Boxoffice> findAll();
}
