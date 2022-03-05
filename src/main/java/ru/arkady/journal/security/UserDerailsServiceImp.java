package ru.arkady.journal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.arkady.journal.entities.Teacher;
import ru.arkady.journal.repositories.TeacherRepository;

@Service("userDetailsServiceImpl")
public class UserDerailsServiceImp implements UserDetailsService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public UserDerailsServiceImp(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return SecurityUser.fromUser(teacher);
    }
}
