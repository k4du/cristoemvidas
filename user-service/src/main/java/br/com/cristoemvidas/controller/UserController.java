package br.com.cristoemvidas.controller;

import br.com.cristoemvidas.dti.UserDti;
import br.com.cristoemvidas.dto.UserDto;
import br.com.cristoemvidas.model.User;
import br.com.cristoemvidas.model.UserToken;
import br.com.cristoemvidas.service.SecurityHelper;
import br.com.cristoemvidas.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, path = "/data")
    public UserDto retrieveUser(){
        UserToken userToken = SecurityHelper.userData();
        return userService.retrieveUserData(userToken.getId());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public User user(@PathVariable("userId") @NotNull @NotBlank String userId ){
        return userService.retrieveUserDataToEdit(userId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Page<User> list(@RequestParam(value = "page", defaultValue = "0")  int page, @RequestParam(value = "size", defaultValue = "12")  int size){
        return userService.retrieveAll(PageRequest.of(page, size, Sort.Direction.ASC, "name"));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public User save(@RequestBody @Valid UserDti newUser){
        return userService.createOrUpdateUser(newUser);
    }

}
