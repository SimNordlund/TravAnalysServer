package com.example.travanalysserver.service.interfaces;
import com.example.travanalysserver.dto.track.DateTrackDTO;
import com.example.travanalysserver.dto.track.NameOfTrackDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrackService {
    List<DateTrackDTO> getAllDates();
    List<NameOfTrackDTO> getAllNamesOfTracks();
    List <NameOfTrackDTO> getAllNamesOfTracksByDate(LocalDate date);
}
