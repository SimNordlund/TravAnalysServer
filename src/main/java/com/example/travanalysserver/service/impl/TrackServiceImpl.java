package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;
import com.example.travanalysserver.dto.track.DateTrackDTO;
import com.example.travanalysserver.dto.track.NameOfTrackDTO;
import com.example.travanalysserver.dto.track.TrackDTO;
import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.entity.testing.RadarHorse;
import com.example.travanalysserver.repository.TrackRepo;
import com.example.travanalysserver.service.interfaces.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TrackServiceImpl implements TrackService {

    private TrackRepo trackRepo;

    @Override
    public List<DateTrackDTO> getAllDates() {
        List<Track> tempTrackList = trackRepo.findAll();
        List<DateTrackDTO> dateList = tempTrackList.stream().map(e -> TrackToDateTrackDTO(e)).toList();
        return dateList;
    }

    @Override
    public List<NameOfTrackDTO> getAllNamesOfTracks() {
        List<Track> tempTrackList2 = trackRepo.findAll();
        List<NameOfTrackDTO> nameOfTrackList = tempTrackList2
                .stream().map(e -> TrackToNameOfTrackDTO(e)).toList();
        return nameOfTrackList;
    }

    @Override
    public List<NameOfTrackDTO> getAllNamesOfTracksByDate(LocalDate date) {
        List<Track> tempTrackList3 = trackRepo.findByDate(date);
        List<NameOfTrackDTO> nameOfTrackDTOListByDate = tempTrackList3
                .stream().map(e -> TrackToNameOfTrackDTO(e)).toList();
        return nameOfTrackDTOListByDate;
    }


    public TrackDTO TrackToTrackDto(Track track) {
        return TrackDTO.builder()
                .id(track.getId())
                .date(track.getDate())
                .nameOfTrack(track.getNameOfTrack())
                .build();
    }

    public DateTrackDTO TrackToDateTrackDTO(Track track) {
        return DateTrackDTO.builder()
                .id(track.getId())
                .date(track.getDate())
                .build();
    }

    public NameOfTrackDTO TrackToNameOfTrackDTO(Track track) {
        return NameOfTrackDTO.builder()
                .id(track.getId())
                .nameOfTrack(track.getNameOfTrack())
                .build();
    }

}
