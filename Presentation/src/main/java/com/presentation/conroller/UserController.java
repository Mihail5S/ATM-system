package com.presentation.conroller;

import Model.Gender;
import Model.HairColor;
import Model.User;
import Services.FriendService;
import Services.UserService;
import com.presentation.entity.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Управление пользователями", description = "Операции с пользователями и их друзьями")
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @Operation(summary = "Создание пользователя",
            description = "Регистрирует нового пользователя в системе")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует")
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {
        if (userService.FindUserByLogin(userDto.getLogin()) != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }

        User user = new User(
                userDto.getName(),
                userDto.getLogin(),
                userDto.getAge(),
                userDto.getGender(),
                userDto.getHaircolor()
        );

        boolean created = userService.TryAddUser(user);
        if (created) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Пользователь создан");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при создании пользователя");
        }
    }

    @Operation(summary = "Получение пользователя",
            description = "Возвращает информацию о пользователе по логину")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam String id) {
        User user = userService.FindUserByLogin(id);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Такого пользователя нет");
        } else {
            return ResponseEntity.ok(UserDTO.toUserDto(user));
        }
    }

    @Operation(summary = "Получение друзей пользователя",
            description = "Возвращает список друзей пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список друзей получен",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FriendDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getUserFriends(@PathVariable String userId) {
        User user = userService.FindUserByLogin(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }

        Set<FriendDTO> friends = user.getFriends().stream()
                .map(friend -> new FriendDTO(friend.getLogin()))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(friends);
    }

    @Operation(summary = "Фильтрация пользователей",
            description = "Возвращает отфильтрованный список пользователей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))
    })
    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredUsers(
            @RequestParam(required = false) HairColor hairColor,
            @RequestParam(required = false) Gender gender
    ) {
        List<User> filteredUsers = userService.getFilteredUsers(hairColor, gender);
        return ResponseEntity.ok(filteredUsers.stream().map(UserDTO::toUserDto).toList());
    }

    @Operation(summary = "Добавление друга",
            description = "Добавляет пользователя в друзья")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно добавлен в друзья"),
            @ApiResponse(responseCode = "400", description = "Невозможно добавить в друзья"),
            @ApiResponse(responseCode = "404", description = "Один из пользователей не найден")
    })
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> addFriend(@PathVariable String userId, @PathVariable String friendId) {
        User user = userService.FindUserByLogin(userId);
        User friend = userService.FindUserByLogin(friendId);
        if (user == null || friend == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Один из пользователей не найден");
        }

        boolean added = friendService.tryAddFriends(user, friend);
        if (added) {
            return ResponseEntity.ok("Пользователи стали друзьями");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Невозможно добавить в друзья");
        }
    }

    @Operation(summary = "Удаление друга",
              description = "Удаляет пользователя из друзей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален из друзей"),
            @ApiResponse(responseCode = "400", description = "Ошибка удаления из друзей"),
            @ApiResponse(responseCode = "404", description = "Один из пользователей не найден")
    })
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> removeFriend(@PathVariable String userId, @PathVariable String friendId) {
        User user = userService.FindUserByLogin(userId);
        User friend = userService.FindUserByLogin(friendId);
        if (user == null || friend == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Один из пользователей не найден");
        }

        boolean removed = friendService.removeFriend(user, friend);
        if (removed) {
            return ResponseEntity.ok("Пользователи больше не друзья");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка удаления из друзей");
        }
    }

    @Operation(summary = "Пользователи с банковскими счетами",
            description = "Возвращает список всех пользователей с их банковскими счетами")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserWithAccountsDTO.class))))
    })
    @GetMapping("/with-accounts")
    public ResponseEntity<?> getAllUsersWithAccounts() {
        List<User> users = userService.getAllUsersWithAccounts();
        List<UserWithAccountsDTO> dtos = users.stream()
                .map(user -> {
                    List<BankAccountDTO> accounts = user.getBankAccounts().stream()
                            .map(BankAccountDTO::toBankAccountDto)
                            .collect(Collectors.toList());

                    return new UserWithAccountsDTO(
                            user.getLogin(),
                            user.getName(),
                            user.getAge(),
                            user.getGender(),
                            user.getHaircolor(),
                            accounts
                    );
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}