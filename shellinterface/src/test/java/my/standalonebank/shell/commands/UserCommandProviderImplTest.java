package my.standalonebank.shell.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import my.standalonebank.model.BankUser;
import my.standalonebank.repository.UserRepository;
import my.standalonebank.shell.commands.prompt.PromptComponent;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandProviderImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PromptComponent promptComponent;

    @Spy
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserCommandProvider provider = new UserCommandProviderImpl();

    @Test
    public void testIsUserAbasent() {
        when(userRepository.findByUsername("scott")).thenReturn(null);
        boolean result = provider.isUserPresent("scott");
        assertThat(result, is(false));
    }

    @Test
    public void testIsUserPresent() {
        when(userRepository.findByUsername("scott")).thenReturn(new BankUser());
        boolean result = provider.isUserPresent("scott");
        assertThat(result, is(true));
    }

    @Test
    public void testPromptUserInformation() {
        when(promptComponent.promptPasswordConfirm(any(String.class), any(String.class)))
                .thenReturn("default720")
                .thenReturn("1234");

        provider.promptUserInformation();

        InOrder inOrder = inOrder(promptComponent);

        inOrder.verify(promptComponent).promptPasswordConfirm(any(String.class), any(String.class));
        inOrder.verify(promptComponent, times(2)).promptText(any(String.class));
        inOrder.verify(promptComponent).promptPasswordConfirm(any(String.class), any(String.class));
        inOrder.verify(promptComponent, times(1)).promptText(any(String.class));

        verify(encoder).encode(any(String.class));
    }

}
