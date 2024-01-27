package pl.jakubdudek.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.jakubdudek.springboot.entity.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {
}
