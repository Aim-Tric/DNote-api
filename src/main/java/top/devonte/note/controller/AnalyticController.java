package top.devonte.note.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.devonte.note.common.BaseController;
import top.devonte.note.service.AnalyticService;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class AnalyticController extends BaseController {

    @Resource
    private AnalyticService analyticService;

    @GetMapping("/analytic/info")
    public ResponseEntity<Map<String, Object>> getUserAnalyticData() {
        int id = getLoginUser().getId();
        return ResponseEntity.ok(analyticService.getUserAnalyticData(id));
    }

}
