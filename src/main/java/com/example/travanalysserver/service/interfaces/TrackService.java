package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.track.TrackDTO;

import java.util.List;

public interface TrackService {
    List<TrackDTO> getAllDates();
}
