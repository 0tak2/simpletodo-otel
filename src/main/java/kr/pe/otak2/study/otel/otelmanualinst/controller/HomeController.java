package kr.pe.otak2.study.otel.otelmanualinst.controller;

import io.micrometer.tracing.annotation.NewSpan;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/test")
    @NewSpan
    public String testHandler() {
        return "Hello, World!";
    }

    @GetMapping("/send")
    public String sendSimpleMessage(@RequestParam String msg) {
        simpMessagingTemplate.convertAndSend("/topic/test", msg);
        return "sent \"" + msg + "\"";
    }
}
