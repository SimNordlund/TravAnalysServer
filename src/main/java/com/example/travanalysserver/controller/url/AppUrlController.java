package com.example.travanalysserver.controller.url;

import com.example.travanalysserver.entitysec.AppUrl;
import com.example.travanalysserver.repositorysec.AppUrlRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
                a1 != null ? a1.getUrl() : null,
                a1 != null ? a1.getDate() : null,
                a2 != null ? a2.getUrl() : null,
                a2 != null ? a2.getDate() : null
        ));
    }

    public record AppButtonDataDto(String button2Url, String button2Date, String button3Url, String button3Date
    ) {}
}