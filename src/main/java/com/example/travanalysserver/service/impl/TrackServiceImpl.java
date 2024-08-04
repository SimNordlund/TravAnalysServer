package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;
import com.example.travanalysserver.dto.track.TrackDTO;
import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.entity.testing.RadarHorse;
import com.example.travanalysserver.repository.TrackRepo;
import com.example.travanalysserver.service.interfaces.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrackServiceImpl implements TrackService {

    private TrackRepo trackRepo;

    @Override
    public List<TrackDTO> getAllDates(){
        List <Track> tempTrackList = trackRepo.findAll();
        List <TrackDTO> dateList = tempTrackList.stream().map(e -> TrackToTrackDto(e)).toList();
        return dateList;
    }

    public TrackDTO TrackToTrackDto(Track track) {
        return TrackDTO.builder()
                .id(track.getId())
                .date(track.getDate())
                .nameOfTrack(track.getNameOfTrack())
                .build();
    }
}
