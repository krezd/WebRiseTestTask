package ru.webrise.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.webrise.testtask.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
