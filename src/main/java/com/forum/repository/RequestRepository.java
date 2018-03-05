package com.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.model.Request;

@Repository("requestRepository")
public interface RequestRepository extends JpaRepository<Request, Long> {
}
