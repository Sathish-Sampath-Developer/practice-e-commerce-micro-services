package com.eshop.authservice.bootstrap;

import com.eshop.authservice.entity.RoleEntity;
import com.eshop.authservice.enums.RoleEnum;
import com.eshop.authservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
//        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
//        Map<RoleEnum, String> roleDescriptionMap = Map.of(
//                RoleEnum.USER, "Default user role",
//                RoleEnum.ADMIN, "Administrator role",
//                RoleEnum.SUPER_ADMIN, "Super Administrator role"
//        );
//
//        Arrays.stream(roleNames).forEach((roleName) -> {
//            RoleEntity role = roleRepository.findByName(roleName.name()).orElse(null);
//
//            if (role == null) {
//                RoleEntity roleToCreate = new RoleEntity();
//
//                roleToCreate.setName(roleName);
//                roleToCreate.setRoleDescription(roleDescriptionMap.get(roleName));
//
//                roleRepository.save(roleToCreate);
//            }
//            ;
//        });
    }
}
