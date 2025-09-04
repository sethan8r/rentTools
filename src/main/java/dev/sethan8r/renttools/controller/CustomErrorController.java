package dev.sethan8r.renttools.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute("code", "NOT_FOUND");
            model.addAttribute("message", "Страница не найдена");
        } else if (exception != null) {
            model.addAttribute("code", "INTERNAL_SERVER_ERROR");
            model.addAttribute("message", "Произошла внутренняя ошибка сервера");
        } else {
            model.addAttribute("code", "ERROR_" + statusCode);
            model.addAttribute("message", "Произошла ошибка");
        }

        return "/error/error-page";
    }
}
