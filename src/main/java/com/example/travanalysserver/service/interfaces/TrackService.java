package com.example.travanalysserver.service.interfaces;
import com.example.travanalysserver.dto.track.DateTrackDTO;
import com.example.travanalysserver.dto.track.NameOfTrackDTO;

import java.util.List;

public interface TrackService {
    List<DateTrackDTO> getAllDates();
    List<NameOfTrackDTO> getAllNamesOfTracks();
}
