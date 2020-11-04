package top.devonte.note.service.impl;

import org.springframework.stereotype.Service;
import top.devonte.note.mapper.AnalyticMapper;
import top.devonte.note.service.AnalyticService;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AnalyticServiceImpl implements AnalyticService {

    @Resource
    private AnalyticMapper mapper;

    @Override
    public Map<String, Object> getUserAnalyticData(int userId) {
        return mapper.analyticUserData(userId);
    }
}
