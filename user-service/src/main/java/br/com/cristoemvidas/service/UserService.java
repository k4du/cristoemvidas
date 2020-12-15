package br.com.cristoemvidas.service;

import br.com.cristoemvidas.dti.UserDti;
import br.com.cristoemvidas.dto.UserDto;
import br.com.cristoemvidas.model.Role;
import br.com.cristoemvidas.model.RoleEnum;
import br.com.cristoemvidas.model.User;
import br.com.cristoemvidas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    
    public UserDto retrieveUserData(String userId){

        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent()){

            User user = userOptional.get();

            return UserDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .roleList(user.getRoleList())
                    .build();
        }
        return null;
    }

    public User retrieveUserDataToEdit(String userId){

        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isPresent()){
            return userOptional.get();
        }
        return null;
    }

    public Page<User> retrieveAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User createOrUpdateUser(UserDti userDti){

        User user = User.builder().build();

        if(Objects.nonNull(userDti.getId())){

            Optional<User> userOptional = userRepository.findById(userDti.getId());
            if(!userOptional.isPresent()){
                throw new RuntimeException("Usuário não encontrado.");
            }

            user = userOptional.get();
        }

        this.validateData(user, userDti);
        userDti.fillUser(user);
        user.setRoles(makeRolesListString(userDti.getRoles()));

        return userRepository.save(user);
    }


    private List<Role> makeRolesListString(List<String> rolesByFront){

        if(!CollectionUtils.isEmpty(rolesByFront)){
            return rolesByFront
                    .stream()
                    .filter(role -> this.validateRole(role))
                    .map((String role) -> Role.builder().role(role).build())
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void validateData(User user, UserDti userDti){
        if(Objects.isNull(userDti.getId()) && Objects.isNull(userDti.getPassword())){
            throw new RuntimeException("Senha não pode ser vazia");
        }

        if(!validEmail(userDti.getEmail(), user.getEmail())){
            throw new RuntimeException("Email já utilizado");
        }
        if(!validUsername(userDti.getUsername(), user.getUsername())){
            throw new RuntimeException("Nome de usuário já utilizado");
        }
    }


    private boolean validateRole(String role){
        for (RoleEnum roleEnum:  RoleEnum.values()){
            if(roleEnum.name().equals(role)){
                return true;
            }
        }
        return false;
    }



    private boolean validEmail(String email, String oldEmail){

        if(Objects.nonNull(oldEmail) && email.equals(oldEmail)){
            return true;
        }

        return !userRepository.existsByEmail(email);
    }

    private boolean validUsername(String username, String oldUserName){

        if(Objects.nonNull(oldUserName) && username.equals(oldUserName)){
            return true;
        }

        return !userRepository.existsByUsername(username);
    }


}
