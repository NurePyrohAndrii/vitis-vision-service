package com.vitisvision.vitisvisionservice.domain.vinayard.service;

import com.vitisvision.vitisvisionservice.domain.vinayard.repository.VineyardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VineyardService {

    private final VineyardRepository vineyardRepository;

}
