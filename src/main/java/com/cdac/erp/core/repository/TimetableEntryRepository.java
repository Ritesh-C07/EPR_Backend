package com.cdac.erp.core.repository;

import com.cdac.erp.core.model.TimetableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, Integer> {
}