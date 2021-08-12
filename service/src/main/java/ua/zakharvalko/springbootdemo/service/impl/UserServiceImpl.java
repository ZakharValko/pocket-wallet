package ua.zakharvalko.springbootdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.zakharvalko.springbootdemo.dao.UserRepository;
import ua.zakharvalko.springbootdemo.domain.User;
import ua.zakharvalko.springbootdemo.service.UserService;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UserRepository> implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }


/*    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().getTitle())
                .build();
    }*/
}
