package com.example.travanalysserver.controller.url;

import com.example.travanalysserver.entitysec.AppUrl;
import com.example.travanalysserver.repositorysec.AppUrlRepo; //Changed!
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

    @GetMapping(value = "/buttons", produces = "application/json") //Changed!
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager") //Changed!
    public ResponseEntity<AppButtonUrlsDto> getButtonUrls() { //Changed!
        var button2 = appUrlRepo.findById(1L).map(AppUrl::getUrl).orElse(null); //Changed!
        var button3 = appUrlRepo.findById(2L).map(AppUrl::getUrl).orElse(null); //Changed!
        return ResponseEntity.ok(new AppButtonUrlsDto(button2, button3)); //Changed!
    }

    //Brut ut och lägg i egen klass sen.
    public record AppButtonUrlsDto(String button2Url, String button3Url) {} //Changed!
}