package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.CourierResponseDTO;
import dev.sethan8r.renttools.dto.OrderResponseDTO;
import dev.sethan8r.renttools.dto.ToolResponseDTO;
import dev.sethan8r.renttools.dto.UserResponseDTO;
import dev.sethan8r.renttools.model.Order;
import dev.sethan8r.renttools.service.CourierService;
import dev.sethan8r.renttools.service.OrderService;
import dev.sethan8r.renttools.service.ToolService;
import dev.sethan8r.renttools.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;
    private final CourierService courierService;
    private final ToolService toolService;
    private final OrderService orderService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/catalog")
    public String catalog() {
        return "catalog";
    }

    @GetMapping("/catalog/tool")
    public String tool(@RequestParam Long id, Model model) {
        ToolResponseDTO tool = toolService.getToolById(id);
        model.addAttribute("tool", tool);
        return "tool";
    }

    @GetMapping("/order/create")
    public String createOrder(@RequestParam Long id, Model model, Authentication authentication) {
        ToolResponseDTO tool = toolService.getToolById(id);
        model.addAttribute("tool", tool);
        UserResponseDTO user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        return "createOrder";
    }

    @GetMapping("/order")
    public String order(@RequestParam Long id, Model model, Authentication authentication) {
        OrderResponseDTO order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        UserResponseDTO user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        return "order";
    }

    @GetMapping("/order/all")
    public String orderAll(Model model, Authentication authentication) {
        UserResponseDTO user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        return "orderAll";
    }

    @GetMapping("/registration/courier")
    public String registrationCourier() {
        return "registration-courier";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/error/401")
    public String error401() {
        return "error/401";
    }

    @GetMapping("/error/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        UserResponseDTO user = userService.getUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        return "profile";
    }

    @GetMapping("/admin/courier")
    public String adminCourier() {
        return "admin-courier";
    }

    @GetMapping("/admin/picture")
    public String adminPicture() {
        return "admin-picture";
    }

    @GetMapping("/admin/picture/delete")
    public String adminPictureDelete() {
        return "admin-picture-delete";
    }

    @GetMapping("/admin/tool")
    public String adminTool() {
        return "admin-tool";
    }

    @GetMapping("/admin/tool/delete")
    public String adminToolDelete() {
        return "admin-tool-delete";
    }

    @GetMapping("/admin/order/found")
    public String adminOrderFound() {
        return "admin-order-found";
    }

    @GetMapping("/admin/order/return")
    public String adminOrderReturn() {
        return "admin-order-return";
    }

    @GetMapping("/admin/order/return/profile")
    public String adminOrderReturnProfile() {
        return "admin-order-return-profile";
    }

    @GetMapping("/admin/delivery/found")
    public String adminDeliveryFound() {
        return "admin-delivery-found";
    }

    @GetMapping("/tool/profile")
    public String toolProfile() {
        return "tool";
    }

    @GetMapping("/courier")
    public String courier(Model model, Authentication authentication) {
        CourierResponseDTO courier = courierService.getCourierByUsername(authentication.getName());
        model.addAttribute("currentCourier", courier);
        return "courier";
    }

    @GetMapping("/courier/delivery/take")
    public String deliveryTake(Model model, Authentication authentication) {
        CourierResponseDTO courier = courierService.getCourierByUsername(authentication.getName());
        model.addAttribute("currentCourier", courier);
        return "deliveryTake";
    }


    @GetMapping("/courier/delivery/progress")
    public String deliveryProgress(Model model, Authentication authentication) {
        CourierResponseDTO courier = courierService.getCourierByUsername(authentication.getName());
        model.addAttribute("currentCourier", courier);
        return "deliveryProgress";
    }

    @GetMapping("/courier/delivery/profile")
    public String deliveryProfile(Model model, Authentication authentication) {
        CourierResponseDTO courier = courierService.getCourierByUsername(authentication.getName());
        model.addAttribute("currentCourier", courier);
        return "deliveryProfile";
    }

    @GetMapping("/courier/delivery/finish")
    public String deliveryFinish(Model model, Authentication authentication) {
        CourierResponseDTO courier = courierService.getCourierByUsername(authentication.getName());
        model.addAttribute("currentCourier", courier);
        return "deliveryFinish";
    }



    @GetMapping("/courier/delivery/all")
    public String deliveryAll(Model model, Authentication authentication) {
        CourierResponseDTO courier = courierService.getCourierByUsername(authentication.getName());
        model.addAttribute("currentCourier", courier);
        return "deliveryAll";
    }

}
