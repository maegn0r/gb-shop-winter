package ru.gb.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.security.dto.UserDto;
import ru.gb.config.JmsConfig;
import ru.gb.dao.security.AccountRoleDao;
import ru.gb.dao.security.AccountUserDao;
import ru.gb.entity.security.AccountRole;
import ru.gb.entity.security.AccountStatus;
import ru.gb.entity.security.AccountUser;
import ru.gb.pojo.RegisterMessage;
import ru.gb.service.ShopMailSenderService;
import ru.gb.service.UserService;
import ru.gb.web.dto.mapper.UserMapper;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService, UserService {

    private final AccountUserDao accountUserDao;
    private final AccountRoleDao accountRoleDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> usersAwaitActivation = new HashMap<>();
    private final JmsTemplate jmsTemplate;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountUserDao.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username: " + username + " not found")
        );
    }

    @Override
    public UserDto register(UserDto userDto) {
        if (accountUserDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username: " + userDto.getUsername() + " already exists");
        }
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        AccountRole accountRole = accountRoleDao.findByName("ROLE_USER");

        accountUser.setRoles(Set.of(accountRole));
        accountUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        accountUser.setStatus(AccountStatus.NOT_ACTIVE);

        AccountUser registeredAccountUser = accountUserDao.save(accountUser);
        usersAwaitActivation.put(accountUser.getUsername(),getRandomNumberString());
        jmsTemplate.convertAndSend(JmsConfig.USER_REGISTER, new RegisterMessage(userDto.getEmail(), "Gb Shop activation code", usersAwaitActivation.get(accountUser.getUsername())));


        log.info("user with username {} was registered successfully", registeredAccountUser.getUsername());

        return userMapper.toUserDto(registeredAccountUser);

    }

    @Override
    public UserDto update(UserDto userDto) {
        return userMapper.toUserDto(accountUserDao.save(userMapper.toAccountUser(userDto)));
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.toUserDto(accountUserDao.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User with id = " + id + " not found")
        ));
    }

    @Override
    public List<UserDto> findAll() {
        log.info("findAll users was called");
        return accountUserDao.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountUser findByUsername(String username) {
        return accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username = " + username + " not found")
        );
    }

    @Override
    public void deleteById(Long id) {
        accountUserDao.deleteById(id);
    }

    @Override
    public boolean checkActivateKey(String username, String secretCode) {
        if (secretCode.equals(usersAwaitActivation.get(username))){
            AccountUser user = findByUsername(username);
            user.setEnabled(true);
            user.setStatus(AccountStatus.ACTIVE);
            accountUserDao.save(user);
            usersAwaitActivation.remove(username);
            return true;
        } return false;
    }

    private String getRandomNumberString (){
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }
}
