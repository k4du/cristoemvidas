package br.com.cristoemvidas.controller;

import br.com.cristoemvidas.model.RoleEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/list")
    public List<RoleEnum> list(){
        return Arrays.asList(RoleEnum.values());
    }
}
