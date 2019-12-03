package my.standalonebank.shell.util;

import my.standalonebank.model.BankUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import my.standalonebank.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityUtilTest {

    @InjectMocks
    private SecurityUtil securityUtil;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
         UserDetails user = User.withUsername("alice")
                .password("$2a$10$oSVTLvsZDUODXHxiayW68uK.gEeWIkH1qQtw6DXseXGk0MDS/L9Ae")
                .roles(new String[]{"USER"})
                .build();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testGetCurrentUser() {
        BankUser bankUser = new BankUser();
        bankUser.setUsername("alice");
        when(userRepository.findByUsername(any(String.class)))
                .thenReturn(bankUser);
        BankUser bankUserResult = securityUtil.getCurrentUser();
        verify(userRepository).findByUsername("alice");
        assertThat(bankUserResult, is(bankUser));
    }
}
