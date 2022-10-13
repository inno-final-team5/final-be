package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Boxoffice;
import com.sparta.innovationfinal.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxofficeRepository extends JpaRepository<Boxoffice, Long> {
    List<Boxoffice> findAll();
}
