package com.sparta.innovationfinal.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxOfficeRepository extends JpaRepository<ExcelData,Long> {

}