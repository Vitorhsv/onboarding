package org.banking.service.user;

import org.banking.entities.user.UserRequest;
import org.banking.entities.viacep.ViaCepResponse;
import org.banking.repositories.database.UserEntity;
import org.banking.repositories.database.UserRepository;
import org.banking.service.viacep.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class UserService {

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private UserRepository userRepository;

    public void post(UserRequest user){

        String date = user.getDateBirth();
        LocalDate dateLocal = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(dateLocal);
        Long year = ChronoUnit.YEARS.between(dateLocal, LocalDate.now());
        if (year <18) {
            throw new RuntimeException();
        }

    ViaCepResponse address = viaCepService.getAddress(user.getZipCode());
        UserEntity userEntity = new UserEntity();
        userEntity.setDateBirth(dateLocal);
        userEntity.setName(user.getName());
        userEntity.setDocument(user.getDocument());
        userRepository.save(userEntity);
}
}
