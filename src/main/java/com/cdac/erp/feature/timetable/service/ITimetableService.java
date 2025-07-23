package com.cdac.erp.feature.timetable.service;

import com.cdac.erp.feature.timetable.dto.TimetableEntryRequest;
import com.cdac.erp.feature.timetable.dto.TimetableEntryResponse;
import com.cdac.erp.feature.timetable.dto.TimetableEntryUpdateRequest;

import java.util.List;

public interface ITimetableService {
    TimetableEntryResponse createTimetableEntry(TimetableEntryRequest request);
    List<TimetableEntryResponse> getAllTimetableEntries();
    void deleteTimetableEntry(Integer id);
    TimetableEntryResponse updateTimetableEntry(Integer id, TimetableEntryUpdateRequest request);
}