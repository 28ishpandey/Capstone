package com.food.user.usertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.user.controller.UsersController;
import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.dto.UserUpdateDTO;
import com.food.user.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UsersService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private UserCreateDTO userCreateDTO;
  private UserResponseDTO userResponseDTO;
  private UserUpdateDTO userUpdateDTO;
  private LoginDTO loginDTO;
  private ForgotPasswordDTO forgotPasswordDTO;

  @BeforeEach
  public void setUp() {
    userCreateDTO = new UserCreateDTO();
    userCreateDTO.setEmail("test@gmail.com");
    userCreateDTO.setContactNumber("9876543210");
    userCreateDTO.setPassword("password");
    userCreateDTO.setFirstName("John");
    userCreateDTO.setLastName("Doe");
    userCreateDTO.setAddress("123 Street");
    userCreateDTO.setWalletBalance(1000.0);

    userResponseDTO = new UserResponseDTO();
    userResponseDTO.setUserId(1L);
    userResponseDTO.setEmail("test@gmail.com");
    userResponseDTO.setContactNumber("9876543210");
    userResponseDTO.setFirstName("John");
    userResponseDTO.setLastName("Doe");
    userResponseDTO.setAddress("123 Street");
    userResponseDTO.setWalletBalance(1000.0);

    userUpdateDTO = new UserUpdateDTO();
    userUpdateDTO.setEmail("newemail@gmail.com");
    userUpdateDTO.setContactNumber("9876543211");
    userUpdateDTO.setFirstName("Jane");
    userUpdateDTO.setLastName("Doe");
    userUpdateDTO.setWalletBalance(1200.0);

    loginDTO = new LoginDTO();
    loginDTO.setEmail("test@gmail.com");
    loginDTO.setPassword("password");

    forgotPasswordDTO = new ForgotPasswordDTO();
    forgotPasswordDTO.setEmail("test@gmail.com");
  }

  @Test
  public void registerUser_ShouldReturnCreated() throws Exception {
    when(userService.createUser(any(UserCreateDTO.class))).thenReturn(userResponseDTO);

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userCreateDTO)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).createUser(any(UserCreateDTO.class));
  }

  @Test
  public void getUserById_ShouldReturnUser() throws Exception {
    when(userService.getUserById(eq(1L))).thenReturn(userResponseDTO);

    mockMvc.perform(get("/users/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).getUserById(eq(1L));
  }

  @Test
  public void getAllUsers_ShouldReturnListOfUsers() throws Exception {
    List<UserResponseDTO> users = Arrays.asList(userResponseDTO);
    when(userService.getAllUsers()).thenReturn(users);

    mockMvc.perform(get("/users/"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].email").value("test@gmail.com"));

    verify(userService, times(1)).getAllUsers();
  }

  @Test
  public void updateUser_ShouldReturnUpdatedUser() throws Exception {
    when(userService.updateUser(eq(1L), any(UserUpdateDTO.class))).thenReturn(userResponseDTO);

    mockMvc.perform(put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userUpdateDTO)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).updateUser(eq(1L), any(UserUpdateDTO.class));
  }

  @Test
  public void deleteUser_ShouldReturnNoContent() throws Exception {
    doNothing().when(userService).deleteUser(eq(1L));

    mockMvc.perform(delete("/users/1"))
      .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteUser(eq(1L));
  }

  @Test
  public void login_ShouldReturnUser() throws Exception {
    when(userService.login(any(LoginDTO.class))).thenReturn(userResponseDTO);

    mockMvc.perform(post("/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginDTO)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).login(any(LoginDTO.class));
  }

  @Test
  public void forgotPassword_ShouldReturnSuccessMessage() throws Exception {
    doNothing().when(userService).forgotPassword(any(ForgotPasswordDTO.class));

    mockMvc.perform(post("/users/forgot-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(forgotPasswordDTO)))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Password reset email sent")));

    verify(userService, times(1)).forgotPassword(any(ForgotPasswordDTO.class));
  }

  @Test
  public void changePassword_ShouldReturnUpdatedUser() throws Exception {
    when(userService.changePassword(eq(1L), anyString())).thenReturn(userResponseDTO);

    mockMvc.perform(put("/users/1/change-password")
        .contentType(MediaType.APPLICATION_JSON)
        .content("\"newPassword\""))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).changePassword(eq(1L), anyString());
  }

  @Test
  public void updateAddress_ShouldReturnUpdatedUser() throws Exception {
    when(userService.updateAddress(eq(1L), anyString())).thenReturn(userResponseDTO);

    mockMvc.perform(put("/users/1/address")
        .contentType(MediaType.APPLICATION_JSON)
        .content("\"123 New Street\""))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).updateAddress(eq(1L), anyString());
  }

  @Test
  public void updateWalletBalance_ShouldReturnUpdatedUser() throws Exception {
    when(userService.updateWalletBalance(eq(1L), anyDouble())).thenReturn(userResponseDTO);

    mockMvc.perform(put("/users/1/wallet-balance")
        .contentType(MediaType.APPLICATION_JSON)
        .content("1000.0"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.email").value("test@gmail.com"));

    verify(userService, times(1)).updateWalletBalance(eq(1L), anyDouble());
  }
}
