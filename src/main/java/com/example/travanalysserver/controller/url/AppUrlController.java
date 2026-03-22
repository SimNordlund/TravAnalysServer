package com.example.travanalysserver.controller.url;

import com.example.travanalysserver.entitysec.AppUrl;
import com.example.travanalysserver.repositorysec.AppUrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app_url")
public class AppUrlController {

    private final AppUrlRepo appUrlRepo;

    @GetMapping(produces = "application/json")
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public List<AppUrl> getAll() {
        return appUrlRepo.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public ResponseEntity<AppUrl> getById(@PathVariable Long id) {
        return appUrlRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/buttons", produces = "application/json")
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public ResponseEntity<AppButtonDataDto> getButtonUrls() {
        var a1 = appUrlRepo.findById(1L).orElse(null);
        var a2 = appUrlRepo.findById(2L).orElse(null);

        return ResponseEntity.ok(new AppButtonDataDto(
                toRaceDataDto(a1, 1L, "V85"),
                toRaceDataDto(a2, 2L, "V86")
        ));
    }

    private AppRaceDataDto toRaceDataDto(AppUrl appUrl, Long fallbackId, String raceType) {
        if (appUrl == null) {
            return new AppRaceDataDto(
                    fallbackId,
                    raceType,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }

        return new AppRaceDataDto(
                appUrl.getId(),
                raceType,
                appUrl.getUrl(),
                appUrl.getDate(),
                appUrl.getAvd1(),
                appUrl.getAvd2(),
                appUrl.getAvd3(),
                appUrl.getAvd4(),
                appUrl.getAvd5(),
                appUrl.getAvd6(),
                appUrl.getAvd7(),
                appUrl.getAvd8()
        );
    }

    public record AppButtonDataDto(
            AppRaceDataDto v85,
            AppRaceDataDto v86
    ) {
    }

    public record AppRaceDataDto(
            Long id,
            String raceType,
            String url,
            String date,
            String avd1,
            String avd2,
            String avd3,
            String avd4,
            String avd5,
            String avd6,
            String avd7,
            String avd8
    ) {
    }
}