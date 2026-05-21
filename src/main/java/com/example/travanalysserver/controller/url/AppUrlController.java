package com.example.travanalysserver.controller.url;

import com.example.travanalysserver.entitysec.AppUrl;
import com.example.travanalysserver.repositorysec.AppUrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app_url")
public class AppUrlController {

    private static final Long V85_APP_URL_ID = 1L;
    private static final Long V86_APP_URL_ID = 2L;
    private static final int DAYS_TO_ADD = 7;
    private static final DateTimeFormatter URL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATE_COLUMN_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private static final Pattern URL_DATE_PATTERN = Pattern.compile("_(\\d{4}-\\d{2}-\\d{2})_");

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

    @PostMapping(value = "/v85/advance-week", produces = "application/json")
    @Transactional(transactionManager = "secondaryTransactionManager")
    public ResponseEntity<AppUrl> advanceV85OneWeek() {
        return advanceAppUrlOneWeek(V85_APP_URL_ID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/v86/advance-week", produces = "application/json")
    @Transactional(transactionManager = "secondaryTransactionManager")
    public ResponseEntity<AppUrl> advanceV86OneWeek() {
        return advanceAppUrlOneWeek(V86_APP_URL_ID)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //Cronjob när urler uppdateras
    @Scheduled(cron = "0 0 03 * * THU", zone = "Europe/Stockholm")
    @Transactional(transactionManager = "secondaryTransactionManager")
    public void scheduledAdvanceV85OneWeek() {
        advanceAppUrlOneWeek(V85_APP_URL_ID);
    }
    @Scheduled(cron = "0 0 03 * * SUN", zone = "Europe/Stockholm")
    @Transactional(transactionManager = "secondaryTransactionManager")
    public void scheduledAdvanceV86OneWeek() {
        advanceAppUrlOneWeek(V86_APP_URL_ID);
    }

    private Optional<AppUrl> advanceAppUrlOneWeek(Long id) {
        return appUrlRepo.findById(id)
                .map(appUrl -> {
                    appUrl.setUrl(advanceUrlDateOneWeek(appUrl.getUrl()));
                    appUrl.setDate(advanceDateColumnOneWeek(appUrl.getDate()));
                    return appUrlRepo.save(appUrl);
                });
    }

    private String advanceUrlDateOneWeek(String url) {
        if (url == null) {
            return null;
        }

        Matcher matcher = URL_DATE_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Could not find yyyy-MM-dd date in AppUrl url");
        }

        LocalDate currentDate = LocalDate.parse(matcher.group(1), URL_DATE_FORMATTER);
        String updatedDate = currentDate.plusDays(DAYS_TO_ADD).format(URL_DATE_FORMATTER);

        return matcher.replaceFirst("_" + updatedDate + "_");
    }

    private String advanceDateColumnOneWeek(String date) {
        if (date == null) {
            return null;
        }

        if (date.length() < 8) {
            throw new IllegalArgumentException("AppUrl date must start with yyyyMMdd");
        }

        LocalDate currentDate = LocalDate.parse(date.substring(0, 8), DATE_COLUMN_FORMATTER);
        String updatedDate = currentDate.plusDays(DAYS_TO_ADD).format(DATE_COLUMN_FORMATTER);

        return updatedDate + date.substring(8);
    }


    //BUILDER
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

    //SKAPA ENTITIES / DTO SEN
    public record AppButtonDataDto(
            AppRaceDataDto v85,
            AppRaceDataDto v86
    ) {
    }

    //SKAPA ENTITIES /DTO SEN
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
