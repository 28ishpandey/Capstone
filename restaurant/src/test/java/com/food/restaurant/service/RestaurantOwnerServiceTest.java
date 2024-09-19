package com.food.restaurant.service;

import com.food.restaurant.constant.Constants;
import com.food.restaurant.dto.ForgotPasswordDTO;
import com.food.restaurant.dto.LoginDTO;
import com.food.restaurant.dto.RestaurantOutDTO;
import com.food.restaurant.dto.RestaurantOwnerInDTO;
import com.food.restaurant.dto.RestaurantOwnerOutDTO;
import com.food.restaurant.dto.RestaurantOwnerUpdateDTO;
import com.food.restaurant.entity.Restaurant;
import com.food.restaurant.entity.RestaurantOwner;
import com.food.restaurant.exception.ResourceAlreadyExistException;
import com.food.restaurant.exception.ResourceNotFoundException;
import com.food.restaurant.repository.RestaurantOwnerRepository;
import com.food.restaurant.repository.RestaurantRepository;
import com.food.restaurant.util.MessageDTO;
import com.food.restaurant.util.PasswordUtil;
import com.food.restaurant.util.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantOwnerServiceTest {

  @Mock
  private RestaurantOwnerRepository ownerRepository;

  @Mock
  private RestaurantMapper restaurantMapper;

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private JavaMailSender javaMailSender;

  @InjectMocks
  private RestaurantOwnerService restaurantOwnerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRestaurantOwner_Success() {
    RestaurantOwnerInDTO ownerDto = new RestaurantOwnerInDTO();
    ownerDto.setEmail("test@example.com");
    ownerDto.setPassword("password");
    ownerDto.setFirstName("first");
    ownerDto.setLastName("last");
    ownerDto.setContactNumber("1234567890");

    when(ownerRepository.existsByEmail(anyString())).thenReturn(false);
    when(restaurantMapper.toEntity(any(RestaurantOwnerInDTO.class))).thenReturn(new RestaurantOwner());

    ResponseEntity<MessageDTO> response = restaurantOwnerService.createRestaurantOwner(ownerDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_OWNER_CREATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(ownerRepository).save(any(RestaurantOwner.class));
  }

  @Test
  void createRestaurantOwner_EmailExists() {
    RestaurantOwnerInDTO ownerDto = new RestaurantOwnerInDTO();
    ownerDto.setEmail("existing@example.com");

    when(ownerRepository.existsByEmail(anyString())).thenReturn(true);

    assertThrows(ResourceAlreadyExistException.class, () -> restaurantOwnerService.createRestaurantOwner(ownerDto));
  }

  @Test
  void createRestaurantOwner_NullEmail() {
    RestaurantOwnerInDTO ownerDto = new RestaurantOwnerInDTO();
    ownerDto.setEmail(null);

    assertThrows(NullPointerException.class, () -> restaurantOwnerService.createRestaurantOwner(ownerDto));
  }

  @Test
  void updateRestaurantOwner_Success() {
    Long id = 1L;
    RestaurantOwnerUpdateDTO ownerDto = new RestaurantOwnerUpdateDTO();
    ownerDto.setEmail("updated@example.com");
    ownerDto.setFirstName("first");
    ownerDto.setLastName("last");
    ownerDto.setContactNumber("1234567890");

    RestaurantOwner existingOwner = new RestaurantOwner();
    when(ownerRepository.findById(id)).thenReturn(Optional.of(existingOwner));

    ResponseEntity<MessageDTO> response = restaurantOwnerService.updateRestaurantOwner(id, ownerDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_OWNER_UPDATED_SUCCESSFULLY, response.getBody().getMessage());
    verify(ownerRepository).save(existingOwner);
  }

  @Test
  void updateRestaurantOwner_NotFound() {
    Long id = 999L;
    RestaurantOwnerUpdateDTO ownerDto = new RestaurantOwnerUpdateDTO();

    when(ownerRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.updateRestaurantOwner(id, ownerDto));
  }


  @Test
  void deleteRestaurantOwner_Success() {
    Long id = 1L;
    when(ownerRepository.existsById(id)).thenReturn(true);

    ResponseEntity<MessageDTO> response = restaurantOwnerService.deleteRestaurantOwner(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Constants.RESTAURANT_OWNER_DELETED_SUCCESSFULLY, response.getBody().getMessage());
    verify(ownerRepository).deleteById(id);
  }

  @Test
  void deleteRestaurantOwner_NotFound() {
    Long id = 999L;
    when(ownerRepository.existsById(id)).thenReturn(false);

    ResponseEntity<MessageDTO> response = restaurantOwnerService.deleteRestaurantOwner(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(Constants.RESOURCE_NOT_FOUND, response.getBody().getMessage());
  }

  @Test
  void getRestaurantOwner_Success() {
    Long id = 1L;
    RestaurantOwner owner = new RestaurantOwner();
    RestaurantOwnerOutDTO ownerDto = new RestaurantOwnerOutDTO();

    when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));
    when(restaurantMapper.toDto(owner)).thenReturn(ownerDto);

    ResponseEntity<RestaurantOwnerOutDTO> response = restaurantOwnerService.getRestaurantOwner(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ownerDto, response.getBody());
  }

  @Test
  void getRestaurantOwner_NotFound() {
    Long id = 999L;
    when(ownerRepository.findById(id)).thenReturn(Optional.empty());

    ResponseEntity<RestaurantOwnerOutDTO> response = restaurantOwnerService.getRestaurantOwner(id);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getAllRestaurantOwners_Success() {
    List<RestaurantOwner> owners = Arrays.asList(new RestaurantOwner(), new RestaurantOwner());
    when(ownerRepository.findAll()).thenReturn(owners);
    when(restaurantMapper.toDto(any(RestaurantOwner.class))).thenReturn(new RestaurantOwnerOutDTO());

    ResponseEntity<List<RestaurantOwnerOutDTO>> response = restaurantOwnerService.getAllRestaurantOwners();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void getAllRestaurantOwners_EmptyList() {
    when(ownerRepository.findAll()).thenReturn(Collections.emptyList());

    ResponseEntity<List<RestaurantOwnerOutDTO>> response = restaurantOwnerService.getAllRestaurantOwners();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
  }

  @Test
  void login_Success() {
    LoginDTO loginDto = new LoginDTO();
    loginDto.setEmail("test@example.com");
    loginDto.setPassword("password");

    RestaurantOwner owner = new RestaurantOwner();
    owner.setPassword(PasswordUtil.encode("password"));
    owner.setId(1L);
    owner.setFirstName("first");
    owner.setLastName("last");
    owner.setEmail("test@example.com");
    owner.setContactNumber("1234567890");

    when(ownerRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(owner));

    ResponseEntity<RestaurantOwnerOutDTO> response = restaurantOwnerService.login(loginDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getId());
    assertEquals("first", response.getBody().getFirstName());
    assertEquals("last", response.getBody().getLastName());
    assertEquals("test@example.com", response.getBody().getEmail());
    assertEquals("1234567890", response.getBody().getContactNumber());
  }

  @Test
  void login_EmailNotFound() {
    LoginDTO loginDto = new LoginDTO();
    loginDto.setEmail("nonexistent@example.com");

    when(ownerRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.login(loginDto));
  }

  @Test
  void login_IncorrectPassword() {
    LoginDTO loginDto = new LoginDTO();
    loginDto.setEmail("test@example.com");
    loginDto.setPassword("wrongpassword");

    RestaurantOwner owner = new RestaurantOwner();
    owner.setPassword(PasswordUtil.encode("correctpassword"));

    when(ownerRepository.findByEmail(loginDto.getEmail())).thenReturn(Optional.of(owner));

    assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.login(loginDto));
  }

  @Test
  void getRestaurantsByOwnerId_Success() {
    Long ownerId = 1L;
    List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
    when(restaurantRepository.findByRestaurantOwnerId(ownerId)).thenReturn(restaurants);

    ResponseEntity<List<RestaurantOutDTO>> response = restaurantOwnerService.getRestaurantsByOwnerId(ownerId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void getRestaurantsByOwnerId_NoRestaurantsFound() {
    Long ownerId = 1L;
    when(restaurantRepository.findByRestaurantOwnerId(ownerId)).thenReturn(Collections.emptyList());

    assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.getRestaurantsByOwnerId(ownerId));
  }

  @Test
  void forgotPassword_Success() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
    forgotPasswordDTO.setEmail("test@example.com");

    RestaurantOwner owner = new RestaurantOwner();
    owner.setPassword(PasswordUtil.encode("password"));

    when(ownerRepository.findByEmail(forgotPasswordDTO.getEmail())).thenReturn(Optional.of(owner));

    assertDoesNotThrow(() -> restaurantOwnerService.forgotPassword(forgotPasswordDTO));
    verify(javaMailSender).send(any(SimpleMailMessage.class));
  }

  @Test
  void forgotPassword_EmailNotFound() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
    forgotPasswordDTO.setEmail("nonexistent@example.com");

    when(ownerRepository.findByEmail(forgotPasswordDTO.getEmail())).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.forgotPassword(forgotPasswordDTO));
  }

  @Test
  void forgotPassword_EmailSendingFailure() {
    ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
    forgotPasswordDTO.setEmail("test@example.com");

    RestaurantOwner owner = new RestaurantOwner();
    owner.setPassword(PasswordUtil.encode("password"));

    when(ownerRepository.findByEmail(forgotPasswordDTO.getEmail())).thenReturn(Optional.of(owner));
    doThrow(new RuntimeException("Email sending failed")).when(javaMailSender).send(any(SimpleMailMessage.class));

    assertThrows(RuntimeException.class, () -> restaurantOwnerService.forgotPassword(forgotPasswordDTO));
  }

  @Test
  void toOutDTO_Success() {
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1L);
    restaurant.setEmail("restaurant@example.com");
    restaurant.setName("Test Restaurant");
    restaurant.setContactNumber("1234567890");
    restaurant.setAddress("123 Test St");
    restaurant.setTimings("9AM-9PM");
    restaurant.setIsOpen(true);
    byte[] image = {1, 2, 3};
    restaurant.setImage(image);

    RestaurantOutDTO dto = restaurantOwnerService.toOutDTO(restaurant);

    assertEquals(1L, dto.getId());
    assertEquals("restaurant@example.com", dto.getEmail());
    assertEquals("Test Restaurant", dto.getName());
    assertEquals("1234567890", dto.getContactNumber());
    assertEquals("123 Test St", dto.getAddress());
    assertEquals("9AM-9PM", dto.getTimings());
    assertTrue(dto.getIsOpen());
    assertArrayEquals(image, dto.getImage());
  }

  @Test
  void toOutDTO_NullFields() {
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1L);

    RestaurantOutDTO dto = restaurantOwnerService.toOutDTO(restaurant);

    assertEquals(1L, dto.getId());
    assertNull(dto.getEmail());
    assertNull(dto.getName());
    assertNull(dto.getContactNumber());
    assertNull(dto.getAddress());
    assertNull(dto.getTimings());
    assertNull(dto.getIsOpen());
    assertNull(dto.getImage());
  }
}