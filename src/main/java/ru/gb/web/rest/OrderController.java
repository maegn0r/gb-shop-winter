package ru.gb.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.order.dto.OrderDto;
import ru.gb.service.OrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrderList() {
        return orderService.findAll();
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long id) {
        OrderDto order;
        if (id != null) {
            order = orderService.findById(id);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody OrderDto orderDto) {
        OrderDto savedOrder = orderService.save(orderDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/order/" + savedOrder.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("orderId") Long id, @Validated @RequestBody OrderDto orderDto) {
        orderDto.setId(id);
        orderService.save(orderDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("orderId") Long id) {
        orderService.deleteById(id);
    }
}
