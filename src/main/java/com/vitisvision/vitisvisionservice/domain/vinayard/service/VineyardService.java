package com.vitisvision.vitisvisionservice.domain.vinayard.service;

import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.repository.VineyardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class VineyardService {

    private final VineyardRepository vineyardRepository;

    public Integer createVineyard(VineyardRequest vineyardRequest, Principal principal) {
        return 1;
    }
}
