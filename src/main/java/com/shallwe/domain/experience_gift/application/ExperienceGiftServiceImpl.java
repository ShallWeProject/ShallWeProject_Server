package com.shallwe.domain.experience_gift.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExperienceGiftServiceImpl implements ExperienceGiftService{
}
