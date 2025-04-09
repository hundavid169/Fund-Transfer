package com.fund.fund_transfer.service.impl;

import com.fund.fund_transfer.dto.request.UserRequest;
import com.fund.fund_transfer.dto.response.UserRes;
import com.fund.fund_transfer.model.User;
import com.fund.fund_transfer.repository.UserRepository;
import com.fund.fund_transfer.service.UserService;
import com.fund.fund_transfer.utils.CommonMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserRes userRegister(UserRequest userReq) {
        UserRes userRes = null;
        try {
            User user = CommonMapper.INSTANCE.userToEntity(userReq);
            user.setUserUuid(UUID.randomUUID().toString());

            String encryptedPassword = passwordEncoder.encode(userReq.getPassword());
            user.setPassword(encryptedPassword);
            user = userRepository.save(user);

            userRes = CommonMapper.INSTANCE.userToDTO(user);
        } catch (Exception ex) {
            log.error("createAccount", ex);
        }
        return userRes;
    }


}
